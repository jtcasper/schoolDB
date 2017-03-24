package schoolDB;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLHandler {
	
	private static SQLHandler instance;

	public static SQLHandler getInstance() {
		if (instance == null)
			instance = new SQLHandler();
		return instance;
	}

	private int numExecuted;
	
	private SQLHandler(){
		
	}
	
	public void executeSQLFile(String filepath) throws SQLException{
		try {
			executeSQL(parseSQLFile(filepath));
		} catch (FileNotFoundException e) {
			System.out.println("File " + filepath + " does not exist.");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executeSQL(List<String> queries) throws SQLException {
		Connection conn = ConnectionManager.getConnectionInstance();
//		long start = System.currentTimeMillis();
		for (String sql : queries) {
			numExecuted++;
			System.out.println(sql);
			Statement stmt = conn.createStatement();
			try {
				stmt.execute(sql);
			} catch (SQLException e) {
				throw new SQLException(e.getMessage() + " from executing: " + sql, e.getSQLState(), e.getErrorCode());
			} finally {
				stmt.close();
			}
		}
		System.out.println(numExecuted);
//		queryTimeTaken += (System.currentTimeMillis() - start);
//		conn.close();
	}

	private List<String> parseSQLFile(String filepath) throws FileNotFoundException, IOException{
		List<String> queries = new ArrayList<String>();
		
		BufferedReader reader = null;
		FileReader fileReader = null;
		
		try{
			fileReader = new FileReader(new File(filepath));
			reader = new BufferedReader(fileReader);
			String line = "";
			String currentQuery = "";
			while ((line = reader.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) == ';') {
						queries.add(currentQuery);
						currentQuery = "";
					} else
						currentQuery += line.charAt(i);
				}
			}
		}
		finally{
			try{
				if(!(fileReader == null)) fileReader.close();
			}
			finally{
				if(!(reader==null)) reader.close();
			}
		}
		
		return queries;
	}

}