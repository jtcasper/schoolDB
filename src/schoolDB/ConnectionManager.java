package schoolDB;

import java.sql.*;

public class ConnectionManager {
	
	private static Connection dbConnection;
	
	public static Connection getConnectionInstance() {
		if (dbConnection == null) {
			try {
				dbConnection = DriverManager.getConnection(
						"jdbc:oracle:thin:@//orca.csc.ncsu.edu:1521/orcl.csc.ncsu.edu", "jtcasper", "200053087");
			} catch (SQLException e) {
				System.out.println();
		    	e.printStackTrace();
			}
		}
		return dbConnection;
	}

}
