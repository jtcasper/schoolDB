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
			newPage();
			if(user.isAdmin()){
				System.out.print("> Please enter a command:  ");
				System.out.print("\n  1.View Profile \n  2.Enroll A New Student  ");
				System.out.print("\n  3.View Student's Details \n  4.View/Add Courses  ");
				System.out.print("\n  5.View/Add Courses Offering \n  6.View/Approve Special Requests  ");
				System.out.print("\n  7.Enforce Add/Drop Deadline \n  8.Logout  \n  9.Help\n>");
				String command = inScan.nextLine();
				if(command.equalsIgnoreCase("1")){//View profile
					newPage();
					System.out.print("# View Profile\n  ");
					viewProfileAdmin(user.getUsername());
				}
				else if(command.equals("2")){//Enroll A New Student
					newPage();
					System.out.print("# Enroll A New Student\n  ");
					enrollStudent(inScan);
				}
				else if(command.equals("9")){
					System.out.println("cmdlist");
				}
				else if(command.equals("3"))//View Student's Details
				{	newPage();
				System.out.print("# View Student's Details\n  ");
					viewstudent(inScan, 11);
					
				}
				else if(command.equals("4"))//View/Add Courses
				{	newPage();
					System.out.print("# View/Add Course \n>Please enter a command:  ");
					System.out.print("\n  1.View Course \n  2.Adding A New Course  \n  0.Back  \n>");
					String coursecmd = inScan.nextLine();
					if(coursecmd.equals("1"))
						readcourse(inScan);
					else if(coursecmd.equals("2"))
					{}
				}
				else if(command.equals("5")){//View/Add Courses Offering
					newPage();
					System.out.print("# View/Add Course Offerings \n>Please enter a command:  ");
					System.out.print("\n  1.View Course Offering \n  2.Adding New Course Offering \n  0.Back  \n>");
					String coursecmd = inScan.nextLine();
					if(coursecmd.equals("1"))
					{
						//View Courses Offering
					}
					else if(coursecmd.equals("2"))
					{
						//Add Courses Offering
					}
					
				}	
				else if(command.equals("6")){//View/Approve Special Requests
					newPage();
					System.out.print("# View/Approve Special Requests \n>Please enter a command:  ");
					System.out.print("\n  1.View Special Requests\n  2.Approve Special Requests \n  0.Back  \n>");
					String coursecmd = inScan.nextLine();
					while(coursecmd.equals("0"))
					if(coursecmd.equals("1"))
					{
						// view special requests
						
						System.out.print("# View/Approve Special Requests \n>Please enter a command:  ");
						System.out.print("\n  1.View Special Requests\n  2.Approve Special Requests \n  0.Back  \n>");
						coursecmd = inScan.nextLine();
					}
					else if(coursecmd.equals("2"))
					{
						// approve/deny special requests
					}
					
				}
				else if(command.equals("7")){//Enforce Add/Drop Deadline
					newPage();
					System.out.print("# Enforce Add/Drop Deadline\n>Please enter a command:  ");
					System.out.print("\n  1.Add Deadline\n  2.Drop Deadline \n  0.Back  \n>");
					String coursecmd = inScan.nextLine();
					if(coursecmd.equals("1"))
					{
						//Add Deadline
					}
					else if(coursecmd.equals("2"))
					{
						//Drop Deadline
					}
				}
				else if(command.equals("8")){//Logout
					return;
				}
				else{
					System.out.println("> That command isn't recognized, enter 'help' for a list of commands.");
				}
			}
			else{
				System.out.print("> Please enter a command:  ");
				System.out.print("\n  1.View/Edit Profile \n  2.View Courses/Enroll/Drop Courses  ");
				System.out.print("\n  3.View Pending Courses \n  4.View Grades  ");
				System.out.print("\n  5.View/Pay Bill \n>");
			String command = inScan.nextLine();
				if(command.equals("1"))//View/edit profile
				{
					newPage();
					System.out.print("# View Profile\n>Please enter a command:  ");
					System.out.print("\n  1.View Profile\n  2.Edit Profile \n  0.Back  \n>");
					String coursecmd = inScan.nextLine();
					if(coursecmd.equals("1"))
					{
						viewstudent(inScan, 21);//student profile
						}
					else if(coursecmd.equals("2"))
					{
						//edit profile ddd
					}
				}
				else if(command.equals("view GPA"))
				{
					viewstudent(inScan,22);
				}
				else if(command.equals("view bill"))
				{
					viewstudent(inScan,23);
					
				}
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
			PreparedStatement ps = conn.prepareStatement("INSERT INTO STUDENT (SID, LNAME, FNAME, EMAIL, PASSWORD, GPA, SLEVEL, DID, RESIDENCY) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");
			ps.setString(1, sid);
			ps.setString(2, lname);
			ps.setString(3, fname);
			ps.setString(4, email);
			ps.setString(5, password);
			ps.setString(6, gpa);
			ps.setString(7, sLevel);
			ps.setString(8, did);
			ps.setString(9, resLevel);
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
		System.out.println(padRight("StudentID", 20) + "|" + padRight("Firstname", 20) + "|" + padRight("Lastname", 20) + "|" + padRight("DOB", 20) + "|");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(eid, 20)+ "|" + padRight(fname, 20) + "|" + padRight(lname, 20) + "|" + padRight(dob, 20) + "|");
		
//		System.out.println("EID: " + eid);
//		System.out.println("FNAME: " + fname);
//		System.out.println("LNAME: " + lname);
//		System.out.println("SSN: " + ssn);
		
		
	}
	private static void viewstudent(Scanner inscan, int a)
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
		if(a == 11){//admin view the basic profile of student
		System.out.println(padRight("StudentID", 10) + "|" + padRight("Firstname", 15) + "|" + padRight("Lastname", 15) + "|" + padRight("DOB", 30) + "|" + padRight("eMAIL", 20) + "|" + padRight("GPA", 10) + "|" + padRight("Level", 10) + "|" + padRight("Residency", 10)+ "|");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(sid, 10)+ "|" + padRight(fname, 15) + "|" + padRight(lname, 15) + "|" + padRight(dob, 30) + "|"+ padRight(email, 20) + "|"+ padRight(gpa, 10) + "|"+ padRight(slevel, 10) + "|"+ padRight(residency, 10) + "|");
		}
		else if(a ==21)//student view the basic profile of student
		{
			//System.out.println(padRight("StudentID", 10) + "|" + padRight("Firstname", 15) + "|" + padRight("Lastname", 15) + "|" + padRight("DOB", 30) + "|" + padRight("eMAIL", 20) + "|" + padRight("GPA", 10));
			//System.out.println("-----------------------------------------------------------------------------------");
			//System.out.println(padRight(sid, 10)+ "|" + padRight(fname, 15) + "|" + padRight(lname, 15) + "|" + padRight(dob, 30) + "|"+ padRight(email, 20) + "|"+ padRight(gpa, 10) + "|");
			System.out.println(padRight("StudentID", 10) + "|" + padRight("Firstname", 15) + "|" + padRight("Lastname", 15) + "|" + padRight("DOB", 30) + "|" + padRight("eMAIL", 20) + "|" + padRight("GPA", 10) + "|" + padRight("Level", 10) + "|"+ padRight("Residency", 10)+ "|");
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println(padRight(sid, 10)+ "|" + padRight(fname, 15) + "|" + padRight(lname, 15) + "|" + padRight(dob, 30) + "|"+ padRight(email, 20) + "|"+ padRight(gpa, 10) + "|"+ padRight(slevel, 10) + "|"+ padRight(residency, 10) + "|");
		}
		else if(a==22)//student view gpa and **grades
		{
			System.out.println(padRight("StudentID", 10) + "|" + padRight("GPA", 10));
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println(padRight(sid, 10) + "|" + padRight(gpa, 10));
			
		}
		else if(a==23)
		{
			System.out.println(padRight("StudentID", 10) + "|" + padRight("Bill", 10));
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println(padRight(sid, 10) + "|" + padRight(bill, 10));
			
		}
		
	}
	private static void readcourse(Scanner inscan)
	{
		Connection conn = ConnectionManager.getConnectionInstance();
		String cid = "";
		String title = "";
		String credits = "";
		String clevel = "";
		String did = "";
		String maxsize = "";
		String wsize = "";
		System.out.println("Please enter the course id: ");
		cid = inscan.nextLine();
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM COURSE WHERE cid = ?");
			ps.setString(1, cid);
			ResultSet rs = ps.executeQuery();
			if( rs.next() ){
				
				title = rs.getString("TITLE");
				credits = rs.getString("CREDITS");
				clevel = rs.getString("CLEVEL");
				did = rs.getString("DID");
				maxsize = rs.getString("MAXSIZE");
				wsize = rs.getString("WSIZE");
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(padRight("courseID", 10) + "|" + padRight("Title", 40) + "|" + padRight("Credits", 10) + "|" + padRight("CourseLevel", 10) + "|" + padRight("Department", 10) + "|" + padRight("CourseSize", 10) + "|");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(cid, 10)+ "|" + padRight(title, 40) + "|" + padRight(credits, 10) + "|" + padRight(clevel, 10) + "|"+ padRight(did, 10) + "|"+ padRight(maxsize, 10) + "|");
		//System.out.println(padRight(cid, 20)+ "|" + padRight(title, 20) + "|" + padRight(credits, 20) + "|" + padRight(clevel, 20) + "|");
	}
	private static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);  
	}
	private static void newPage()
	{
		System.out.println("---------------------------------------\n");
	}
}
