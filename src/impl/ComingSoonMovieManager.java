package impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bean.ComingSoonMovie;
import dao.ComingSoonMovieDao;
import db.DBConnection;
import okhttp3.Response;
import tools.OKHttpUtil;

public class ComingSoonMovieManager implements ComingSoonMovieDao {

	int movieCount;
	int serverMovieCount;
	int start = 0;
	int count = 20;
	ArrayList<ComingSoonMovie> csMovieList;
	
	public ComingSoonMovieManager() {
		init();
	}
	
	public static void main(String[] args) {
		new ComingSoonMovieManager().AddAllMovie();
	}
	
	/**
	 * 初始化文件夹参数
	 */
	public void init() {
		String dir = "/home/chk/MyMovie";
		String comingSoonMovieDir = "/home/chk/MyMovie/ComingSoonDir";	
		File file1 = new File(dir);
		File file2 = new File(comingSoonMovieDir);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		if (!file2.exists()) {
			file2.mkdirs();
		}
		csMovieList = new ArrayList<>();
		movieCount = getMovieCount();
		serverMovieCount = getServerMovieCount();
	}
	
	/**t
	 * 讲服务器上的所有影片添加到本地
	 * @return
	 */
	public boolean AddAllMovie() {
		
		if (serverMovieCount <= movieCount) {
			System.out.println("影片已下载完毕");
			return true;
		}
			
		while (start <= serverMovieCount) {
			if (!addMovie())
				return false;
			start = start + count;
		}
		System.out.println("影片已下载完毕");
		return true;
	}
	
	@Override
	public boolean addMovie() {
		// TODO Auto-generated method stub
		if (!getJson())
			return false;
		int result[] = null;
		Connection conn = DBConnection.getConnection();
		try {
			String sql = "insert into ComingSoonMovie(id,title,collect_count,original_title,year,images,image_path,create_date)"
					+ " values(?,?,?,?,?,?,?,?);";
			PreparedStatement preStat = conn.prepareStatement(sql);
			for (ComingSoonMovie csMovie:csMovieList) {
				preStat.setInt(1, csMovie.getId());
				preStat.setString(2, csMovie.getTitle());
				preStat.setInt(3, csMovie.getCollect_count());
				preStat.setString(4, csMovie.getOriginal_title());
				preStat.setInt(5, csMovie.getYear());
				preStat.setString(6, csMovie.getImages());
				preStat.setString(7, csMovie.getImage_path());
				preStat.setDate(8, csMovie.getCreate_date());
				preStat.addBatch();
			}
			result = preStat.executeBatch();
			for (int r:result) {
				System.out.println(r+"");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public boolean getPic(String url,int id,ComingSoonMovie csMovie) {
		// TODO Auto-generated method stub
		
		try {
			Response response = OKHttpUtil.getRequest(url, null);
			String pathDir = "/home/chk/MyMovie/ComingSoonDir";
			String path = "/home/chk";
			String fileName = "";
			InputStream is = null;
            byte[] buf = new byte[2048];
            int len = 0;
            FileOutputStream fos = null;
            try {
            	String content_Type = response.header("Content-Type");
            	String type = content_Type.substring(content_Type.lastIndexOf('/')+1);
            	fileName = id+"."+type;
            
                is = response.body().byteStream();
                File file = new File(pathDir, fileName);
                fos = new FileOutputStream(file);
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                }
                fos.flush();
                csMovie.setImage_path(pathDir+"/"+fileName);
                System.out.println("文件下载成功！");
                return true;
            } catch (Exception e) {
            	e.printStackTrace();
            	System.out.println("文件下载失败！");
            	return false;
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
		return false;
	}


	@Override
	public boolean getJson() {
		// TODO Auto-generated method stub
		String url = "http://api.douban.com/v2/movie/coming_soon?start="+start+"&count="+count;
		try {
			Response response = OKHttpUtil.getRequest(url, null);
			String json = response.body().string();
			parseComingSoon(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param json
	 * @return 获取服务器电影图片出错的个数，0个表示解析全部成功
	 */
	public int parseComingSoon(String json) {
		Date create_date = new Date(System.currentTimeMillis());	//存储时间
		int errorCount = 0;	//获取图片出错的个数
		csMovieList.clear();
		
		JsonParser parser = new JsonParser();
		JsonObject object = null;
		object = (JsonObject) parser.parse(json);
		JsonArray array = object.get("subjects").getAsJsonArray();
		
		for(int i=0; i<array.size(); i++) {
			JsonObject subjectsObject = array.get(i).getAsJsonObject();
			JsonArray genresArray = subjectsObject.get("genres").getAsJsonArray();
			JsonObject imagesObject = subjectsObject.get("images").getAsJsonObject();
			JsonObject ratingObject = subjectsObject.get("rating").getAsJsonObject();
			
			int id = subjectsObject.get("id").getAsInt();
			String title = subjectsObject.get("title").getAsString();
			int collect_count = subjectsObject.get("collect_count").getAsInt();
			String original_title = subjectsObject.get("original_title").getAsString();
			int year = subjectsObject.get("year").getAsInt();
			String images = imagesObject.get("large").getAsString();

//			for(int j=0; j<genresArray.size(); j++) {
//				System.out.println(genresArray.get(j).getAsString());
//			}
			
			ComingSoonMovie csMovie = new ComingSoonMovie();
			csMovie.setId(id);
			csMovie.setTitle(title);
			csMovie.setCollect_count(collect_count);
			csMovie.setOriginal_title(original_title);
			csMovie.setYear(year);
			csMovie.setImages(images);
			csMovie.setCreate_date(create_date);
			csMovieList.add(csMovie);
		}
		for (ComingSoonMovie csMovie:csMovieList) {
			int id = csMovie.getId();
			String image_url = csMovie.getImages();
			if (!getPic(image_url,id,csMovie))
				errorCount ++;
		}
		
		for(ComingSoonMovie csMovie:csMovieList) {
			System.out.println(csMovie.getId()+"");
			System.out.println(csMovie.getTitle());
			System.out.println(csMovie.getCollect_count());
			System.out.println(csMovie.getOriginal_title());
			System.out.println(csMovie.getYear()+"");
			System.out.println(csMovie.getImages());
			System.out.println(csMovie.getImage_path());
			System.out.println(csMovie.getCreate_date());
			System.out.println();
		}
		return errorCount;	
	}

	@Override
	public int getMovieCount() {
		// TODO Auto-generated method stub
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		int count = 0;
		try {
			String sql = "select count(*) from ComingSoonMovie";
			PreparedStatement preStat = conn.prepareStatement(sql);
			rs = preStat.executeQuery();
			while(rs.next()) {
				count = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(count+"");
		return count;
	}

	@Override
	public int getServerMovieCount() {
		// TODO Auto-generated method stub
		int serverMovieCount = 0;
		String url = "http://api.douban.com/v2/movie/coming_soon?start=1&count=1";	
		try {
			Response response = OKHttpUtil.getRequest(url, null);
			String json = response.body().string();
			JsonParser parser = new JsonParser();
			JsonObject object = null;
			object = (JsonObject) parser.parse(json);
			serverMovieCount = object.get("total").getAsInt();
			System.out.println(serverMovieCount+ "");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}				
		return serverMovieCount;
	}

	@Override
	public String getQueryMovieJson(int from, int count) {
		// TODO Auto-generated method stub
		String result = null;
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		List<ComingSoonMovie> csMovieList = new ArrayList<>();
		try {
			String sql = "select * from ComingSoonMovie limit ?,?;";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setInt(1, from);
			preStat.setInt(2, count);
			rs = preStat.executeQuery();
			while(rs.next()) {
				ComingSoonMovie csMovie = new ComingSoonMovie();
				csMovie.setId(rs.getInt(1));
				csMovie.setTitle(rs.getString(2));
				csMovie.setCollect_count(rs.getInt(3));
				csMovie.setOriginal_title(rs.getString(4));
				csMovie.setYear(rs.getInt(5));
				csMovie.setImages(rs.getString(6));
				csMovie.setImage_path(rs.getString(7));
				csMovie.setCreate_date(rs.getDate(8));
				csMovieList.add(csMovie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = listToJson(csMovieList);
		System.out.println(result);
		return result;
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
