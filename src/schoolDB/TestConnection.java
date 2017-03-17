package schoolDB;

import java.sql.*;

public class TestConnection {
	
	public static void main(String[] argv) {
		
		System.out.println("Testing Oracle JDBC Connection");
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		}
		catch (ClassNotFoundException e) {
			System.out.println("No Oracle JDBC driver found.");
			e.printStackTrace();
			return;
		}
		
        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;

        try{
        	
        	connection = DriverManager.getConnection(
        			"jdbc:oracle:thin:@localhost:1521:xe", "schooldb", "root");
        	
        } catch(SQLException e){
        	System.out.println("CONNECTION FAILED!");
        	e.printStackTrace();
        	return;
        }
        
        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
        
        try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT ADMINID FROM ADMINS");
			while (rs.next()) {
				System.out.println("rs not null");
				System.out.println("AID IS: " + rs.getInt("ADMINID"));
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
	}

}
