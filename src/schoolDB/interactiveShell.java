package schoolDB;

import java.sql.*;

import java.util.Date;
import java.text.ParseException;
import java.util.Scanner;

import com.sun.xml.internal.bind.unmarshaller.InfosetScanner;

import oracle.jdbc.internal.OracleTypes;

import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.CallableStatement;
public class interactiveShell {
	
	public static void main(String[] args){
				
		//Get SQLHandler and construct DB
		SQLHandler handler = SQLHandler.getInstance();
		Scanner inScan = new Scanner(System.in);
		
		//try {
			//handler.executeSQLFile("scripts/dropTableProcedures.sql");
			//handler.executeSQLFile("scripts/setupdb.sql");
			//handler.executeSQLFile("scripts/createData.sql");
	//} catch(SQLException e){
		//	e.printStackTrace();
		//}
		
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
				//Login by username
				else {
					PreparedStatement pstmt = conn.prepareStatement("SELECT PASSWORD FROM ADMIN WHERE USERNAME = ?");
					pstmt.setString(1, username);
					ResultSet rset = pstmt.executeQuery();
					if( rset.next() ) {
						dbPW = rset.getString("password");
					}
				}
				if( !(dbPW.equals("")) && dbPW.toString().equals(password)){
					retString = "> Successfully logged in as " + username;
					success = true;
					admin = true;
				}
				//See if it is a student
				else {
					try {
						PreparedStatement stmt = conn.prepareStatement("SELECT PWD FROM STUDENT WHERE SID = ?");
						stmt.setString(1, username);
						ResultSet results = stmt.executeQuery();
						if( results.next() ){
							dbPW = results.getString("PWD");
						}
						if( !(dbPW.equals("")) && dbPW.equals(password)){
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
				System.out.print("\n  7.Enforce Add/Drop Deadline \n  8.Logout  \n  9.Help\n> ");
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
				{	
					newPage();
					System.out.print("# View Student's Details\n  ");
					System.out.print("> Please enter the student id: ");
					String sid = inScan.nextLine();
					viewstudent(sid);
					System.out.print("> Press 0 to go back\n> Press 1 to View/Enter Grades\n> ");
					String coursecmd = inScan.nextLine();
					while(!(coursecmd.equals("0"))){
						if(coursecmd.equals("1"))
							viewEnterGrades(inScan, sid);
						else 
							System.out.println("# Incorrect input.");
						System.out.print("> Press 0 to go back\n> Press 1 to View/Enter Grades\n> ");
						coursecmd = inScan.nextLine();
					}
					
				}
				else if(command.equals("4"))//View/Add Courses
				{	newPage();
					System.out.print("# View/Add Course \n>Please enter a command:  ");
					System.out.print("\n  1.View A Course \n  2.Adding A New Course \n  3.View All Courses \n  0.Back  \n> ");
					String coursecmd = inScan.nextLine();
					while(!coursecmd.equals("0")){
					if(coursecmd.equals("1"))
					{
						readcourse(inScan);
					}
					else if(coursecmd.equals("2"))
					{
						addCourse(inScan);
					}
					else if(coursecmd.equals("3"))
					{
						readallcourse(inScan);
					}
					else{
						invalidCommand();
					}
					System.out.print("# View/Add Course \n>Please enter a command:  ");
					System.out.print("\n  1.View Course \n  2.Adding A New Course  \n  3.View All Courses\n  0.Back  \n> ");
					coursecmd = inScan.nextLine();
					}
				}
				else if(command.equals("5")){//View/Add Courses Offering
					newPage();
					System.out.print("# View/Add Course Offerings \n>Please enter a command:  ");
					System.out.print("\n  1.View Course Offering \n  2.Adding New Course Offering \n  0.Back  \n> ");
					String coursecmd = inScan.nextLine();
					while(!coursecmd.equals("0"))
					{if(coursecmd.equals("1"))
					{
						viewOfferings(inScan);
					}
					else if(coursecmd.equals("2"))
					{
						addOfferings(inScan);
					}
					else{
						invalidCommand();
					}
					System.out.print("# View/Add Course Offerings \n>Please enter a command:  ");
					System.out.print("\n  1.View Course Offering \n  2.Adding New Course Offering \n  0.Back  \n> ");
					coursecmd = inScan.nextLine();
					}
				}	
				else if(command.equals("6")){//View/Approve Special Requests
					newPage();
					System.out.print("# View/Approve Special Requests \n>Please enter a command:  ");
					System.out.print("\n  1.View Special Requests\n  2.Approve Special Requests \n  0.Back  \n>");
					String coursecmd = inScan.nextLine();
					while(!(coursecmd.equals("0"))){
						if(coursecmd.equals("1"))
						{
							viewpending2();// view special requests

							
						}
						else if(coursecmd.equals("2"))
						{
							// approve/deny special requests
							System.out.println("> Entering data to approve or deny a special request. ");
							System.out.print("> Student ID: ");
							String sid = inScan.nextLine();
							System.out.print("> Course ID: ");
							String cid = inScan.nextLine();
							System.out.print("> Session ID: ");
							String sessID = inScan.nextLine();
							System.out.print("> Semester ID: ");
							String semID = inScan.nextLine();
							System.out.print("> Should student request be approved? (Approve/Deny): ");
							String decision = inScan.nextLine();
							while(!(decision.equalsIgnoreCase("approve")) && !(decision.equalsIgnoreCase("deny"))){
								System.out.print("#### ERRONEOUS INPUT\n> Should student request be approved? (Approve/Deny): ");
								decision = inScan.nextLine();
							}
							approveDenyPending(sid, cid, semID, decision, sessID);
						}
						else{
							invalidCommand();
						}
						System.out.print("# View/Approve Special Requests \n>Please enter a command:  ");
						System.out.print("\n  1. View Special Requests\n  2. Approve Special Requests \n  0. Back  \n> ");
						coursecmd = inScan.nextLine();
					}
					
				}
				else if(command.equals("7")){//Enforce Add/Drop Deadline
					newPage();
					System.out.print("# Enforce Add/Drop Deadline\n>Please enter a command:  ");
					System.out.print("\n  Type Y to confirm to enforce semester's deadline. Or return to main menu \n>");
					String coursecmd = inScan.nextLine();
					if(coursecmd.equals("Y"))
					{
						System.out.print("# Please enter the semester id:\n>  ");
						String semid=inScan.nextLine();
						//enforce a deadline
						enforcedeadline(semid);
					}
					
				}
				else if(command.equals("8")){//Logout
					return;
				}
				else{
					System.out.println("> That command isn't recognized, enter '9' for a list of commands.");
				}
			}
/////////////////Student user part			
			else{//Student code
				System.out.print("> Please enter a command:  ");
				System.out.print("\n  1.View/Edit Profile \n  2.View Courses/Enroll/Drop Courses  ");
				System.out.print("\n  3.View Pending Courses \n  4.View Grades  ");
				System.out.print("\n  5.View/Pay Bill\n  6.Logout\n> ");
			String command = inScan.nextLine();
				if(command.equals("1"))//View/edit profile
				{
					newPage();
					System.out.print("# View Profile\n>Please enter a command:  ");
					System.out.print("\n  1.View Profile\n  2.Edit Profile \n  0.Back  \n> ");
					String coursecmd = inScan.nextLine();
					while(!coursecmd.equals("0")){
					if(coursecmd.equals("1"))
					{
						studentProfile(inScan, user.getUsername());//student profile
						
					}
					else if(coursecmd.equals("2"))
					{
						editStudent(inScan, user.getUsername());////edit student profile
						
					}
					else{
						invalidCommand();
						
					}
					System.out.print("# View Profile\n>Please enter a command:  ");
					System.out.print("\n  1.View Profile\n  2.Edit Profile \n  0.Back  \n> ");
					coursecmd = inScan.nextLine();
					}
				}
				else if(command.equals("2"))//View Courses/Enroll/Drop Courses
				{
					newPage();
					System.out.print("# View Courses/Enroll/Drop Courses\n>Please enter a command:  ");
					System.out.print("\n  1.View Courses\n  2.Enroll a course \n  3.Drop a course\n  4.Show all my courses\n  0.Back  \n> ");
					String coursecmd = inScan.nextLine();
					while(!coursecmd.equals("0")){
					if(coursecmd.equals("1"))
					{
						//view course
						viewOfferings(inScan);
					}
					else if(coursecmd.equals("2"))
					{
						//add a course
						enrollCourseStudent(inScan, user.getUsername());
					}
					else if(coursecmd.equals("3"))
					{
						//drop a course
						dropCourseStudent(inScan, user.getUsername());
					}
					else if(coursecmd.equals("4"))
					{	
						showmycourses(user.getUsername());
						//show all my courses
					}
					else{
						invalidCommand();
					}
					System.out.print("# View Courses/Enroll/Drop Courses\n>Please enter a command:  ");
					System.out.print("\n  1.View Courses\n  2.Enroll a course \n  3.Drop a course\n  4.Show all my courses\n  0.Back  \n> ");
					coursecmd = inScan.nextLine();
					}
				}
				else if(command.equals("3"))//View Pending Courses
				{
					newPage();
					System.out.print("# View Pending Courses \n");
					viewmypending(user.getUsername());//view pending courses
				}
				else if(command.equals("4"))//View Grades
				{
					newPage();
					System.out.print("# View Grades \n");
					viewGPA(inScan, user.getUsername());//view GPA, implement student with course grades later
				}
				else if(command.equals("5"))//View/Pay Bills
				{
					newPage();
					System.out.print("# View/Pay Bill\n>Please enter a command:  ");
					System.out.print("\n  1.View Bill\n  2.Pay Bill \n   0.Back  \n> ");
					String coursecmd = inScan.nextLine();
					while(!coursecmd.equals("0")){
					if(coursecmd.equals("1"))
					{
						viewBill(inScan, user.getUsername());//view Bill
						
						}
					else if(coursecmd.equals("2"))
					{
						payBill(inScan, user.getUsername());
					}
					else{
						invalidCommand();
					}
					System.out.print("# View/Pay Bill\n>Please enter a command:  ");
					System.out.print("\n  1.View Bill\n  2.Pay Bill \n   0.Back  \n> ");
					coursecmd = inScan.nextLine();
					}
				}
				else if(command.equals("6"))//Logout
				{
					return;
				}
			}
		}
		
	}
	private static void viewEnterGrades(Scanner inScan, String sid) {
		
		showmycourses(sid);
		System.out.print("> Enter grades (Y/N)? ");
		String response = inScan.nextLine();
		if(!(response.equalsIgnoreCase("Y")))
			return;
		System.out.println("> Please choose the offering to enter grades for");
		System.out.print("> Enter Course ID for grade: ");
		String cid = inScan.nextLine();
//		System.out.print("> Enter Session ID for grade: ");
//		String sessID = inScan.nextLine();
		System.out.print("> Enter Semester ID for grade: ");
		String semID = inScan.nextLine();
		System.out.print("> Enter Grade for course: ");
		String grade = inScan.nextLine();
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try{
			PreparedStatement ps = conn.prepareStatement("UPDATE TAKES SET GRADE = ? WHERE SID = ? AND CID = ? AND SEMID = ?");
			ps.setString(1, grade);
			ps.setString(2, sid);
			ps.setString(3, cid);
			ps.setString(4, semID);
			ps.executeUpdate();
			System.out.println("Successfully updated grade.");
		} catch (SQLException e) {
			System.out.println("Failed to update grade.");
		}
		
		
		
		
		
		
	}

	private static void enrollCourseStudent(Scanner inScan, String username) {
		
		System.out.println("> Entering data for course registration.");
		System.out.print("> Please enter the Course ID of the course offering: ");
		String offerCID = inScan.nextLine();
		System.out.print("> Please enter the Semester ID of the course offering: ");
		String offerSemID = inScan.nextLine();
		System.out.print("> Please enter the Session ID of the course offering (defaulted:1): ");
		String offerSessionID = inScan.nextLine();
		String offerCreds = "";
		String statusOut = "";
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try{
			PreparedStatement ps = conn.prepareStatement("SELECT CREDITS FROM COURSE WHERE CID = ?");
			ps.setString(1, offerCID);
			ResultSet rs = ps.executeQuery();
			if( rs.next() ){
				
				offerCreds = rs.getString("CREDITS");
				if(offerCreds.length()==3)
				{
					System.out.println("This course is a special course, please enter the credit number you want to take: "+ offerCreds);
					offerCreds = inScan.nextLine();
				}
			}
		} catch(SQLException e) {
			System.out.println("Course offering not found.");
			e.printStackTrace();
			return;
		}
		
		/*System.out.println(username);
		System.out.println(offerCID);
		System.out.println(offerSemID);
		System.out.println(offerCreds);
		System.out.println(offerSessionID);*/
		
		try{
			CallableStatement cs = conn.prepareCall("{call ENROLL_COURSE(?, ?, ?, ?, ?, ?)}");
			cs.setString(1, username);
			cs.setString(2, offerCID);
			cs.setString(3, offerSemID);
			cs.registerOutParameter(4, OracleTypes.VARCHAR);
			cs.setInt(5, Integer.parseInt(offerCreds));
			cs.setString(6, offerSessionID);
			cs.executeUpdate();
			statusOut = cs.getString(4);
		} catch(SQLException e){
			System.out.println("Error in database call.");
			e.printStackTrace();
		}
		
		System.out.println("> Enrollment Status: " + statusOut);

		
	}

	private static void dropCourseStudent(Scanner inScan, String username) {
		
		System.out.println("> Current courses.");
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try{
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TAKES WHERE SID = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			System.out.println(padRight("Course ID", 20) + "|" + padRight("Grade", 5) + "|" + padRight("Credits", 10) + "|" + padRight("Status", 15) + "|" + padRight("Semester ID", 20) + "|");
			System.out.println("----------------------------------------------------------------------");
			while( rs.next() ){
				String cid = rs.getString("CID");
				String grade = rs.getString("GRADE");
				String credits = rs.getString("CREDITS");
				String status = rs.getString("STATUS");
				String semID = rs.getString("SEMID");
				System.out.println(padRight(cid, 20) + "|" + padRight(grade, 5) + "|" + padRight(credits, 10) + "|" + padRight(status, 15) + "|" + padRight(semID, 20) + "|");
			}
		} catch (SQLException e) {
			System.out.println("Could not retrieve taken courses for student.");
			e.printStackTrace();
		}
		
		System.out.println("> Enter the Course ID and Semester ID of the course you would like to drop.");
		System.out.print("> Course ID: ");
		String cid = inScan.nextLine();
		System.out.print("> Semester ID: ");
		String semid = inScan.nextLine();
		
		try{
			CallableStatement cs = conn.prepareCall("{call DROP_COURSE(?, ?, ?)}");
			cs.setString(1, cid);
			cs.setString(2, username);
			cs.setString(3, semid);
			cs.execute();
			System.out.println("Successfully dropped course " + cid + " in semester " + semid);
		} catch(SQLException e) {
			System.out.println("Could not drop course " + cid + " in semester " + semid);
			e.printStackTrace();
		}
		
	}

	private static void approveDenyPending(String sid, String cid, String semid, String decision, String sessID) {
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		String decisionString = "";
		
		if(decision.equalsIgnoreCase("approve")){
			decisionString = "Confirmed";
		} else {
			decisionString = "Denied";
		}
		
		try{
			int count = 0;
			PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM TAKES WHERE CID = ?  AND SEMID = ? AND SESSIONID = ?");
			ps.setString(1, cid);
			//ps.setString(2, sid);
			ps.setString(2, semid);
			ps.setString(3, sessID);
			ResultSet rs = ps.executeQuery();
			if( rs.next() ){
				count = rs.getInt(1);
				try{
					PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM OFFERS WHERE CID = ? AND SEMID = ? AND SESSIONID = ?");
					pstmt.setString(1, cid);
					pstmt.setString(2, semid);
					pstmt.setString(3, sessID);
					ResultSet rset = pstmt.executeQuery();
					if( rset.next() ){
						int classSize = rset.getInt("CLASSSIZE");
						int waitSize = rset.getInt("WAITSIZE");
						if( count > classSize )
							decisionString = "Pending";
						if(count > classSize + waitSize)
							decisionString = "Denied";
					}
				} catch(SQLException e){
					System.out.println("Error while checking waitlist size.");
				}
			}
				
		} catch(SQLException e){
			System.out.println("Could not find student takes instance to approve permission");
		}
		
		try{
			PreparedStatement ps = conn.prepareStatement("UPDATE TAKES SET STATUS = ? WHERE CID = ? AND SID = ? AND SEMID = ? AND SESSIONID = ?");
			ps.setString(1, decisionString);
			ps.setString(2, cid);
			ps.setString(3, sid);
			ps.setString(4, semid);
			ps.setString(5, sessID);
			ps.executeUpdate();
			System.out.println("Successfully updated status.");
		} catch(SQLException e){
			System.out.println("Could not update status for CID/SID/SEMID combination.");
		}
		
	}

	private static void invalidCommand()
	{
		System.out.print("***Invalid Command\n");

	}
	private static void enforcedeadline(String semid){//not finished
		Connection conn = ConnectionManager.getConnectionInstance();
		try{
			CallableStatement cStmt = conn.prepareCall("{call ENFORCE_DEADLINE(?,?)}");
			 cStmt.setString(1,semid);
			 cStmt.registerOutParameter(2, Types.VARCHAR);
			
			 boolean hadResults = cStmt.execute();
			 while(hadResults){
				 ResultSet rs = cStmt.getResultSet();
				 hadResults = cStmt.getMoreResults();
			}
			String status = cStmt.getString(2);
			System.out.println("Successfully enforce the deadline in " + semid);
		} catch(SQLException e){
			System.out.println("Could not enforce deadline.");
			e.printStackTrace();
		}

		
	}
	private static void payBill(Scanner inScan, String username) {
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		String billAmt = "";
		int intBillAmt = 0;
		String payAmt = "";
		int intPayAmt = 0;
		
		try{
			PreparedStatement ps = conn.prepareStatement("SELECT BILL FROM STUDENT WHERE SID = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if( rs.next() ){
				billAmt = rs.getString("BILL");
				intBillAmt = Integer.parseInt(billAmt);
			}
			if(intBillAmt == 0){
				System.out.println("> Current bill is $0, no need to pay.");
				return;
			} else {
				System.out.println("> Current bill amount is $" + intBillAmt + ".");
				System.out.print("> Enter amount to be paid: ");
				payAmt = inScan.nextLine();
				intPayAmt = Integer.parseInt(payAmt);
				if(intBillAmt - intPayAmt < 0){
					System.out.println("Overpaid by $" + -(intBillAmt - intPayAmt) + " the difference will be refunded.");
				}
				try{
					PreparedStatement payStmt = conn.prepareStatement("UPDATE STUDENT SET BILL = ? WHERE SID = ?");
					int diff = intBillAmt - intPayAmt;
					if(diff < 0){
						diff = 0;
					}
					String diffString = Integer.toString(diff);
					payStmt.setString(1, diffString);
					payStmt.setString(2, username);
					payStmt.executeUpdate();
				} catch(SQLException e) {
					System.out.println("Could not pay bill for user " + username + " at this time.");
				}
			}
		} catch(SQLException e){
			System.out.println("Could not retrieve bill for user " + username);
		}
	}
		

	private static void addOfferings(Scanner inScan) {
		
		System.out.println("> Please enter data to create a course offering.");
		System.out.print("> Semester ID(Please follow the example format: F2016): ");
		String semID = inScan.nextLine();
		System.out.print("> Course ID: ");
		String cid = inScan.nextLine();
		System.out.print("> Session ID(defaulted as 1): ");
		String sessionid = inScan.nextLine();
		System.out.print("> Schedule: ");
		String schedule = inScan.nextLine();
		System.out.print("> Location: ");
		String location = inScan.nextLine();
		System.out.print("> Faculty ID: ");
		String facultyID = inScan.nextLine();
		System.out.print("> Class Size: ");
		String classSize = inScan.nextLine();
		System.out.print("> Waitlist Size: ");
		String waitSize = inScan.nextLine();
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try{
			PreparedStatement ps = conn.prepareStatement("INSERT INTO OFFERS (SCHEDULE, LOCATION, CID, SEMID, FID, CLASSSIZE, WAITSIZE, SESSIONID) "
					+ "VALUES( ?, ?, ?, ?, ?, ?, ?,?)");
			
			ps.setString(1, schedule);
			ps.setString(2, location);
			ps.setString(3, cid);
			ps.setString(4, semID);
			ps.setString(5, facultyID);
			ps.setString(6, classSize);
			ps.setString(7, waitSize);
			ps.setString(8, sessionid);
			ps.execute();
			System.out.println("Successfully added course offering: " + cid);
		} catch(SQLException e){
			System.out.println("Could not create course offering.");
			e.printStackTrace();
		}

		
	}

	private static void viewOfferings(Scanner inScan) {
		
		System.out.print("> Please enter the course ID to view offerings for (enter 'all' to see all offerings): \n>");
		String semID = "";
		
		
		String schedule = "";
		String location = "";
		String cid = inScan.nextLine();
		//String cid = "";
		String facultyID = "";
		String classSize = "";
		String waitSize = "";
		String sessionid = "";
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try{
			if(cid.equals("all")){
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM OFFERS");
				ResultSet rs = ps.executeQuery();
				System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println(padRight("Course ID", 20) + "|"+ padRight("Semester", 20)+ "|"+ padRight("SessionID", 10) + "|" + padRight("Schedule", 20) + "|" + padRight("Location", 20) + "|" + padRight("Faculty ID", 20) + "|" + padRight("Class Size", 20) + "|" + padRight("Waitlist Size", 20));
				while( rs.next() ) {
					
					schedule = rs.getString("SCHEDULE");
					location = rs.getString("LOCATION");
					semID = rs.getString("SEMID");
					cid= rs.getString("CID");
					facultyID = rs.getString("FID");
					classSize = rs.getString("CLASSSIZE");
					waitSize = rs.getString("WAITSIZE");
					sessionid = rs.getString("SESSIONID");
					
					System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
					System.out.println( padRight(cid, 20) + "|"+ padRight(semID, 20) + "|"+ padRight(sessionid, 10)+ "|" + padRight(schedule, 20) + "|" + padRight(location, 20) + "|" + padRight(facultyID, 20) + "|" + padRight(classSize, 20) + "|" + padRight(waitSize, 20));
					
				}
			}
			else{
				PreparedStatement ps = conn.prepareStatement("SELECT * FROM OFFERS WHERE CID = ?");
				ps.setString(1, cid);
				ResultSet rs = ps.executeQuery();
				System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println(padRight("Course ID", 20) + "|"+ padRight("Semester", 20)+ "|"+ padRight("SessionID", 10) + "|" + padRight("Schedule", 20) + "|" + padRight("Location", 20) + "|" + padRight("Faculty ID", 20) + "|" + padRight("Class Size", 20) + "|" + padRight("Waitlist Size", 20));
				while( rs.next() ) {
					
					schedule = rs.getString("SCHEDULE");
					location = rs.getString("LOCATION");
					semID = rs.getString("SEMID");
					cid= rs.getString("CID");
					facultyID = rs.getString("FID");
					classSize = rs.getString("CLASSSIZE");
					waitSize = rs.getString("WAITSIZE");
					sessionid = rs.getString("SESSIONID");
					
					System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
					System.out.println( padRight(cid, 20) + "|"+ padRight(semID, 20) + "|"+ padRight(sessionid, 10)+ "|" + padRight(schedule, 20) + "|" + padRight(location, 20) + "|" + padRight(facultyID, 20) + "|" + padRight(classSize, 20) + "|" + padRight(waitSize, 20));
					
				}
			}
			//PreparedStatement ps = conn.prepareStatement("SELECT * FROM OFFERS WHERE CID = ?");
			//PreparedStatement ps = conn.prepareStatement("SELECT * FROM OFFERS");
			//ps.setString(1, cid);
			//ResultSet rs = ps.executeQuery();
			

			
		} catch(SQLException e) {
			System.out.println("Error retrieving semester course offerings for semester " + semID);
		}
		
		
	}

	private static void viewBill(Scanner inScan, String username) {
		
		String fname = "";
		String lname = "";
		String bill = "";
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try{
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM STUDENT WHERE SID = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if( rs.next() ){
				fname = rs.getString("FNAME");
				lname = rs.getString("LNAME");
				bill = rs.getString("BILL");
			}
		} catch (SQLException e){
			System.out.println("Error retrieving BILL.");
			return;
		}
		
		newPage();
		System.out.println(padRight("StudentID", 20) + "|" + padRight("Firstname", 20) + "|" + padRight("Lastname", 20) + "|" + padRight("BILL", 10));
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(username, 20)+ "|" + padRight(fname, 20) + "|" + padRight(lname, 20) + "|" + padRight(bill, 10));
			
	}
	private static void viewGPA(Scanner inScan, String username) {
		
		String fname = "";
		String lname = "";
		String gpa = "";
		String cid = "";
		String sid = "";
		String semid = "";
		String grade = "";
		String credits = "";
		String title = "";
		String clevel = "";
		String did = "";
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try{
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM STUDENT WHERE SID = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if( rs.next() ){
				fname = rs.getString("FNAME");
				lname = rs.getString("LNAME");
				gpa = rs.getString("GPA");
			}
		} catch (SQLException e){
			System.out.println("Error retrieving GPA.");
			return;
		}
		
		newPage();
		System.out.println(padRight("StudentID", 20) + "|" + padRight("Firstname", 20) + "|" + padRight("Lastname", 20) + "|" + padRight("GPA", 10));
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(username, 20)+ "|" + padRight(fname, 20) + "|" + padRight(lname, 20) + "|" + padRight(gpa, 10));
		
		try{
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TAKES WHERE SID = ? AND STATUS = ?");
			ps.setString(1, username);
			ps.setString(2, "Graded");
			ResultSet rs = ps.executeQuery();
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println(padRight("StudentID", 20) + "|" +padRight("Grade", 20)+ "|" +padRight("CourseID", 20) + "|" + padRight("Title", 20) + "|" + padRight("Credits", 10) + "|" + padRight("CourseLevel", 13) + "|" + padRight("Department", 10) + "|"  +padRight("semID", 10) + "|");
			while( rs.next() ){
				cid = rs.getString("CID");
				sid = rs.getString("SID");
				semid = rs.getString("SEMID");
				grade = rs.getString("GRADE");
				credits = rs.getString("CREDITS");
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM COURSE WHERE cid = ?");
				ps1.setString(1, cid);
				ResultSet rs1 = ps1.executeQuery();
				if(rs1.next())
				{
					title = rs1.getString("TITLE");
					clevel = rs1.getString("CLEVEL");
					did = rs1.getString("DID");
				}
			
				System.out.println("-----------------------------------------------------------------------------------");
				System.out.println(padRight(sid, 20)+ "|"+padRight(grade, 10)+ "|"+padRight(cid, 20)+ "|" + padRight(title, 20) + "|" + padRight(credits, 10) + "|" + padRight(clevel, 13) + "|"+ padRight(did, 10) + "|"+ padRight(semid, 10) + "|");
			}
		} catch(SQLException e){
			System.out.println("Could not retrieve course grades.");
		}
			
	}
	private static void studentProfile(Scanner inScan, String username) {//student side
		
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
		String cid ="";
		String grade = "";
		String address = "";
		String phone = "";
		int credits = 0;
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try{
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM STUDENT WHERE SID = ?");
			ps.setString(1, username);
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
				credits = rs.getInt("CREDITS");
				address = rs.getString("ADDRESS");
				phone = rs.getString("PHONE_NUMBER");
			}
			PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM TAKES WHERE sid = ? AND (STATUS =? OR STATUS = ?) ");
			ps2.setString(1, username);
			ps2.setString(2, "Confirmed");//should be modified to Graded later
			ps2.setString(3, "Graded");
			ResultSet rs2 = ps2.executeQuery();
			System.out.println(padRight("StudentID", 10) + "|" + padRight("Firstname", 15) + "|" + padRight("Lastname", 15) + "|" + padRight("DOB", 28) + "|" +padRight("email", 20) + "|" + padRight("Residency", 15) + "|" + padRight("Level", 12) + "|" + padRight("Department", 10) + "|" + padRight("Bill Amount", 12)+ "|" + padRight("Credits", 10)+ "|"+ padRight("Address", 10)+ "|"+ padRight("Phone", 10)+ "|"+ padRight("Enrolled Course & Grades", 20)+ "|");
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.print("\n"+padRight(username, 10)+ "|" + padRight(fname, 15) + "|" + padRight(lname, 15) + "|" + padRight(dob, 28)+ "|" + padRight(email, 20)+ "|"+ padRight(residency, 15) + "|"+ padRight(slevel, 12) + "|"+ padRight(did, 10) + "|"+ padRight(bill, 12) + "|"+ padRight(String.valueOf(credits), 10) + "|"+ padRight(address, 10) + "|"+ padRight(phone, 10) +"|");
			while(rs2.next())
			{	
				cid = rs2.getString("CID");
				grade = rs2.getString("GRADE");
				System.out.print(cid + "(" + grade + ") ");
			}
			System.out.println();
		} catch (SQLException e){
			System.out.println("Error retrieving profile details.");
			return;
		}
		
		newPage();
		
		
	}
	private static void editStudent(Scanner inScan, String username) {
		
		System.out.println("> Please enter data to update your student profile.");
		System.out.print("> New E-mail: ");
		String email = inScan.nextLine();
		System.out.print("> Password: ");
		String pwd = inScan.nextLine();
		System.out.print("> Phone Number: ");
		String phone = inScan.nextLine();
		System.out.print("> Address: ");
		String address = inScan.nextLine();
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE STUDENT SET email = ?, PWD = ?, PHONE_NUMBER= ?, ADDRESS = ?  WHERE SID = ?");
			ps.setString(1, email);
			ps.setString(2, pwd);
			ps.setString(3, phone);
			ps.setString(4, address);
			ps.setString(5, username);
			
			ps.executeUpdate();
			System.out.println("Student profile updated.");
		} catch(SQLException e) {
			System.out.println("Student profile could not be updated.");
			e.printStackTrace();
		}
	}
	private static void showmycourses(String sid)
	{
		Connection conn = ConnectionManager.getConnectionInstance();
		String cid = "";
		String title = "";
		String credits = "";
		String clevel = "";
		String semid = "";
		String did = "";
		String grade = "";
		try {
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TAKES WHERE SID = ? AND (STATUS = ? OR STATUS = ?)");
			ps.setString(1, sid);
			ps.setString(2, "Confirmed");
			ps.setString(3, "Graded");
			ResultSet rs = ps.executeQuery();
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println(padRight("sid", 10) + "|" +padRight("Grade", 10)+ "|" +padRight("courseID", 10) + "|" + padRight("Title", 40) + "|" + padRight("Credits", 10) + "|" + padRight("CourseLevel", 13) + "|" + padRight("Department", 10) + "|"  +padRight("semID", 10) + "|");
			while( rs.next() ){
				cid = rs.getString("CID");
				sid = rs.getString("SID");
				semid = rs.getString("SEMID");
				grade = rs.getString("GRADE");
				credits = rs.getString("CREDITS");
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM COURSE WHERE cid = ?");
				ps1.setString(1, cid);
				ResultSet rs1 = ps1.executeQuery();
				if(rs1.next())
				{title = rs1.getString("TITLE");
				clevel = rs1.getString("CLEVEL");
				did = rs1.getString("DID");
				}
			
				System.out.println("-----------------------------------------------------------------------------------");
				System.out.println(padRight(sid, 10)+ "|"+padRight(grade, 10)+ "|"+padRight(cid, 10)+ "|" + padRight(title, 40) + "|" + padRight(credits, 10) + "|" + padRight(clevel, 13) + "|"+ padRight(did, 10) + "|"+ padRight(semid, 10) + "|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private static void addCourse(Scanner inScan) {
		
		System.out.println("> Please enter data to create a new course.");
		System.out.print("> Course ID: ");
		String cid = inScan.nextLine();
		System.out.print("> Course Title: ");
		String title = inScan.nextLine();
		System.out.print("> Credits(if it is a special course, please enter as the example: '1-3'): ");
		String credits = inScan.nextLine();
		System.out.print("> Course Level:\n1.UG\n2.PG\n> ");
		String cLevel = "";
		String cLevel2 = inScan.nextLine();
		while(!( cLevel2.equals("1") || cLevel2.equals("2") ))
		{
			System.out.println("###error input\n");
			System.out.print("> Course Level:\n1.UG\n2.PG\n> ");
			cLevel2 = inScan.nextLine();	
		}
		if(cLevel2.equals("1"))
		{
			cLevel="UG";
		}
		else if(cLevel2.equals("2"))
		{	
			cLevel="PG";
		}
		System.out.print("> Department ID: ");
		String did = inScan.nextLine();
		System.out.print("> Precondition: Please select the option:\n 1.Adding Prerequisite course.\n 2.Adding required gpa.\n 3.Adding special permission requirement.\n 0.Exit\n> ");
		String precondition = inScan.nextLine();
		String precid="";
		String pregpa="";
		String prespe="N";
		while(!precondition.equals("0"))
		{
			if(precondition.equals("1"))
				{
				System.out.println(">Please enter the prerequisite course id:");
				precid=inScan.nextLine();
				}
			else if(precondition.equals("2"))
			{
				System.out.println(">Please enter the required GPA:");
				pregpa=inScan.nextLine();
				float pgpa = Float.valueOf(pregpa);
				while(pgpa>4.33||pgpa<0)
				{
					errorin();
					System.out.println(">Please enter the required GPA:");
					pregpa=inScan.nextLine();
					pgpa = Float.valueOf(pregpa);
				}
			}
			else if(precondition.equals("3"))
			{	System.out.println(">Please select from the options: 1.adding special permission requirement.2.cancel the special permission requirement\n>");
				String prespe2=inScan.nextLine();
				while(!(prespe2.equals("1")||prespe2.equals("2")))
				{
					System.out.println(">Please select from the options: 1.adding special permission requirement.2.cancel the special permission requirement\n>");
					 prespe2=inScan.nextLine();
					if(prespe2.equals("1"))
					{
						prespe ="Y";
					}
					else if(prespe2.equals("2"))
					{
						prespe ="N";
					}
					else{
						System.out.println("###error input\n");
					}
				}
			prespe = "Y";
			}
			
			else
			{
				System.out.print("#error input\n");
				
			}
			System.out.print("> Precondition: Please select the option:\n 1.Adding Prerequisite course.\n 2.Adding required gpa.\n 3.Adding special permission requirement.\n 0.Exit\n> ");
			precondition = inScan.nextLine();
		}
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try{
			PreparedStatement ps = conn.prepareStatement("INSERT INTO COURSE (CID, TITLE, CREDITS, CLEVEL, DID) "
					+ "VALUES (?, ?, ?, ?, ?) " );
			ps.setString(1, cid);
			ps.setString(2, title);
			ps.setString(3, credits);
			ps.setString(4, cLevel);
			ps.setString(5, did);
			ps.execute();
			PreparedStatement ps2 = conn.prepareStatement("INSERT INTO PRECONDITION (CID, PRE_CID, GPA, SPPERM) "
					+ "VALUES (?, ?, ?, ?) " );
			ps2.setString(1, cid);
			ps2.setString(2, precid);
			ps2.setString(3, pregpa);
			ps2.setString(4, prespe);
			ps2.execute();
			System.out.println("Course " + cid + " was created.");
		} catch(SQLException e) {
			System.out.println("Course could not be created.");
			e.printStackTrace();
		}

	}

	private static void enrollStudent(Scanner inScan) {
		
		System.out.println("> Please enter data to create a new student record.");
		System.out.print("> SID: ");
		String sid = inScan.nextLine();
		System.out.print("> First Name: ");
		String fname = inScan.nextLine();
		System.out.print("> Last Name: ");
		String lname = inScan.nextLine();
		System.out.print("> DOB: **Please keep the format as dd/mm/yyyy:\n> ");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		String dob = inScan.nextLine();
		Date dobDate = null;
		try {
			dobDate = formatter.parse(dob);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		java.sql.Date sqlDobDate = null;
		if(dobDate != null) {
			sqlDobDate = new java.sql.Date(dobDate.getTime());
		}
		/*Date dob2=null;
		try{dob2=formatter.parse(dob);}
		catch (ParseException e){
			e.printStackTrace();
		}*/
		System.out.print("> Email: ");
		String email = inScan.nextLine();
		//System.out.print("> Password: ");
		String password = sid;
		//System.out.print("> GPA: ");
		//String gpa = inScan.nextLine();
		System.out.print("> Student Level: Please select the option: \n 1. Undergraduate\n 2. Graduate\n> ");
		String sLevel2 = inScan.nextLine();
		String sLevel="";
		while(!(sLevel2.equals("1")||sLevel2.equals("2")))
		{
			System.out.print("###Error input.\n Student Level: Please select the option: \n1.Undergraduate  \n2. Graduate\n>");
			sLevel2 = inScan.nextLine();
			
		}
		if(sLevel2.equals("1"))
		{
			sLevel = "UG";
		}
		else if(sLevel2.equals("2")){
			sLevel ="PG";
		}
		System.out.print("> Department: ");
		String did = inScan.nextLine();
		System.out.print("> Residency: Please select the option: \n 1.In-State  \n 2.Out of State\n 3.International\n> ");
		String resLevel = "";
		String resLevel2 = inScan.nextLine();
		while(!(resLevel2.equals("1")||resLevel2.equals("2")||resLevel2.equals("3"))){
			System.out.print("###Error input.\n> Residency: Please select the option: \n 1.In-State  \n 2.Out of State\n 3.International\n> ");
			resLevel2 = inScan.nextLine();
		}
		if(resLevel2.equals("1"))
		{
			resLevel = "In State";
		}
		else if(resLevel2.equals("2")){
			resLevel ="Out Osf State";
		}
		else if(resLevel2.equals("3")){
			
			resLevel ="International";
		}
		int amountOwed = 0;
		System.out.print("> Amount owed (if any): ");
		String amtOwedStr = inScan.nextLine();
		if(!(amtOwedStr.equals("")) && Integer.parseInt(amtOwedStr) > 0){
			amountOwed = Integer.parseInt(amtOwedStr);
		}

		Connection conn = ConnectionManager.getConnectionInstance();
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO STUDENT (SID, FNAME, LNAME,DOB, EMAIL,PWD, SLEVEL, DID, RESIDENCY, BILL) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
			//PreparedStatement ps = conn.prepareStatement("INSERT INTO STUDENT (SID, FNAME, LNAME,DOB, EMAIL,PWD, SLEVEL, DID, RESIDENCY) "
			//		+ "VALUES ('444','123','45','12/23/1666','321321','password','UG','CSC','In-State') ");
			 //CallableStatement ps = conn.prepareCall("{call ENROLL_STUDENT(?, ?,?,?,?,?,?,?)}");
			//PreparedStatement ps = conn.prepareStatement("INSERT INTO STUDENT (SID, LNAME, FNAME, EMAIL, GPA, SLEVEL, DID, RESIDENCY) "
			//		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
			ps.setString(1, sid);
			ps.setString(2, fname);
			ps.setString(3, lname);
			ps.setDate(4, sqlDobDate);
			ps.setString(5, email);
			ps.setString(6, password);
			ps.setString(7, sLevel);
			ps.setString(8, did);
			ps.setString(9, resLevel);
			ps.setInt(10, amountOwed);
			ps.execute();
			System.out.println("Student has been sucessfully enrolled");
		} catch (SQLException e) {
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
		String userN = "";
		
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ADMIN WHERE eid = ? OR username = ?");
			ps.setString(1, username);
			ps.setString(2, username);
			ResultSet rs = ps.executeQuery();
			if( rs.next() ){
				eid = rs.getString("EID");
				fname = rs.getString("FNAME");
				lname = rs.getString("LNAME");
				dob = rs.getString("DOB");
				userN = rs.getString("USERNAME");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		newPage();
		System.out.println(padRight("EID", 20) + "|" + padRight("Username", 20) + "|" + padRight("Firstname", 20) + "|" + padRight("Lastname", 20) + "|" + padRight("DOB", 20) + "|");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(eid, 20)+ "|" + padRight(userN, 20) + "|" + padRight(fname, 20) + "|" + padRight(lname, 20) + "|" + padRight(dob, 20) + "|");
		
	}
	private static void viewstudent(String sid)
	{
		Connection conn = ConnectionManager.getConnectionInstance();
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
		String address = "";
		String phone = "";
		int credits = 0;
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
				address = rs.getString("ADDRESS");
				phone = rs.getString("PHONE_NUMBER");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//admin view the basic profile of student
			newPage();
		System.out.println(padRight("StudentID", 10) + "|" + padRight("Firstname", 15) + "|" + padRight("Lastname", 15) + "|" + padRight("DOB", 30) + "|" + padRight("eMAIL", 20) + "|" + padRight("GPA", 10) + "|" + padRight("Level", 10) + "|" + padRight("Residency", 10)+ "|"+ padRight("Address", 10)+ "|"+ padRight("Phone", 10)+ "|");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(sid, 10)+ "|" + padRight(fname, 15) + "|" + padRight(lname, 15) + "|" + padRight(dob, 30) + "|"+ padRight(email, 20) + "|"+ padRight(gpa, 10) + "|"+ padRight(slevel, 10) + "|"+ padRight(residency, 10) + "|"+ padRight(address, 10) + "|"+ padRight(phone, 10) + "|");		
	}
	private static void viewmypending(String sid)
	{
		Connection conn = ConnectionManager.getConnectionInstance();
		String cid = "";
		String title = "";
		String credits = "";
		String clevel = "";
		String semid = "";
		String did = "";
		try {
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TAKES WHERE STATUS = ? AND SID = ?");
			ps.setString(1, "Pending");
			ps.setString(2, sid);
			ResultSet rs = ps.executeQuery();
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println(padRight("sid", 10) + "|" +padRight("courseID", 10) + "|" + padRight("Title", 40) + "|" + padRight("Credits", 10) + "|" + padRight("CourseLevel", 13) + "|" + padRight("Department", 10) + "|" +padRight("semID", 10));
			while( rs.next() ){
				cid = rs.getString("CID");
				semid = rs.getString("SEMID");
				credits = rs.getString("CREDITS");
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM COURSE WHERE cid = ?");
				ps1.setString(1, cid);
				ResultSet rs1 = ps1.executeQuery();
				if(rs1.next())
				{title = rs1.getString("TITLE");
				clevel = rs1.getString("CLEVEL");
				did = rs1.getString("DID");
				}
				
				System.out.println("-----------------------------------------------------------------------------------");
				System.out.println(padRight(sid, 10)+  "|"+padRight(cid, 10)+ "|" + padRight(title, 40) + "|" + padRight(credits, 10) + "|" + padRight(clevel, 13) + "|"+ padRight(did, 10) + "|"+ padRight(semid, 10) + "|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private static void viewpending2()
	{
		Connection conn = ConnectionManager.getConnectionInstance();
		String cid = "";
		String title = "";
		String credits = "";
		String clevel = "";
		String semid = "";
		String did = "";
		String sid = "";
		String gpa = "";
		try {
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM TAKES WHERE STATUS = ?");
			ps.setString(1, "Pending");
			ResultSet rs = ps.executeQuery();
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println(padRight("sid", 10) + "|" +padRight("GPA", 10)+ "|" +padRight("courseID", 10) + "|" + padRight("Title", 40) + "|" + padRight("Credits", 10) + "|" + padRight("CourseLevel", 13) + "|" + padRight("Department", 10) + "|" +padRight("semID", 10));
			while( rs.next() ){
				cid = rs.getString("CID");
				sid = rs.getString("SID");
				semid = rs.getString("SEMID");
				credits = rs.getString("CREDITS");
				PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM COURSE WHERE cid = ?");
				ps1.setString(1, cid);
				ResultSet rs1 = ps1.executeQuery();
				if(rs1.next())
				{title = rs1.getString("TITLE");
				clevel = rs1.getString("CLEVEL");
				did = rs1.getString("DID");
				}
				PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM STUDENT WHERE SID = ?");
				ps2.setString(1, sid);
				ResultSet rs2 = ps2.executeQuery();
				if( rs2.next() ){
					gpa= rs2.getString("GPA");
				}
				
				System.out.println("-----------------------------------------------------------------------------------");
				System.out.println(padRight(sid, 10)+ "|"+padRight(gpa, 10)+ "|"+padRight(cid, 10)+ "|" + padRight(title, 40) + "|" + padRight(credits, 10) + "|" + padRight(clevel, 13) + "|"+ padRight(did, 10) + "|"+ padRight(semid, 10) + "|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		System.out.print("Please enter the course id: ");
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
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(padRight("courseID", 10) + "|" + padRight("Title", 40) + "|" + padRight("Credits", 10) + "|" + padRight("CourseLevel", 13) + "|" + padRight("Department", 10) + "|" );
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(cid, 10)+ "|" + padRight(title, 40) + "|" + padRight(credits, 10) + "|" + padRight(clevel, 13) + "|"+ padRight(did, 10) + "|");
		
	}
	private static void readallcourse(Scanner inscan)
	{
		Connection conn = ConnectionManager.getConnectionInstance();
		String cid = "";
		String title = "";
		String credits = "";
		String clevel = "";
		String did = "";

		try {
			
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM COURSE");

			ResultSet rs = ps.executeQuery();
			System.out.println("-----------------------------------------------------------------------------------");
			System.out.println(padRight("courseID", 10) + "|" + padRight("Title", 40) + "|" + padRight("Credits", 10) + "|" + padRight("CourseLevel", 13) + "|" + padRight("Department", 10) + "|" );
			while( rs.next() ){
				cid = rs.getString("CID");
				title = rs.getString("TITLE");
				credits = rs.getString("CREDITS");
				clevel = rs.getString("CLEVEL");
				did = rs.getString("DID");

				System.out.println("-----------------------------------------------------------------------------------");
				System.out.println(padRight(cid, 10)+ "|" + padRight(title, 40) + "|" + padRight(credits, 10) + "|" + padRight(clevel, 13) + "|"+ padRight(did, 10) + "|");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//System.out.println(padRight(cid, 20)+ "|" + padRight(title, 20) + "|" + padRight(credits, 20) + "|" + padRight(clevel, 20) + "|");
	}
	
	private static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);  
	}
	private static void newPage()
	{
		System.out.println("---------------------------------------\n");
	}
	private static void errorin()
	{
		System.out.println("###error input\n");
	}
}
