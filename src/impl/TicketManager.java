package impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Ticket;
import bean.TicketShow;
import dao.TicketDao;
import db.DBConnection;

public class TicketManager implements TicketDao{

	public static void main(String[] args) {
		System.out.println(new TicketManager().updatePrice(19899718, 23));
	}
	
	@Override
	public int checkCount(int movieId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addTicket(int movieId, int count, int price, Date create_date) {
		// TODO Auto-generated method stub
		Connection conn = DBConnection.getConnection();
			
		return false;
	}

	public boolean addAllTickets(ArrayList<Ticket> ticketList) {
		deleteAllTickets();
		int result[] = null;
		Connection conn = DBConnection.getConnection();
		try {
			String sql = "insert into Ticket(movieId,count,price,create_date)"
					+ " values(?,?,?,?);";
			PreparedStatement preStat = conn.prepareStatement(sql);
			for (Ticket ticket:ticketList) {
				preStat.setInt(1, ticket.getMovieId());
				preStat.setInt(2, ticket.getCount());
				preStat.setInt(3, ticket.getPrice());
				preStat.setDate(4, ticket.getCreate_date());
				preStat.addBatch();
			}
			result = preStat.executeBatch();
			for (int r:result) {
				System.out.println(r+"");
			}
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
	
	public boolean deleteAllTickets() {
		Connection conn = DBConnection.getConnection();
		try {
			String sql = "delete from Ticket";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.executeUpdate();
			System.out.println("删除电影票成功");
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
	
	@Override
	public int getPrice(int movieId) {
		// TODO Auto-generated method stub
		
		int price = -1;
		Connection conn  = DBConnection.getConnection();
		ResultSet rs = null;
		try {
			String sql = "select price from Ticket where movieId=?";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setInt(1,movieId);
			rs = preStat.executeQuery();
			while (rs.next()) {
				price = rs.getInt(1);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return price;
	}
	
	public List<TicketShow> getTicketShow() {
		ArrayList<TicketShow> ticketShowList = new ArrayList<TicketShow>();
		Connection conn  = DBConnection.getConnection();
		ResultSet rs = null;
		try {
			String sql = "select movieId,title,price from Ticket,InTheaterMovie where movieId = id;";
			PreparedStatement preStat = conn.prepareStatement(sql);
			rs = preStat.executeQuery();
			while (rs.next()) {
				TicketShow ticketShow = new TicketShow();
				ticketShow.setMovieId(rs.getInt(1));
				ticketShow.setMovieTitle(rs.getString(2));
				ticketShow.setMoviePrice(rs.getFloat(3));
				ticketShowList.add(ticketShow);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return ticketShowList;
		} finally {
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ticketShowList;
	}
	
	public boolean updatePrice(int movieId,float price) {
		int result = -1;
		Connection conn = DBConnection.getConnection();
		try {
			String sql = "update Ticket set price = ? where movieId = ?";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setFloat(1, price);
			preStat.setInt(2,movieId);
			result = preStat.executeUpdate();
			if(result != 0) 
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
		return false;
	}
}
