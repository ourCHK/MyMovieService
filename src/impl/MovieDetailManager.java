package impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mysql.jdbc.Connection;

import bean.MovieDetail;
import dao.MovieDetailDao;
import db.DBConnection;
import okhttp3.Response;
import tools.OKHttpUtil;

public class MovieDetailManager implements MovieDetailDao{

	public final static int REQUEST_SUCCESS = 1;
	public final static int REQUEST_FAILURE = 2;
	public final static int NETWORK_ERROE = 3;
	
	MovieDetail movieDetail;
	/**
	 * 请求豆瓣数据的url
	 */
	String requestUrl;
	
	/**
	 * 请求豆瓣返回的json
	 */
	String requestJson;
	
	/**
	 * 返回给客户端的json
	 */
	String resultJson;
	
	Gson gson;
	
	public static void main(String[] args) {
		System.out.println(new MovieDetailManager().getMovieDetailJson(19899718));
		
	}
	
	public MovieDetailManager() {
		init();
	}
	
	public void init() {
		File file1 = new File("/home/chk/MyMovie");
		File file2 = new File("/home/chk/MyMovie/MovieDetail");
		if (!file1.exists()) {
			file1.mkdirs();
		} 
		if (!file2.exists()) {
			file2.mkdirs();
		}
		movieDetail = new MovieDetail();
		requestUrl="http://api.douban.com/v2/movie/subject/";
		gson = new Gson();
	}
	
	@Override
	public boolean queryMovieInLocal(int id) {
		// TODO Auto-generated method stub
	
		Connection conn = DBConnection.getConnection();
		String sql = "select * from MovieDetail where id = ?";
		ResultSet rs = null;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				movieDetail.setId(rs.getInt(1));
				movieDetail.setRating(rs.getInt(2));
				movieDetail.setYear(rs.getInt(3));
				movieDetail.setTitle(rs.getString(4));
				movieDetail.setGenres(rs.getString(5));
				movieDetail.setSummary(rs.getString(6));
				movieDetail.setCasts(rs.getString(7));
				movieDetail.setDirectors(rs.getString(8));
				movieDetail.setMobile_url(rs.getString(9));
				movieDetail.setImages(rs.getString(10));
				movieDetail.setImage_path(rs.getString(11));
				movieDetail.setAka(rs.getString(12));
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) 
					conn.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}				
		return false;
	}

	@Override
	public boolean queryMovieInNetwork(int id) {
		// TODO Auto-generated method stub
		Response response= null;
		try {
			response = OKHttpUtil.getRequest(requestUrl+id, null);
			requestJson = response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean writeToLocalDatabase() {
		// TODO Auto-generated method stub
		Connection conn = DBConnection.getConnection();
		String sql = "insert into MovieDetail values(?,?,?,?,?,?,?,?,?,?,?,?)";
		int result = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, movieDetail.getId());
			ps.setInt(2,movieDetail.getRating());
			ps.setInt(3, movieDetail.getYear());
			ps.setString(4, movieDetail.getTitle());
			ps.setString(5, movieDetail.getGenres());
			ps.setString(6, movieDetail.getSummary());
			ps.setString(7, movieDetail.getCasts());
			ps.setString(8, movieDetail.getDirectors());
			ps.setString(9, movieDetail.getMobile_url());
			ps.setString(10, movieDetail.getImages());
			ps.setString(11, movieDetail.getImage_path());
			ps.setString(12, movieDetail.getAka());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) 
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (result != 0) {
			System.out.println("影片已保存至本地数据库");
			return true;
		}
			
		return false;
	}

	@Override
	public boolean parseJson(String json) {
		// TODO Auto-generated method stub
		JsonParser parser = new JsonParser();
		JsonObject object = (JsonObject) parser.parse(json);
		
		JsonObject ratingObject = (JsonObject) object.getAsJsonObject("rating");
		JsonObject imagesObject = (JsonObject) object.get("images").getAsJsonObject();
		JsonArray genresArray = (JsonArray) object.get("genres").getAsJsonArray();
		JsonArray castsArray = (JsonArray) object.get("casts").getAsJsonArray();
		JsonArray directorsArray = (JsonArray) object.get("directors").getAsJsonArray();
		JsonArray akaArray = (JsonArray) object.get("aka").getAsJsonArray();
		
		
		int id = object.get("id").getAsInt();
		int rating = ratingObject.get("average").getAsInt();
		int year = object.get("year").getAsInt();
		String title = object.get("title").getAsString();
		String genres = "";
		for (int i=0; i<genresArray.size(); i++) {
			genres += genresArray.get(i).getAsString();
			if (i != genresArray.size())
				genres += "/";
			
		}
		
		String summary = object.get("summary").getAsString();
		String casts = null;
		for (int i=0; i<castsArray.size(); i++) {
			JsonObject castsObject = castsArray.get(i).getAsJsonObject();
			casts += castsObject.get("name").getAsString();
			if (i != castsArray.size())
				casts += "/";
		}
		String directors = "";
		for (int i=0; i<directorsArray.size(); i++) {
			JsonObject directorsObject = directorsArray.get(i).getAsJsonObject();
			directors += directorsObject.get("name").getAsString();
			if (i != directorsArray.size())
				directors += "/";
		}
		String mobile_url = object.get("mobile_url").getAsString();
		String images = imagesObject.get("large").getAsString();
		String image_path = saveImage(id, images);
		String aka = "";
		for (int i=0; i<akaArray.size(); i++) {
			aka += akaArray.get(i).getAsString();
			if (i != akaArray.size())
				aka += "/";
		}
		
		movieDetail.setId(id);
		movieDetail.setRating(rating);
		movieDetail.setYear(year);
		movieDetail.setTitle(title);
		movieDetail.setGenres(genres);
		movieDetail.setSummary(summary);
		movieDetail.setCasts(casts);
		movieDetail.setDirectors(directors);
		movieDetail.setMobile_url(mobile_url);
		movieDetail.setImages(images);
		movieDetail.setImage_path(image_path);
		movieDetail.setAka(aka);
		
		return true;	
	}

	
	
	@Override
	public String getMovieDetailJson(int id) {
		// TODO Auto-generated method stub
		if (queryMovieInLocal(id))	{ //如果本地查到的话
			resultJson = toJson(movieDetail);
			System.out.println("本地数据库查询");
		} else {
			if (queryMovieInNetwork(id)) { //查询
				if (parseJson(requestJson)) {
					if (!writeToLocalDatabase())
						System.out.println("插入数据库失败");
					resultJson = toJson(movieDetail);
				}	
			}
			System.out.println("豆瓣数据库查询");
		}		
		return resultJson;	
	}

	@Override
	public String toJson(MovieDetail movieDetail) {
		// TODO Auto-generated method stub
		return gson.toJson(movieDetail);
	}

	@Override
	public String saveImage(int id,String url) {
		// TODO Auto-generated method stub
		InputStream is = null;
		byte[] buf = new byte[2048];
		int len = 0;
		FileOutputStream fos = null;
		try {
			Response response = OKHttpUtil.getRequest(url, null);
			String content_Type = response.header("content-Type");
			String type = content_Type.substring(content_Type.lastIndexOf('/')+1);
			String fileName = id+"."+type;
			is = response.body().byteStream();
			File file = new File("/home/chk/MyMovie/MovieDetail/"+fileName);
			fos = new FileOutputStream(file);
			while ((len = is.read(buf)) != -1) {
				fos.write(buf,0,len);
			}
			fos.flush();
			return file.getAbsolutePath();
		} catch (Exception e) {
			e.printStackTrace();
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
		
		return "";
	}
}
