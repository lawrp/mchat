package src.mchat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import src.Entity.*;
import src.dao.mChatDBA;
import java.util.List;

public class MemberListPanel extends AbsMChatPanel {
	private MChatContext context;
	private mChatDBA dba = new mChatDBA();

	public MemberListPanel(MChatContext context) {
		this.context = context;
		init();
	}

	private void init() {
		this.setLayout(null);

		ImageIcon imageIcon = new ImageIcon("image/DummyProfileImage.jpg");
		JLabel userPhoto, displayName;

		int userCount = 8;
		int xOffset = 22, yOffset = 22;
		for (int i = 0; i < userCount; i++) {
			userPhoto = new JLabel(imageIcon);
			userPhoto.setBounds(xOffset + (i % 5) * 80, yOffset + i / 5 * 100, 60, 60);
			System.out.println("Photo-" + i + "(" + (xOffset + (i % 5) * 80) + "," +
					(yOffset + i / 5 * 100) + ")");
			this.add(userPhoto);
			displayName = new JLabel("User " + i);
			displayName.setBounds(xOffset + (i % 5) * 80,
					yOffset + i / 5 * 100 + 65, 60, 25);
			System.out.println("Name-" + i + "(" + (xOffset + (i % 5) * 80 + 65) + "," +
					(yOffset + i / 5 * 100) + ")");
			this.add(displayName);
		}
	}
}
