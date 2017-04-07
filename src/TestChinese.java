import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DBConnection;

public class TestChinese {
	public static void main(String[] args) throws UnsupportedEncodingException {
		Connection conn = DBConnection.getConnection();
		try {
			String name = "ha哈";
			String name1 = new String(name.getBytes(),"gb2312");
			String sql = "insert into test values(?);";
			PreparedStatement preStat = conn.prepareStatement(sql);
			preStat.setString(1, name1);
			int result = preStat.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}
}
