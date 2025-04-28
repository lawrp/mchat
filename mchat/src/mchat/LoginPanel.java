package src.mchat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import src.Entity.*;
import src.dao.mChatDBA;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Graphics;

public class LoginPanel extends AbsMChatPanel {
	private MChatContext context;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JCheckBox seeProfile;
	private mChatDBA dba = new mChatDBA();
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JButton registerButton;
	private Image bkgrd;
	private JPanel whiteBoxPanel; // New panel for white box

	// seq number for creating test id
	public int nextId = 1;

	public LoginPanel(MChatContext context) {
		this.context = context;
		bkgrd = new ImageIcon(getClass().getResource("/image/wallpaper.gif")).getImage();
		init();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (bkgrd != null) {
			g.drawImage(bkgrd, 0, 0, getWidth(), getHeight(), this);
		}
	}

	private void init() {
		this.setLayout(null);

		// Create white box panel
		whiteBoxPanel = new JPanel();
		whiteBoxPanel.setLayout(null);
		whiteBoxPanel.setBackground(Color.WHITE);
		// Position will be set after calculating component bounds
		this.add(whiteBoxPanel);

		userNameLabel = new JLabel("User Name:");
		passwordLabel = new JLabel("Password:");

		userNameLabel.setBounds(120, 70, 100, 25);
		whiteBoxPanel.add(userNameLabel);
		passwordLabel.setBounds(120, 170, 100, 25);
		whiteBoxPanel.add(passwordLabel);

		userNameField = new JTextField(20);
		passwordField = new JPasswordField(20);

		userNameField.setBounds(120, 100, 200, 25);
		whiteBoxPanel.add(userNameField);
		passwordField.setBounds(120, 200, 200, 25);
		whiteBoxPanel.add(passwordField);

		JButton jbtnLogin = new JButton("Login");
		jbtnLogin.setBounds(100, 350, 100, 25);
		whiteBoxPanel.add(jbtnLogin);

		seeProfile = new JCheckBox();
		seeProfile.setBounds(80, 280, 25, 25);
		whiteBoxPanel.add(seeProfile);

		JLabel profileLabel = new JLabel("Show Profile");
		profileLabel.setBounds(110, 280, 100, 25);
		whiteBoxPanel.add(profileLabel);

		registerButton = new JButton("Register");
		registerButton.setBounds(240, 350, 100, 25);
		whiteBoxPanel.add(registerButton);

		// Calculate the bounds for white box panel
		// Find min and max x/y coordinates of all components
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = 0;
		int maxY = 0;

		for (java.awt.Component comp : whiteBoxPanel.getComponents()) {
			minX = Math.min(minX, comp.getX());
			minY = Math.min(minY, comp.getY());
			maxX = Math.max(maxX, comp.getX() + comp.getWidth());
			maxY = Math.max(maxY, comp.getY() + comp.getHeight());
		}

		// Add padding of 20 pixels
		int padding = 20;
		whiteBoxPanel.setBounds(minX - padding, minY - padding,
				maxX - minX + (padding * 2), maxY - minY + (padding * 2));

		// Adjust component positions relative to the panel
		for (java.awt.Component comp : whiteBoxPanel.getComponents()) {
			comp.setLocation(comp.getX() - (minX - padding), comp.getY() - (minY - padding));
		}

		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Going to Registration Page");
				context.request(LoginPanel.this, MChatStateEnum.Register);
			}
		});

		jbtnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				User user = dba.getUserByName(userNameField.getText());
				if (user == null) {
					System.out.println("User not found, creating new user");
					return;
				} else if (!user.getPassword().equals(new String(passwordField.getPassword()))) {
					System.out.println("Wrong password");
					return;
				}
				context.setUser(user);
				if (seeProfile.isSelected()) {
					System.out.println("Going to Profile Page");
					context.request(LoginPanel.this, MChatStateEnum.ViewProfile);
				} else {
					System.out.println("Going to Profile View Chats");
					context.request(LoginPanel.this, MChatStateEnum.ListChats);
				}
			}
		});
	}
}