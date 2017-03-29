package impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import bean.Movie;
import dao.MovieDao;
import db.DBConnection;

public class MovieManager implements MovieDao {

	@Override
	public boolean addMovie(String name, String main_performer, String introduce, boolean is_on_show, String path) {
		// TODO Auto-generated method stub
		int result = 0;
		Connection conn = DBConnection.getConnection();
		try {
			String sql = "insert into Movie(name,main_performer,introduce,is_on_show,path)"
					+ " values(?,?,?,?,?);";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setString(1, name);
			preStat.setString(2, main_performer);
			preStat.setString(3, introduce);
			preStat.setBoolean(4, is_on_show);
			preStat.setString(5, path);
			result = preStat.executeUpdate();
			if(result != 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 
		return false;
	}

	@Override
	public boolean queryMovie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteMovie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMovie() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getQueryMovieJson(int from, int to) {
		// TODO Auto-generated method stub
		String result = null;
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		List<Movie> movieList = new ArrayList<>();
		try {
			String sql = "select * from Movie order by id desc limit ?,?;";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setInt(1, from);
			preStat.setInt(2, to);
			rs = preStat.executeQuery();
			while(rs.next()) {
				Movie movie = new Movie();
				movie.setId(rs.getInt(1));
				movie.setName(rs.getString(2));
				movie.setMain_performer(rs.getString(3));
				movie.setIntroduce(rs.getString(4));
				movie.setIs_on_show(rs.getBoolean(5));
				movie.setPath(rs.getString(6));
				movieList.add(movie);
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
		result = listToJson(movieList);
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
