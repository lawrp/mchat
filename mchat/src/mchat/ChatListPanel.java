package src.mchat;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import src.Entity.*;
import src.dao.mChatDBA;

public class ChatListPanel extends AbsMChatPanel {
	private MChatContext context;
	// MouseAdapter select;
	private int selectedChatId;
	private List<ChatRoom> chatRooms = new ArrayList<>();
	private mChatDBA dba = new mChatDBA();

	public ChatListPanel(MChatContext context) {
		this.context = context;
		chatRooms = dba.getChatRoomsByUserId(context.getUser().getUserId());

		for (int i = 0; i < chatRooms.size(); i++) {
			System.out.println("Chat " + i + ": " + chatRooms.get(i).getChatName());
		}

		System.out.println("ChatListPanel: " + chatRooms.size() + " chats found.");
		init();
	}

	private void init() {
		this.setLayout(null);

		ImageIcon userIcon = new ImageIcon("image/DummyProfileImage.jpg");
		ImageIcon groupIcon = new ImageIcon("image/DummyGroupImage.jpg");
		JLabel userPhoto, displayName;

		Rectangle userSelectBox;
		java.util.List<Rectangle> userBoxes = new ArrayList<>();
		userBoxes.add(null);
		for (int i = 0; i < chatRooms.size(); i++) {
			ChatRoom chatRoom = chatRooms.get(i);
			userPhoto = new JLabel(userIcon);
			userSelectBox = new Rectangle(20, 20 + i * 100, 60, 60);
			userPhoto.setBounds(userSelectBox);
			this.add(userPhoto);

			displayName = new JLabel(chatRoom.getChatName());
			displayName.setBounds(90, 20 + i * 100, 200, 25);
			this.add(displayName);

			userBoxes.add(userSelectBox);
		}

		// add mouse control to pick chat for display next on MChatPanel
		MouseAdapter select = new MouseAdapter() {
			// int boxId = 0;

			public void mouseClicked(MouseEvent e) {
				Point clicked = e.getPoint();
				int boxId = whichBox(clicked);
				System.out.println("Chat " + boxId + " selected");
				context.request(ChatListPanel.this, MChatStateEnum.CHAT);
			}

			private int whichBox(Point clicked) {
				for (int i = 1; i < 9; i++) {
					if (userBoxes.get(i).contains(clicked)) {
						selectedChatId = i;
						break;
					}
				}
				return selectedChatId;
			}
		};
		this.addMouseListener(select);
	}

	public int getSelectedChatId() {
		return selectedChatId;
	}
}