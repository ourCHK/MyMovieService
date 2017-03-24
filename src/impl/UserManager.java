package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import dao.UserDao;
import db.DBConnection;

public class UserManager implements UserDao{

	@Override
	public boolean loginUser(String userAccount,String userPassword) {
		// TODO Auto-generated method stub
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		String sql = "select * from User where account =? and password =?;";
		try {
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setString(1, userAccount);
			preStat.setString(2, userPassword);
			rs = preStat.executeQuery();
			if(rs.next())
				return true;
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
		return false;
	}

	@Override
	public boolean registerUser(String name, String sex, String account, String password, String phone) {
		// TODO Auto-generated method stub
		int result = 0;
		Connection conn = DBConnection.getConnection();
		try {
			String sql = "insert into User(name,sex,account,password,phone) values (?,?,?,?,?);";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setString(1, name);
			preStat.setString(2, sex);
			preStat.setString(3, account);
			preStat.setString(4, password);
			preStat.setString(5, phone);
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
	public boolean deleteUser() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateUser() {
		// TODO Auto-generated method stub
		return false;
	}

}
