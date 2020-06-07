package qpgenerator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static Connection connection = null;
	static{
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/qp", "root", "root");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		return connection;
	}
}