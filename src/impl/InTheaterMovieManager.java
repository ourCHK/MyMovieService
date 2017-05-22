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

import bean.InTheaterMovie;
import bean.Ticket;
import dao.InTheaterMovieDao;
import db.DBConnection;
import okhttp3.Response;
import tools.OKHttpUtil;

public class InTheaterMovieManager implements InTheaterMovieDao {

	int movieCount;
	int serverMovieCount;
	int start = 0;
	int count = 20;
	ArrayList<InTheaterMovie> itMovieList;
	
	/**
	 * 用于批量添加电影价格
	 */
	TicketManager ticketManager = new TicketManager();
	ArrayList<Ticket> ticketList = new ArrayList<>(); 
	
	public InTheaterMovieManager() {
		init();
	}
	
	public static void main(String[] args) {
		new InTheaterMovieManager().AddAllMovie();
	}
	
	/**
	 * 初始化文件夹参数
	 */
	public void init() {
		String dir = "/home/chk/MyMovie";
		String inThreaterMovieDir = "/home/chk/MyMovie/InTheaterDir";	
		File file1 = new File(dir);
		File file2 = new File(inThreaterMovieDir);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		if (!file2.exists()) {
			file2.mkdirs();
		}
		itMovieList = new ArrayList<>();
		movieCount = getMovieCount();
		serverMovieCount = getServerMovieCount();
	}
	
	/**t
	 * 将服务器上的所有影片添加到本地
	 * @return
	 */
	public boolean AddAllMovie() {
		
		if (serverMovieCount <= movieCount) {
			System.out.println("影片已下载完毕");
			return true;
		} else {
			deleteAllMovie();
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
			String sql = "insert into InTheaterMovie(id,title,average,collect_count,original_title,year,images,image_path,create_date)"
					+ " values(?,?,?,?,?,?,?,?,?);";
			PreparedStatement preStat = conn.prepareStatement(sql);
			for (InTheaterMovie itMovie:itMovieList) {
				preStat.setInt(1, itMovie.getId());
				preStat.setString(2, itMovie.getTitle());
				preStat.setFloat(3,itMovie.getAverage());
				preStat.setInt(4, itMovie.getCollect_count());
				preStat.setString(5, itMovie.getOriginal_title());
				preStat.setInt(6, itMovie.getYear());
				preStat.setString(7, itMovie.getImages());
				preStat.setString(8, itMovie.getImage_path());
				preStat.setDate(9, itMovie.getCreate_date());
				preStat.addBatch();
			}
			result = preStat.executeBatch();
			for (int r:result) {
				System.out.println(r+"");
			}
			
			for (InTheaterMovie itMovie:itMovieList) {	//这部分用于添加电影电影票
				Ticket ticket = new Ticket();
				ticket.setMovieId(itMovie.getId());
				ticket.setCount(48);
				ticket.setPrice(40);
				ticket.setCreate_date(new Date(System.currentTimeMillis()));
				ticketList.add(ticket);
			}
			ticketManager.addAllTickets(ticketList);
			System.out.println("电影价格添加完毕");
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
	public boolean getPic(String url,int id,InTheaterMovie itMovie) {
		// TODO Auto-generated method stub
		
		try {
			Response response = OKHttpUtil.getRequest(url, null);
			String pathDir = "/home/chk/MyMovie/InTheaterDir";
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
                itMovie.setImage_path(pathDir+"/"+fileName);
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
		String url = "http://api.douban.com/v2/movie/in_theaters?city=广州&start="+start+"&count="+count;
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
		itMovieList.clear();
		
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
			float average = ratingObject.get("average").getAsFloat();
			int collect_count = subjectsObject.get("collect_count").getAsInt();
			String original_title = subjectsObject.get("original_title").getAsString();
			int year = subjectsObject.get("year").getAsInt();
			String images = imagesObject.get("large").getAsString();
			

//			for(int j=0; j<genresArray.size(); j++) {
//				System.out.println(genresArray.get(j).getAsString());
//			}
			
			InTheaterMovie itMovie = new InTheaterMovie();
			itMovie.setId(id);
			itMovie.setTitle(title);
			itMovie.setAverage(average);
			itMovie.setCollect_count(collect_count);
			itMovie.setOriginal_title(original_title);
			itMovie.setYear(year);
			itMovie.setImages(images);
			itMovie.setCreate_date(create_date);
			itMovieList.add(itMovie);
		}
		for (InTheaterMovie csMovie:itMovieList) {
			int id = csMovie.getId();
			String image_url = csMovie.getImages();
			if (!getPic(image_url,id,csMovie))
				errorCount ++;
		}
		
		for(InTheaterMovie itMovie:itMovieList) {
			System.out.println(itMovie.getId()+"");
			System.out.println(itMovie.getTitle());
			System.out.println(itMovie.getAverage()+"");
			System.out.println(itMovie.getCollect_count());
			System.out.println(itMovie.getOriginal_title());
			System.out.println(itMovie.getYear()+"");
			System.out.println(itMovie.getImages());
			System.out.println(itMovie.getImage_path());
			System.out.println(itMovie.getCreate_date());
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
			String sql = "select count(*) from InTheaterMovie";
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
		String url = "http://api.douban.com/v2/movie/in_theaters?city=广州";	
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
		List<InTheaterMovie> itMovieList = new ArrayList<>();
		try {
			String sql = "select * from InTheaterMovie limit ?,?;";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setInt(1, from);
			preStat.setInt(2, count);
			rs = preStat.executeQuery();
			while(rs.next()) {
				InTheaterMovie itMovie = new InTheaterMovie();
				itMovie.setId(rs.getInt(1));
				itMovie.setTitle(rs.getString(2));
				itMovie.setAverage(rs.getFloat(3));
				itMovie.setCollect_count(rs.getInt(4));
				itMovie.setOriginal_title(rs.getString(5));
				itMovie.setYear(rs.getInt(6));
				itMovie.setImages(rs.getString(7));
				itMovie.setImage_path(rs.getString(8));
				itMovie.setCreate_date(rs.getDate(9));
				itMovieList.add(itMovie);
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
		result = listToJson(itMovieList);
		System.out.println(result);
		return result;
	}
	
	public boolean deleteAllMovie() {
		int result = 0;
		int movieCount = getMovieCount();
		if (movieCount == 0)
			return true;
		Connection conn = DBConnection.getConnection();
		try {
			String sql = "delete from InTheaterMovie";
			PreparedStatement preStat = conn.prepareStatement(sql);
			result = preStat.executeUpdate();
			if (result != count) {
				System.out.println("删除正在上映电影出错");
				return false;
			}
			System.out.println("删除正在上映电影成功");
			return true;
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
