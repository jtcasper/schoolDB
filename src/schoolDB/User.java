package schoolDB;

public class User {
	
	private boolean adminStatus;
	private String username;
	
	public User(String username, boolean adminStatus){
		this.username = username;
		this.adminStatus = adminStatus;
	}
	
	public boolean isAdmin() {
		return adminStatus;
	}
	public void setAdminStatus(boolean adminStatus) {
		this.adminStatus = adminStatus;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
