package schoolDB;

import java.sql.*;

public class ConnectionManager {
	
	private static Connection dbConnection;
	
	public static Connection getConnectionInstance() {
		if (dbConnection == null) {
			try {
				dbConnection = DriverManager.getConnection(
						"jdbc:oracle:thin:@//orca.csc.ncsu.edu:1521/orcl.csc.ncsu.edu", "bkmukhej", "200154304");
			} catch (SQLException e) {
				System.out.println();
		    	e.printStackTrace();
			}
		}
		return dbConnection;
	}

}
