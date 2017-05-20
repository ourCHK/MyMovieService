package impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.ComingSoonMovie;
import bean.Ticket;
import dao.TicketDao;
import db.DBConnection;

public class TicketManager implements TicketDao{

	public static void main(String[] args) {
		System.out.println(new TicketManager().getPrice(26808466));
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
	

}
