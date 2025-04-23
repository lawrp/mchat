package mchat;

public enum MChatStateEnum {
	Initial, Login, ViewProfile, EditProfile, ListChats, DoChat, ZoomPhoto;
	
	public String toString() {
		return this.name();
	}
}
