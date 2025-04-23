package mchat;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MChatContext {
	private MChatUser user;
	private MChatGroup group;
	
	private MChatStateEnum theState;
	private MChatMainCardLayout theFrame;
	
    public MChatContext() {
    	theState = MChatStateEnum.Initial;
    }

	public void setUser(MChatUser user) {
		this.user = user;
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
			
		case DoChat:
			ChatListPanel p = (ChatListPanel)source;
			int chatId = p.getSelectedChatId();
			theFrame.setTitle(theFrame.getTitle() + " - " + chatId);
			theFrame.showMChatPanel();
			break;
			
		}
	}

	public void setState(MChatStateEnum state) {
		theState = state;
	}
}
