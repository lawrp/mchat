package src.mchat;

public enum MChatStateEnum {
	Login, ViewProfile, EditProfile, ListChats, CHAT, Register;

	public String toString() {
		return this.name();
	}
}
