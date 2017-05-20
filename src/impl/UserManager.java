package impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.Gson;
import com.mysql.jdbc.Connection;

import bean.User;
import dao.UserDao;
import db.DBConnection;

public class UserManager implements UserDao{

	String userAccount;
	User user;
	
	public static void main(String[] arsg) {
		System.out.println(new UserManager().updateUser("chk2", "男","6622236", "1234567", "18826402897"));
	}
	
	@Override
	public boolean updateUser(String name, String sex, String account, String password, String phone) {
		// TODO Auto-generated method stub
		System.out.println(name+" "+sex+" "+account+" "+password+" "+phone);
		int result = 0;
		Connection conn = DBConnection.getConnection();
		try {
			Statement stat = conn.createStatement();
//			String sql1 = "update User set name = '"+name+"'"
//					+",sex = '"+sex+"'"
//					+",password='"+password+"'"
//					+",phone='"+phone+"' where account="+account;
			String sql = "update User set name = ?,password = ?,phone = ? where account = ?;";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setString(1, name);
			preStat.setString(2, password);
			preStat.setString(3, phone);
			preStat.setString(4, account);
			result = preStat.executeUpdate();
			System.out.println("执行"+" ");
			System.out.println(result);
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
	
	public String getUserInfo(){
		Connection conn = DBConnection.getConnection();
		ResultSet rs = null;
		String sql = "select * from User where account =?;";
		try {
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setString(1, userAccount);
			rs = preStat.executeQuery();
			if(rs.next()) {
				user = new User();
				user.setName(rs.getString(2));
				user.setSex(rs.getString(3));
				user.setAccount(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setPhone(rs.getString(6));
			}
			return userToJson();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
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
	}
	
	@Override
	public boolean loginUser(String userAccount,String userPassword) {
		// TODO Auto-generated method stub
		this.userAccount = userAccount;
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

	@Override
	public boolean deleteUser() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	public String userToJson() {
		Gson gson = new Gson();
		return gson.toJson(user);
	}

}
