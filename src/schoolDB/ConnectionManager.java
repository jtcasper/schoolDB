package schoolDB;

import java.sql.*;

public class ConnectionManager {
	
	private static Connection dbConnection;
	
	public static Connection getConnectionInstance() {
		if (dbConnection == null) {
			try {
				dbConnection = DriverManager.getConnection(
						"jdbc:oracle:thin:@localhost:1521:xe", "schooldb", "root");
			} catch (SQLException e) {
				System.out.println();
		    	e.printStackTrace();
			}
		}
		return dbConnection;
	}

}
