package mchat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class ProfilePanel extends AbsMChatPanel {
	private MChatContext context;
	private JLabel userPhoto;
	private JLabel userName;
	private JLabel userId;
	private JLabel displayName;
	
	/*private*/ boolean ownProfile = true;	// show Edit button when true
	
	public ProfilePanel(MChatContext context) {
		this.context = context;
		
		init();
	}

	private void init() {
		this.setLayout(null);
		
		ImageIcon imageIcon = new ImageIcon("image/DummyProfileImageBig.jpg");
		userPhoto = new JLabel(imageIcon);
		
		MChatUser testUser = new MChatUser(12 + "", "Test");
		//userName = new JLabel(testbed.getUser().getUserName());
		userName = new JLabel(testUser.getUserName());
		userId = new JLabel("" + testUser.getId());
		displayName = new JLabel(testUser.getDisplayName());
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