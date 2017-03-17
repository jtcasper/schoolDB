package schoolDB;

import java.sql.*;
import java.util.Scanner;

import schoolDB.ConnectionManager;

public class SchoolDB {
	
	public static void main(String[] args) {
		
		//setup Connection instance to database
		Connection connection = ConnectionManager.getConnectionInstance();
		
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
			System.out.println("made it this far");
			PreparedStatement ps = conn.prepareStatement("SELECT pw FROM ADMINS WHERE ADMINID = ?");
			ps.setInt(1, userID);
			System.out.println("ps made");
			ResultSet rs = ps.executeQuery();
			System.out.println("ps exec");
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
