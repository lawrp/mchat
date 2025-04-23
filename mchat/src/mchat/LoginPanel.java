package mchat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends AbsMChatPanel {
	private MChatContext context;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JCheckBox seeProfile; 
	
	// seq number for creating test id
	public int nextId = 1;
	
	public LoginPanel(MChatContext context) {
		this.context = context;
		
		init();
	}

	private void init() {
		this.setLayout(null);
		
		userNameField = new JTextField(20);
		passwordField = new JPasswordField(20);
		
		userNameField.setBounds(60, 100, 200, 25);
		this.add(userNameField);
		passwordField.setBounds(60, 200, 200, 25);
		this.add(passwordField);
		
		JButton jbtnLogin = new JButton("Login");
		jbtnLogin.setBounds(120, 350, 100, 25);
		this.add(jbtnLogin);
		
		seeProfile = new JCheckBox(); 
		seeProfile.setBounds(80, 280, 25, 25);
		this.add(seeProfile);
		jbtnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MChatUser user = new MChatUser(nextId++ + "", userNameField.getText());
				System.out.println("User logged in: " + user);
				context.setUser(user);
				
				if (seeProfile.isSelected()) {
					System.out.println("Going to Profile Page");
					context.request(LoginPanel.this, MChatStateEnum.ViewProfile);
				} 
				else {
					System.out.println("Going to Profile View Chats");
					context.request(LoginPanel.this, MChatStateEnum.ListChats);
				}
			}
			
		});
	}
}