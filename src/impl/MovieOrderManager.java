package impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bean.MovieOrder;
import dao.MovieOrderDao;
import db.DBConnection;

public class MovieOrderManager implements MovieOrderDao{

	ArrayList<MovieOrder> movieOrderList;
	
	public static void main(String[] args) {
		System.out.println(new MovieOrderManager().getChoosedMovieOrder(1,Date.valueOf("2017-05-12")));
	}
	
	public MovieOrderManager() {
		init();
	}
	
	public void init() {
		movieOrderList = new ArrayList<>();
	}
	
	@Override
	public boolean buyTicket(int userId, int movieId, int[] choosed_rows, int[] choosed_columns) {
		// TODO Auto-generated method stub
		int result[] = null;
		Date create_date = new Date(System.currentTimeMillis());
		for (int i=0;i<choosed_rows.length; i++) {
			MovieOrder movieOrder = new MovieOrder();
			movieOrder.setUserId(userId);
			movieOrder.setMovieId(movieId);
			movieOrder.setChoosed_row(choosed_rows[i]);
			movieOrder.setChoosed_column(choosed_columns[i]);
			movieOrder.setCreate_date(create_date);
			movieOrderList.add(movieOrder);
		}
		
		Connection conn = DBConnection.getConnection();
		try {
			String sql = "insert into MovieOrder (userId,movieId,choosed_row,choosed_column,create_date)"
					+"values(?,?,?,?,?);";
			PreparedStatement preStat = conn.prepareStatement(sql);
			for(MovieOrder movieOrder:movieOrderList) {
				preStat.setInt(1,movieOrder.getUserId());
				preStat.setInt(2,movieOrder.getMovieId());
				preStat.setInt(3,movieOrder.getChoosed_row());
				preStat.setInt(4,movieOrder.getChoosed_column());
				preStat.setDate(5,movieOrder.getCreate_date());
				preStat.addBatch();
			}
			result = preStat.executeBatch();
			for(int r:result) 
				System.out.println("购票结果:"+r);
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
	public String getChoosedMovieOrder(int movieId, Date create_date) {
		// TODO Auto-generated method stub
//		create_date = new Date(System.currentTimeMillis());
		String  result = null;
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		List<MovieOrder> movieOrderList = new ArrayList<>();
		try {
			String sql  = "select * from MovieOrder where movieId=? and create_date=?";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setInt(1,movieId);
			preStat.setDate(2,create_date);
			rs = preStat.executeQuery();
			while (rs.next()) {
				MovieOrder movieOrder = new MovieOrder();
				movieOrder.setUserId(rs.getInt(1));
				movieOrder.setMovieId(rs.getInt(2));
				movieOrder.setChoosed_row(rs.getInt(3));
				movieOrder.setChoosed_column(rs.getInt(4));
				movieOrder.setCreate_date(rs.getDate(5));
				movieOrderList.add(movieOrder);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		} finally {
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		result = listToJson(movieOrderList);
		Gson gson = new Gson();
		ArrayList<MovieOrder> myMovieOrderList = gson.fromJson(result,new TypeToken<ArrayList<MovieOrder>>(){}.getType());
		System.out.println(result);
//		for (int i=0; i<myMovieOrderList.size();i++) {
//			System.out.println(myMovieOrderList.get(i).getCreate_date());
//		}
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
