package mchat;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ChatListPanel extends AbsMChatPanel {
	private MChatContext context;
	//MouseAdapter select;
	private int selectedChatId;
	
	public ChatListPanel(MChatContext context) {
		this.context = context;
		
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
		for (int i=1; i<9; i++) {
			if (i % 3 == 0) {
				userPhoto = new JLabel(userIcon);
				displayName = new JLabel("Group " + i + " Name");
			}
			else {
				userPhoto = new JLabel(userIcon);
				displayName = new JLabel("User " + i + " Display Name");
			}
			userSelectBox = new Rectangle(60, -50 + i * 70, 60, 60);
			userBoxes.add(userSelectBox);
			userPhoto.setBounds(userSelectBox);
			this.add(userPhoto);
			displayName.setBounds(160, -30 + i * 70, 150, 25);
			this.add(displayName);
		}
		
		// add mouse control to pick chat for display next on MChatPanel
		MouseAdapter select = new MouseAdapter() {
			//int boxId = 0;
			
			public void mouseClicked(MouseEvent e) {
				Point clicked = e.getPoint();
				int boxId = whichBox(clicked);
				System.out.println("Chat " + boxId + " selected");
				context.request(ChatListPanel.this, MChatStateEnum.DoChat);
			}

			private int whichBox(Point clicked) {
				for (int i=1; i<9; i++) {
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