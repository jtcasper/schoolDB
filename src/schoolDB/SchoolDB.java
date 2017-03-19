package schoolDB;

import java.sql.*;
import java.util.Scanner;

import schoolDB.ConnectionManager;

public class SchoolDB {
	
	public static void main(String[] args) {
		
		//setup Connection instance to database
		Connection connection = ConnectionManager.getConnectionInstance();
		
		SQLHandler handler = SQLHandler.getInstance();
		
		try {
			handler.executeSQLFile("sql/dropTables.sql");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			handler.executeSQLFile("sql/createTables.sql");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			handler.executeSQLFile("sql/testData.sql");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
		Scanner inScan = new Scanner(System.in);
		
		System.out.print("Enter your id: ");
		
		String userID = inScan.next();
		
		System.out.print("Enter your password: ");
		
		String userPW = inScan.next();
		
		System.out.println(userID);
		System.out.println(userPW);
		
		String loginAttempt = login(Integer.parseInt(userID), userPW);
		
		System.out.println(loginAttempt);
		
		inScan.close();
		
	}

	private static String login(int userID, String userPW) {
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		String retString = "null";
		String dbRetVal = "";
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT pw FROM ADMINS WHERE eid = ?");
			ps.setInt(1, userID);
			ResultSet rs = ps.executeQuery();
			while( rs.next() ) {
				System.out.println(rs.getString("pw"));
				dbRetVal = rs.getString("pw");
				System.out.println(dbRetVal);
			}
			if( dbRetVal.equals(userPW) ){
				retString = "Successfully authenticated as " + userID; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
			retString = "Failed to authenticate";
		}
		
		return retString;
	}

}
