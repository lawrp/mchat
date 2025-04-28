package src.mchat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import src.Entity.*;

public class ProfilePanel extends AbsMChatPanel {
	private MChatContext context;
	private JLabel userPhoto;
	private JLabel userName;
	private JLabel userId;
	private JLabel displayName;
	private User user; // testbed.getUser();

	/* private */ boolean ownProfile = true; // show Edit button when true

	public ProfilePanel(MChatContext context) {
		this.context = context;
		user = context.getUser(); // testbed.getUser();
		init();
	}

	private void init() {
		this.setLayout(null);

		ImageIcon imageIcon = new ImageIcon("image/DummyProfileImageBig.jpg");
		userPhoto = new JLabel(imageIcon);

		// userName = new JLabel(testbed.getUser().getUserName());
		userName = new JLabel(user.getUsername());
		userId = new JLabel("" + user.getUserId());
		displayName = new JLabel(user.getDisplayName());
		userPhoto.setBounds(60, 60, 200, 200);
		userName.setBounds(60, 300, 200, 25);
		userId.setBounds(60, 350, 200, 25);
		displayName.setBounds(60, 400, 200, 25);
		this.add(userPhoto);
		this.add(userName);
		this.add(userId);
		this.add(displayName);

		JButton jbtnEdit = new JButton("Edit");
		jbtnEdit.setBounds(120, 450, 100, 25);
		if (ownProfile)
			this.add(jbtnEdit);
	}
}