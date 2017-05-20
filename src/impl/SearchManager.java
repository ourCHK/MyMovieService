package impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bean.SearchMovie;
import db.DBConnection;
import okhttp3.Response;
import tools.OKHttpUtil;

public class SearchManager {
	
	static String KEYWORD = "";
	
	List<SearchMovie> smList;
	int start = 0;
	int count = 10;
	public static void main(String[] args) {
		System.out.println(new SearchManager().search("功夫熊猫", 0, 2));
	}
	
	public SearchManager() {
		init();
	}
	
	public void init() {
		String dir = "/home/chk/MyMovie";
		String inThreaterMovieDir = "/home/chk/MyMovie/SearchMovieDir";	
		File file1 = new File(dir);
		File file2 = new File(inThreaterMovieDir);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		if (!file2.exists()) {
			file2.mkdirs();
		}
		smList = new ArrayList<>();
	}
	
	public String search(String keyword,int start,int count) {
		String url = "http://api.douban.com/v2/movie/search?q="+keyword+"&start="+start+"&count="+count;
		System.out.println(url);
		try {
			Response response = OKHttpUtil.getRequest(url,null);	//非异步
			String json = response.body().string();
			System.out.println(json);
			parseJson(json);
			checkIsInTheater();
			getPic();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}				
		return listToJson(smList);
	}
	
	public void getPic() {
		for (SearchMovie sMovie:smList) {
			try {
				Response response = OKHttpUtil.getRequest(sMovie.getImages(), null);
				String pathDir = "/home/chk/MyMovie/SearchMovieDir";
				String path = "/home/chk";
				String fileName = "";
				InputStream is = null;
	            byte[] buf = new byte[2048];
	            int len = 0;
	            FileOutputStream fos = null;
	            try {
	            	String content_Type = response.header("Content-Type");
	            	String type = content_Type.substring(content_Type.lastIndexOf('/')+1);
	            	fileName = sMovie.getId()+"."+type;
	                is = response.body().byteStream();
	                File file = new File(pathDir, fileName);
	                fos = new FileOutputStream(file);
	                while ((len = is.read(buf)) != -1) {
	                    fos.write(buf, 0, len);
	                }
	                fos.flush();
	                sMovie.setImage_path(pathDir+"/"+fileName);
	                System.out.println("图片下载成功！");
	            } catch (Exception e) {
	            	e.printStackTrace();
	            	System.out.println("图片下载失败！");
	            } finally {
	                try {
	                    if (is != null)
	                        is.close();
	                } catch (IOException e) {
	                }
	                try {
	                    if (fos != null)
	                        fos.close();
	                } catch (IOException e) {
	                }
	            }			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	
	public void parseJson(String json) {
		JsonParser parser = new JsonParser();
		JsonObject object = null;
		object = (JsonObject) parser.parse(json);
		int total = object.get("total").getAsInt();
		JsonArray array = object.get("subjects").getAsJsonArray();
		for(int i=0; i<array.size(); i++) {
			JsonObject subjectsObject = array.get(i).getAsJsonObject();
			JsonArray genresArray = subjectsObject.get("genres").getAsJsonArray();
			JsonObject imagesObject = subjectsObject.get("images").getAsJsonObject();
			JsonObject ratingObject = subjectsObject.get("rating").getAsJsonObject();
			
			int id = subjectsObject.get("id").getAsInt();
			String title = subjectsObject.get("title").getAsString();
			float average = ratingObject.get("average").getAsFloat();
			int year = subjectsObject.get("year").getAsInt();
			String images = imagesObject.get("large").getAsString();
			
			SearchMovie sMovie = new SearchMovie();
			sMovie.setId(id);
			sMovie.setTitle(title);
			sMovie.setYear(year);
			sMovie.setAverage(average);
			sMovie.setImages(images);
			sMovie.setTotal(total);
			smList.add(sMovie);
		}
	}
	
	/**
	 * 检查是否是正在上映的电影
	 */
	public void checkIsInTheater() {
		if (smList.isEmpty()) {
			return;
		}
		List<Integer> inTheaterMovieId = new ArrayList<>();
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		try {
			String sql = "select id from InTheaterMovie";
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				inTheaterMovieId.add(rs.getInt(1));
			}
			for (SearchMovie sMovie:smList) {
				if (inTheaterMovieId.contains(sMovie.getId())) {
					sMovie.setInThreater(true);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 讲list转化为Json，若list为空，则返回""
	 * @param list	要转化的list
	 * @return
	 */
	private String listToJson(List list) {
		if(list.isEmpty())
			return "";
		Gson gson = new Gson();
		return gson.toJson(list);
		
	}
}
