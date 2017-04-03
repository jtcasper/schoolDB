package schoolDB;

import java.sql.*;

import java.util.Date;
import java.text.ParseException;
import java.util.Scanner;
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
					viewstudent(inScan);
					
				}
				else if(command.equals("4"))//View/Add Courses
				{	newPage();
					System.out.print("# View/Add Course \n>Please enter a command:  ");
					System.out.print("\n  1.View A Course \n  2.Adding A New Course \n  3.View All Courses \n  0.Back  \n> ");
					String coursecmd = inScan.nextLine();
					while(!coursecmd.equals("0")){
					if(coursecmd.equals("1"))
						{readcourse(inScan);
						
						}
					else if(coursecmd.equals("2"))
					{
						addCourse(inScan);
					}
					if(coursecmd.equals("3"))
					{readallcourse(inScan);
					
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

						}
						else{
							invalidCommand();
						}
						System.out.print("# View/Approve Special Requests \n>Please enter a command:  ");
						System.out.print("\n  1.View Special Requests\n  2.Approve Special Requests \n  0.Back  \n>");
						coursecmd = inScan.nextLine();
					}
					
				}
				else if(command.equals("7")){//Enforce Add/Drop Deadline
					newPage();
					System.out.print("# Enforce Add/Drop Deadline\n>Please enter a command:  ");
					System.out.print("\n  Type Y to confirm to enforce semester's deadline. Or return to main menu \n>");
					String coursecmd = inScan.nextLine();
					if(coursecmd.equals("Y"))
					{	System.out.print("# Please enter the semester id:\n>  ");
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
						System.out.print("# View Profile\n>Please enter a command:  ");
						System.out.print("\n  1.View Profile\n  2.Edit Profile \n  0.Back  \n> ");
						coursecmd = inScan.nextLine();
					}
					else if(coursecmd.equals("2"))
					{
						editStudent(inScan, user.getUsername());////edit student profile
						System.out.print("# View Profile\n>Please enter a command:  ");
						System.out.print("\n  1.View Profile\n  2.Edit Profile \n  0.Back  \n> ");
						coursecmd = inScan.nextLine();
					}
					else{
						invalidCommand();
						System.out.print("# View Profile\n>Please enter a command:  ");
						System.out.print("\n  1.View Profile\n  2.Edit Profile \n  0.Back  \n> ");
						coursecmd = inScan.nextLine();
					}
					}
				}
				else if(command.equals("2"))//View Courses/Enroll/Drop Courses
				{
					newPage();
					System.out.print("# View Courses/Enroll/Drop Courses\n>Please enter a command:  ");
					System.out.print("\n  1.View Courses\n  2.Enroll a course \n  3.Drop a course\n  0.Back  \n> ");
					String coursecmd = inScan.nextLine();
					while(!coursecmd.equals("0")){
					if(coursecmd.equals("1"))
					{
						//view course
						viewOfferings(inScan);

						System.out.print("# View Courses/Enroll/Drop Courses\n>Please enter a command:  ");
						System.out.print("\n  1.View Courses\n  2.Enroll a course \n  3.Drop a course\n  0.Back  \n> ");
						coursecmd = inScan.nextLine();
					}
					else if(coursecmd.equals("2"))
					{
						//add a course
						System.out.print("# View Courses/Enroll/Drop Courses\n>Please enter a command:  ");
						System.out.print("\n  1.View Courses\n  2.Enroll a course \n  3.Drop a course\n  0.Back  \n> ");
						coursecmd = inScan.nextLine();
					}
					else if(coursecmd.equals("3"))
					{
						//drop a course 
						System.out.print("# View Courses/Enroll/Drop Courses\n>Please enter a command:  ");
						System.out.print("\n  1.View Courses\n  2.Enroll a course \n  3.Drop a course\n  0.Back  \n> ");
						coursecmd = inScan.nextLine();
					}
					else{
						invalidCommand();
						System.out.print("# View Courses/Enroll/Drop Courses\n>Please enter a command:  ");
						System.out.print("\n  1.View Courses\n  2.Enroll a course \n  3.Drop a course\n  0.Back  \n> ");
						coursecmd = inScan.nextLine();
						
					}
					}
				}
				else if(command.equals("3"))//View Pending Courses
				{
					newPage();
					System.out.print("# View Pending Courses \n");
					//view pending courses
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
						//viewstudent(inScan, 23);
						viewBill(inScan, user.getUsername());//view Bill
						System.out.print("# View/Pay Bill\n>Please enter a command:  ");
						System.out.print("\n  1.View Bill\n  2.Pay Bill \n   0.Back  \n> ");
						coursecmd = inScan.nextLine();
						}
					else if(coursecmd.equals("2"))
					{
						payBill(inScan, user.getUsername());
						System.out.print("# View/Pay Bill\n>Please enter a command:  ");
						System.out.print("\n  1.View Bill\n  2.Pay Bill \n   0.Back  \n> ");
						coursecmd = inScan.nextLine();
					}
					else{
						invalidCommand();
						System.out.print("# View/Pay Bill\n>Please enter a command:  ");
						System.out.print("\n  1.View Bill\n  2.Pay Bill \n   0.Back  \n> ");
						coursecmd = inScan.nextLine();
					}
					}
				}
				else if(command.equals("6"))//Logout
				{
					return;
				}
			}
		}
		
	}
	private static void invalidCommand()
	{
		System.out.print("***Invalid Command\n");

	}
	private static boolean enforcedeadline(String semid){//not finished
		Connection conn = ConnectionManager.getConnectionInstance();
		try{
			CallableStatement cStmt = conn.prepareCall("{call ENFORCE_DEADLINE(?,?)}");
			 cStmt.setString(1,semid);
			 cStmt.registerOutParameter(2, Types.BOOLEAN);
			
			 boolean hadResults = cStmt.execute();
			while(hadResults){
				ResultSet rs = cStmt.getResultSet();
				 hadResults = cStmt.getMoreResults();
			}
			boolean status = cStmt.getBoolean(2);
			System.out.println("Successfully enforce the deadline in " + semid);
			return status;
		} catch(SQLException e){
			System.out.println("Could not enforce deadline.");
			e.printStackTrace();
			return false;
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
			PreparedStatement ps = conn.prepareStatement("INSERT INTO OFFERS (SCHEDULE, LOCATION, CID, SEMID, FID, CLASSSIZE, WAITSIZE) "
					+ "VALUES( ?, ?, ?, ?, ?, ?, ?)");
			
			ps.setString(1, schedule);
			ps.setString(2, location);
			ps.setString(3, cid);
			ps.setString(4, semID);
			ps.setString(5, facultyID);
			ps.setString(6, classSize);
			ps.setString(7, waitSize);
			ps.execute();
			System.out.println("Successfully added course offering: " + cid);
		} catch(SQLException e){
			System.out.println("Could not create course offering.");
			e.printStackTrace();
		}

		
	}

	private static void viewOfferings(Scanner inScan) {
		
		System.out.println("> Please enter the course ID to view offerings for: ");
		String semID = "";
		
		
		String schedule = "";
		String location = "";
		String cid = inScan.nextLine();
		String facultyID = "";
		String classSize = "";
		String waitSize = "";
		
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try{
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM OFFERS WHERE CID = ?");
			ps.setString(1, cid);
			ResultSet rs = ps.executeQuery();
			while( rs.next() ) {
				
				schedule = rs.getString("SCHEDULE");
				location = rs.getString("LOCATION");
				semID = rs.getString("SEMID");
				facultyID = rs.getString("FID");
				classSize = rs.getString("CLASSSIZE");
				waitSize = rs.getString("WAITSIZE");
				
				System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println(padRight("Course ID", 20) + "|"+ padRight("Semester", 20) + "|" + padRight("Schedule", 20) + "|" + padRight("Location", 20) + "|" + padRight("Faculty ID", 20) + "|" + padRight("Class Size", 20) + "|" + padRight("Waitlist Size", 20));
				System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println( padRight(cid, 20) + "|"+ padRight(semID, 20) + "|" + padRight(schedule, 20) + "|" + padRight(location, 20) + "|" + padRight(facultyID, 20) + "|" + padRight(classSize, 20) + "|" + padRight(waitSize, 20));
				
			}

			
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
			
	}
	private static void studentProfile(Scanner inScan, String username) {
		
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
				uname = rs.getString("UNAME");
				credits = rs.getInt("CREDITS");
			}
			
		} catch (SQLException e){
			System.out.println("Error retrieving profile details.");
			return;
		}
		
		newPage();
		System.out.println(padRight("StudentID", 10) + "|" + padRight("Firstname", 15) + "|" + padRight("Lastname", 15) + "|" + padRight("DOB", 28) + "|" +padRight("email", 20) + "|" + padRight("status", 10) + "|" + padRight("Level", 12) + "|" + padRight("Department", 10) + "|" + padRight("Bill Amount", 12)+ "|"+ padRight("Enrolled Course & Grades", 20)+ "|");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(username, 10)+ "|" + padRight(fname, 15) + "|" + padRight(lname, 15) + "|" + padRight(dob, 28)+ "|" + padRight(email, 20)+ "|"+ padRight(residency, 10) + "|"+ padRight(slevel, 12) + "|"+ padRight(did, 10) + "|"+ padRight(bill, 12) + "|"+ padRight(null, 10) + "|");
		
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
			PreparedStatement ps = conn.prepareStatement("UPDATE STUDENT SET email = ?, PWD = ?, PHONE_NUMBER= ?, ADDRESS = ?,  WHERE SID = ?");
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

	private static void addCourse(Scanner inScan) {
		
		System.out.println("> Please enter data to create a new course.");
		System.out.print("> Course ID: ");
		String cid = inScan.nextLine();
		System.out.print("> Course Title: ");
		String title = inScan.nextLine();
		System.out.print("> Credits: ");
		String credits = inScan.nextLine();
		System.out.print("> Course Level:\n1.UG\n2.PG\n> ");
		String cLevel = "";
		String cLevel2 = inScan.nextLine();
		while(!(cLevel2.equals("1")||cLevel2.equals("1")))
		{System.out.println("###error input\n");
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
		System.out.print("> Precondition: Please select the option:\n1.Adding Prerequisite course.\n2.Adding required gpa.\n3.Adding special permision requirement.\n0.Exit\n>");
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
			System.out.print("> Precondition: Please select the option:\n1.Adding Prerequisite course.\n2.Adding required gpa.\n3.Adding special permission requirement.\n0.Exit\n>");
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
		System.out.print("> DOB: **Please keep the format as dd/mm/yyyy:\n>");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		String dob = inScan.nextLine();
		/*Date dob2=null;
		try{dob2=formatter.parse(dob);}
		catch (ParseException e){
			e.printStackTrace();
		}*/
		System.out.print("> Email: ");
		String email = inScan.nextLine();
		//System.out.print("> Password: ");
		String password = "password";
		//System.out.print("> GPA: ");
		//String gpa = inScan.nextLine();
		System.out.print("> Student Level: Please select the option: \n1.Undergraduate\n 2. Graduate\n>");
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
		else if(sLevel2.equalsIgnoreCase("2")){
			sLevel ="PG";
		}
		System.out.print("> Department: ");
		String did = inScan.nextLine();
		System.out.print("> Residency: Please select the option: \n1.In-State  \n2.Out of State\n3.International\n>");
		String resLevel = "";
		String resLevel2 = inScan.nextLine();
		while(!(resLevel2.equals("1")||resLevel2.equals("2")||resLevel2.equals("3"))){
			System.out.print("###Error input.\n> Residency: Please select the option: \n1.In-State  \n2.Out of State\n3.International\n>");
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
		Connection conn = ConnectionManager.getConnectionInstance();
		
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO STUDENT (SID, FNAME, LNAME,DOB, EMAIL,PWD, SLEVEL, DID, RESIDENCY) "
					+ "VALUES (?, ?, ?, ? ?, ?, ?, ?, ?) ");
			//PreparedStatement ps = conn.prepareStatement("INSERT INTO STUDENT (SID, FNAME, LNAME,DOB, EMAIL,PWD, SLEVEL, DID, RESIDENCY) "
			//		+ "VALUES ('444','123','45','12/23/1666','321321','password','UG','CSC','In-State') ");
			 //CallableStatement ps = conn.prepareCall("{call ENROLL_STUDENT(?, ?,?,?,?,?,?,?)}");
			//PreparedStatement ps = conn.prepareStatement("INSERT INTO STUDENT (SID, LNAME, FNAME, EMAIL, GPA, SLEVEL, DID, RESIDENCY) "
			//		+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
			ps.setString(1, sid);
			ps.setString(2, fname);
			ps.setString(3, lname);
			ps.setDate(4, java.sql.Date.valueOf(dob));//TODO change the dob
			ps.setString(5, email);
			ps.setString(6, password);
			ps.setString(7, sLevel);
			ps.setString(8, did);
			ps.setString(9, resLevel);
			ps.execute();
			System.out.println("Student has been sucessfully enrolled");
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
		newPage();
//		System.out.println("EID                 |FNAME               |LNAME               |SSN                 ");
		System.out.println(padRight("StudentID", 20) + "|" + padRight("Firstname", 20) + "|" + padRight("Lastname", 20) + "|" + padRight("DOB", 20) + "|");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(eid, 20)+ "|" + padRight(fname, 20) + "|" + padRight(lname, 20) + "|" + padRight(dob, 20) + "|");
		
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
		//admin view the basic profile of student
			newPage();
		System.out.println(padRight("StudentID", 10) + "|" + padRight("Firstname", 15) + "|" + padRight("Lastname", 15) + "|" + padRight("DOB", 30) + "|" + padRight("eMAIL", 20) + "|" + padRight("GPA", 10) + "|" + padRight("Level", 10) + "|" + padRight("Residency", 10)+ "|");
		System.out.println("-----------------------------------------------------------------------------------");
		System.out.println(padRight(sid, 10)+ "|" + padRight(fname, 15) + "|" + padRight(lname, 15) + "|" + padRight(dob, 30) + "|"+ padRight(email, 20) + "|"+ padRight(gpa, 10) + "|"+ padRight(slevel, 10) + "|"+ padRight(residency, 10) + "|");
		
	
		//{	newPage();
			//System.out.println(padRight("StudentID", 10) + "|" + padRight("Firstname", 15) + "|" + padRight("Lastname", 15) + "|" + padRight("DOB", 30) + "|" + padRight("eMAIL", 20) + "|" + padRight("GPA", 10));
			//System.out.println("-----------------------------------------------------------------------------------");
			//System.out.println(padRight(sid, 10)+ "|" + padRight(fname, 15) + "|" + padRight(lname, 15) + "|" + padRight(dob, 30) + "|"+ padRight(email, 20) + "|"+ padRight(gpa, 10) + "|");
			//System.out.println(padRight("StudentID", 10) + "|" + padRight("Firstname", 15) + "|" + padRight("Lastname", 15) + "|" + padRight("DOB", 30) + "|" + padRight("eMAIL", 20) + "|" + padRight("GPA", 10) + "|" + padRight("Level", 10) + "|"+ padRight("Residency", 10)+ "|");
			//System.out.println("-----------------------------------------------------------------------------------");
			//System.out.println(padRight(sid, 10)+ "|" + padRight(fname, 15) + "|" + padRight(lname, 15) + "|" + padRight(dob, 30) + "|"+ padRight(email, 20) + "|"+ padRight(gpa, 10) + "|"+ padRight(slevel, 10) + "|"+ padRight(residency, 10) + "|");
		//}
		//else if(a==23)
		//{	newPage();
		//	System.out.println(padRight("StudentID", 20) + "|" + padRight("Firstname", 20) + "|" + padRight("Lastname", 20) + "|" + padRight("Bill", 10));
		//	System.out.println("-----------------------------------------------------------------------------------");
		//	System.out.println(padRight(sid, 20)+ "|" + padRight(fname, 20) + "|" + padRight(lname, 20) + "|" + padRight(bill, 10));
		//	
		//}
		
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
				System.out.println(padRight("sid", 10) + "|" +padRight("GPA", 10)+ "|" +padRight("courseID", 10) + "|" + padRight("Title", 40) + "|" + padRight("Credits", 10) + "|" + padRight("CourseLevel", 13) + "|" + padRight("Department", 10) + "|" + "|" +padRight("semID", 10));
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
			while( rs.next() ){
				cid = rs.getString("CID");
				title = rs.getString("TITLE");
				credits = rs.getString("CREDITS");
				clevel = rs.getString("CLEVEL");
				did = rs.getString("DID");
				System.out.println("-----------------------------------------------------------------------------------");
				System.out.println(padRight("courseID", 10) + "|" + padRight("Title", 40) + "|" + padRight("Credits", 10) + "|" + padRight("CourseLevel", 13) + "|" + padRight("Department", 10) + "|" );
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
