package schoolDB;

import java.sql.*;
import java.util.Scanner;

public class interactiveShell {
	
	public static void main(String[] args){
				
		//Get SQLHandler and construct DB
		SQLHandler handler = SQLHandler.getInstance();
		Scanner inScan = new Scanner(System.in);
		
		try {
			handler.executeSQLFile("scripts/setupdb.sql");
			handler.executeSQLFile("scripts/createData.sql");
		} catch(SQLException e){
			e.printStackTrace();
		}
		
		while(true){
			
			String userInput = null;
		
			System.out.print("> Welcome! Enter login to login, or exit to exit: ");
			
			userInput = inScan.nextLine();
			
			if(userInput.equalsIgnoreCase("login") || userInput.equalsIgnoreCase("l")){
				
				login(inScan);
									
			} else if(userInput.equalsIgnoreCase("exit") || userInput.equalsIgnoreCase("e")){
				return;
			} else {
				System.out.println("Command " + userInput + " is not a recognized command.");
			}
		}
		
		
		
	}
	
	/**
	 * Login method, if successful calls the logged in interface.
	 * @param inScan Scanner for System.in for user input
	 */
	private static void login(Scanner inScan) {
		int numTries = 0;
		while(true){
			System.out.print("> Enter your username: ");
			String username = inScan.nextLine();
			System.out.print("> Enter your password: ");
			String password = inScan.nextLine();

			Connection conn = ConnectionManager.getConnectionInstance();

			String retString = "";
			String dbPW = "";
			boolean success = false;
			boolean admin = false;
			User user = null;

			try{
				PreparedStatement ps = conn.prepareStatement("SELECT PASSWORD FROM ADMIN WHERE EID = ?");
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				if( rs.next() ) {
					dbPW = rs.getString("password");
				}
				if( !(dbPW.equals("")) && dbPW.toString().equals(password)){
					retString = "> Successfully logged in as " + username;
					success = true;
					admin = true;
				}
				//See if it is a student
				else {
					try {
						PreparedStatement stmt = conn.prepareStatement("SELECT PASSWORD FROM STUDENT WHERE SID = ?");
						stmt.setString(1, username);
						ResultSet results = stmt.executeQuery();
						if( results.next() ){
							dbPW = results.getString("password");
						}
						if( !(dbPW.equals("")) && dbPW.toString().equals(password)){
							retString = "> Successfully logged in as " + username;
							success = true;
							admin = false;
						}
						else {
							retString = "> Sorry, that username/password combination is not recognized.";
						}
					} catch (SQLException e) {
						retString = "> Sorry, that username/password combination is not recognized.";
					}
				}

			} catch (SQLException e){
				retString = "> Sorry, that username/password combination is not recognized.";
				e.printStackTrace();
			}
			
			System.out.println(retString);
			numTries++;
			if(numTries == 3 && !success){
				System.out.println("> Reached 3 failed attempts. Returning to launch prompt.");
				return;
			}
			if(success){
				user = new User(username, admin);
				numTries = 0;
				shell(user, inScan);
				return;
			}
		}
	}

	private static void shell(User user, Scanner inScan) {
		
		while(true){
			if(user.isAdmin()){
				System.out.print("> Enter a command: ");
				String command = inScan.nextLine();
				if(command.equalsIgnoreCase("view profile")){
					viewProfileAdmin(user.getUsername());
				}
				else if(command.equals("enroll student")){
					enrollStudent(inScan);
				}
				else if(command.equals("help")){
					System.out.println("cmdlist");
				}
				else if(command.equals("view student"))
				{
					viewstudent(inScan);
				}
				else if(command.equals("logout")){
					return;
				}
				else{
					System.out.println("> That command isn't recognized, enter 'help' for a list of commands.");
				}
			}
			else{
				//Student code
				return;
			}
		}
		
	}

	private static void enrollStudent(Scanner inScan) {
		
		System.out.println("> Please enter data to create a new student record.");
		System.out.print("> SID: ");
		String sid = inScan.nextLine();
		System.out.print("> Last Name: ");
		String lname = inScan.nextLine();
		System.out.print("> First Name: ");
		String fname = inScan.nextLine();
		System.out.print("> Email: ");
		String email = inScan.nextLine();
		System.out.print("> Password: ");
		String password = inScan.nextLine();
		System.out.print("> GPA: ");
		String gpa = inScan.nextLine();
		System.out.print("> Student Level: ");
		String sLevel = inScan.nextLine();
		System.out.print("> Minimum Credits: ");
		String minCred = inScan.nextLine();
		System.out.print("> Maximum Credits: ");
		String maxCred = inScan.nextLine();
		System.out.print("> Department: ");
		String did = inScan.nextLine();
		System.out.print("> Residency: ");
		String resLevel = inScan.nextLine();
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO STUDENT (SID, LNAME, FNAME, EMAIL, PASSWORD, GPA, SLEVEL, MINCREDITS, MAXCREDITS, DID, RESIDENCY) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
			ps.setString(1, sid);
			ps.setString(2, lname);
			ps.setString(3, fname);
			ps.setString(4, email);
			ps.setString(5, password);
			ps.setString(6, gpa);
			ps.setString(7, sLevel);
			ps.setString(8, minCred);
			ps.setString(9, maxCred);
			ps.setString(10, did);
			ps.setString(11, resLevel);
			ps.execute();
			System.out.println("Enrolled student.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Student could not be enrolled.");
			e.printStackTrace();
		}
		
	}

	private static void viewProfileAdmin(String username) {
		
		Connection conn = ConnectionManager.getConnectionInstance();
		String eid = "";
		String fname = "";
		String lname = "";
		String dob = "";
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ADMIN WHERE eid = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if( rs.next() ){
				eid = rs.getString("EID");
				fname = rs.getString("FNAME");
				lname = rs.getString("LNAME");
				dob = rs.getString("DOB");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		System.out.println("EID                 |FNAME               |LNAME               |SSN                 ");
		System.out.println(padRight("EID", 20) + "|" + padRight("FNAME", 20) + "|" + padRight("LNAME", 20) + "|" + padRight("DOB", 20) + "|");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(eid, 20)+ "|" + padRight(fname, 20) + "|" + padRight(lname, 20) + "|" + padRight(dob, 20) + "|");
		
//		System.out.println("EID: " + eid);
//		System.out.println("FNAME: " + fname);
//		System.out.println("LNAME: " + lname);
//		System.out.println("SSN: " + ssn);
		
		
	}
	private static void viewstudent(Scanner inscan)
	{
		Connection conn = ConnectionManager.getConnectionInstance();
		String sid = "";
		String fname = "";
		String lname = "";
		String email ="";
		String dob = "";
		String pwd = "";
		String gpa = "";
		String slevel = "";
		String bill = "";
		String did = "";
		String residency = "";
		String uname = "";
		int credits = 0;
		System.out.println("Please enter the student id: ");
		sid = inscan.nextLine();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM STUDENT WHERE sid = ?");
			ps.setString(1, sid);
			ResultSet rs = ps.executeQuery();
			if( rs.next() ){
				
				fname = rs.getString("FNAME");
				lname = rs.getString("LNAME");
				email = rs.getString("EMAIL");
				dob = rs.getString("DOB");
				pwd = rs.getString("PWD");
				gpa = rs.getString("GPA");
				slevel = rs.getString("SLEVEL");
				bill = rs.getString("BILL");
				did = rs.getString("DID");
				residency = rs.getString("RESIDENCY");
				uname = rs.getString("UNAME");
				credits = rs.getInt("CREDITS");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println(padRight("StudentID", 20) + "|" + padRight("Firstname", 20) + "|" + padRight("Lastname", 20) + "|" + padRight("DOB", 20) + "|" + padRight("eMAIL", 20) + "|" + padRight("GPA", 20));
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(sid, 20)+ "|" + padRight(fname, 20) + "|" + padRight(lname, 20) + "|" + padRight(dob, 20) + "|"+ padRight(email, 20) + "|"+ padRight(gpa, 20) + "|");
		
	}
	private static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);  
	}

}
