package src.mchat;

import src.Entity.*;
import src.RegiForm.gui.*;

public class MChatContext {
	private User user;
	private ChatRoom group;

	private MChatStateEnum theState;
	private MChatMainCardLayout theFrame;

	public MChatContext() {
		theState = MChatStateEnum.Login;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setFrame(MChatMainCardLayout frame) {
		theFrame = frame;
	}

	public void request(AbsMChatPanel source, MChatStateEnum targetEnum) {
		switch (targetEnum) {
			case ViewProfile:
				theFrame.showProfilePanel();
				break;

			case ListChats:
				theFrame.showChatListPanel();
				break;

			case Login:
				// Handle Login case
				theFrame.showLoginPanel();
				break;

			case EditProfile:
				// Handle EditProfile case
				theFrame.showEditProfilePanel();
				break;

			case CHAT:
				// Handle inChat case
				theFrame.showMChatPanel();
				break;

			case Register:
				// Handle Register case
				theFrame.showRegistrationPanel();
				break;
		}
	}

	public void setState(MChatStateEnum state) {
		theState = state;
	}
}
