package mchat;

import java.awt.Image;

public class MChatUser implements MChatEntity {
	private String id;
	private String userName;
	private String password; // is this needed?
	private String displayName;
	private Image photo;

	public MChatUser(String id, String userName) {
		this.id = id;
		this.userName = userName;
		password = "1234";
		displayName = userName;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public String toString() {
		return this.userName + " (" + id + ")";
	}

	public String getUserName() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	public String getId() {
		return id;
	}

	@Override
	public Image getPhoto() {
		// TODO Auto-generated method stub
		return photo;
	}
}
