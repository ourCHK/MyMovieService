package impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import bean.MovieOrder;
import bean.MovieOrderBuy;
import bean.OrderShow;
import bean.User;
import dao.MovieOrderDao;
import db.DBConnection;

public class MovieOrderManager implements MovieOrderDao{

	ArrayList<MovieOrder> movieOrderList;
	
	public static void main(String[] args) {
		System.out.println(new MovieOrderManager().getMovieOrder(10));
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
		Date date  = new Date(System.currentTimeMillis());
		Timestamp create_date = new Timestamp(date.getTime());
//		DateTime create_date = new DateTime();
//		Date create_date = new Date();
		for (int i=0;i<choosed_rows.length; i++) {
			MovieOrder movieOrder = new MovieOrder();
			movieOrder.setUserId(userId);
			movieOrder.setMovieId(movieId);
			movieOrder.setChoosed_row(choosed_rows[i]);
			movieOrder.setChoosed_column(choosed_columns[i]);
			movieOrder.setTicket_date(date);
			movieOrder.setCreate_date(create_date);
			movieOrderList.add(movieOrder);
		}
		
		Connection conn = DBConnection.getConnection();
		try {
			String sql = "insert into MovieOrder (userId,movieId,choosed_row,choosed_column,ticket_date,create_date)"
					+"values(?,?,?,?,?,?);";
			PreparedStatement preStat = conn.prepareStatement(sql);
			for(MovieOrder movieOrder:movieOrderList) {
				preStat.setInt(1,movieOrder.getUserId());
				preStat.setInt(2,movieOrder.getMovieId());
				preStat.setInt(3,movieOrder.getChoosed_row());
				preStat.setInt(4,movieOrder.getChoosed_column());
				preStat.setDate(5,movieOrder.getTicket_date());
				preStat.setTimestamp(6, create_date);
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
	public String getChoosedMovieOrder(int movieId, Date ticket_date) {
		// TODO Auto-generated method stub
//		create_date = new Date(System.currentTimeMillis());
		String  result = null;
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		List<MovieOrder> movieOrderList = new ArrayList<>();
		try {
			String sql  = "select * from MovieOrder where movieId=? and ticket_date=?";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setInt(1,movieId);
			preStat.setDate(2,ticket_date);
			rs = preStat.executeQuery();
			while (rs.next()) {
				MovieOrder movieOrder = new MovieOrder();
				movieOrder.setUserId(rs.getInt(1));
				movieOrder.setMovieId(rs.getInt(2));
				movieOrder.setChoosed_row(rs.getInt(3));
				movieOrder.setChoosed_column(rs.getInt(4));
//				movieOrder.setTicket_date(rs.getDate(5));
//				movieOrder.setCreate_date(rs.getTimestamp(6));
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
	
	public String getMovieOrder(int id) {
		List<MovieOrderBuy> movieOrderBuyList = new ArrayList<>();
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		try {
			String sql  = "select userId,movieId,choosed_row,choosed_column,ticket_date,create_date,title from"
					+ " MovieOrder,MovieDetail where MovieOrder.movieId = MovieDetail.id and userId = ?  order by create_date";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setInt(1,id);
			rs = preStat.executeQuery();
			while (rs.next()) {
				
				MovieOrderBuy movieOrderBuy = new MovieOrderBuy();
				movieOrderBuy.setUserId(rs.getInt(1));
				movieOrderBuy.setMovieId(rs.getInt(2));
				movieOrderBuy.setChoosed_row(rs.getInt(3));
				movieOrderBuy.setChoosed_column(rs.getInt(4));
				System.out.println(rs.getDate(5).toString());
				SimpleDateFormat format = new SimpleDateFormat();
				movieOrderBuy.setTicket_date(rs.getDate(5).toString());
				movieOrderBuy.setCreate_date(rs.getTimestamp(6).toString());
				movieOrderBuy.setTitle(rs.getString(7));
				movieOrderBuyList.add(movieOrderBuy);
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
		return listToJson(movieOrderBuyList);
	}
	
	/**
	 * 获取订单，用于后台订单显示
	 * @return
	 */
	public List getAllOrderShow() {
		ArrayList<OrderShow> orderShowList = new ArrayList();
		
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		String sql = "select User.name,MovieDetail.title,MovieOrder.choosed_row,MovieOrder.choosed_column,MovieOrder.create_date "
				+ "from User,MovieOrder,MovieDetail "
				+ "where MovieOrder.movieId = MovieDetail.id and User.id = MovieOrder.userId order by create_date;";
		try {
			PreparedStatement preStat = conn.prepareStatement(sql);
			rs = preStat.executeQuery();
			while(rs.next()) {
				OrderShow orderShow = new OrderShow();
				String userName = rs.getString(1);
				String movieName = rs.getString(2);
				String rows = rs.getInt(3)+"";
				String columns = rs.getInt(4)+"";
				String create_date = rs.getTimestamp(5).toString();
				orderShow.setUserName(userName);
				orderShow.setMovieName(movieName);
				orderShow.setRows(rows);
				orderShow.setColumns(columns);
				orderShow.setCreate_date(create_date);
				orderShowList.add(orderShow);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(rs != null)
					rs.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return orderShowList;
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
	
	private String stringToJson(String[] strings) {
		if (strings == null) 
			return "";
		Gson gson = new Gson();
		return gson.toJson(strings);
	}
	

}
