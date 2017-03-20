package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import dao.UserDao;
import db.DBConnection;

public class UserManager implements UserDao{

	@Override
	public boolean queryUser(String userAccount,String userPassword) {
		// TODO Auto-generated method stub
		
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		String sql = "select * from User where account =? and password =?;";
		try {
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setString(1, "12345");
			preStat.setString(2, "12345");
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
	public boolean insertUser() {
		// TODO Auto-generated method stub
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
