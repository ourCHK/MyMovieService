package db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;


public class DBConnection { 
	
		  
	private final static String DBDRIVER = "com.mysql.jdbc.Driver";   
	private final static String DBURL = "jdbc:mysql://localhost:3306/MyMovie";  	  
	private final static String DBUSER = "root";  	  
	private final static String DBPASSWORD = "root";  	  
	private static Connection conn = null;  
	
	public DBConnection(){   
	}  
	  
	public void testConnection() {
		Connection conn = getConnection();
		String sql = "insert into User(name,account,password,phone) values(?,?,?,?)";
		try {
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setString(1, "CHK");
			preStat.setString(2, "12345");
			preStat.setString(3, "12345");
			preStat.setString(4, "18826402897");
			preStat.execute();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){    
		try {  	  
			Class.forName(DBDRIVER);  
		    conn = (com.mysql.jdbc.Connection) DriverManager.getConnection(DBURL,DBUSER,DBPASSWORD);    
		} catch (Exception e) {  
		    System.out.println("加载数据库驱动失败！");  
		} 
	    return conn;  
	}  
	  
	public void close(){  
		try{
	       conn.close();  
	    }catch(Exception e){  
	    	System.out.println("数据库连接关闭失败！");  
	    }  
	}  
}
