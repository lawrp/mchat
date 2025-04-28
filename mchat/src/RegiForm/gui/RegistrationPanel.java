package src.RegiForm.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import src.RegiForm.forgui.RegistrationChecker;
import src.dao.mChatDBA;
import src.Entity.User;
import src.mchat.AbsMChatPanel;
import src.mchat.MChatContext;
import src.mchat.MChatStateEnum;

public class RegistrationPanel extends AbsMChatPanel {
	private RegistrationChecker regChecker = new RegistrationChecker();
	private mChatDBA dba;
	private String userId;
	private MChatContext context;

	private JPasswordField jpf;
	private boolean showPassword = false;
	private JTextField jtfUserName;
	private JLabel jlblMessage;
	private Image bkgrd;

	public RegistrationPanel(MChatContext context) {
		this.context = context;
		bkgrd = new ImageIcon(getClass().getResource("/image/wallpaper4.gif")).getImage();
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

		JLabel jlblUserName = new JLabel("User Name:");
		jlblUserName.setBounds(30, 30, 100, 25);
		jtfUserName = new JTextField(50);
		jtfUserName.setBounds(50, 70, 150, 25);
		this.add(jlblUserName);
		this.add(jtfUserName);

		JLabel jlblPassword = new JLabel("Password:");
		jlblPassword.setBounds(30, 120, 120, 25);
		jpf = new JPasswordField(25);
		jpf.setEchoChar(showPassword ? (char) 0 : '*');
		jpf.setBounds(50, 160, 120, 25);
		this.add(jlblPassword);
		this.add(jpf);

		JCheckBox jtbtn = new JCheckBox();
		jtbtn.setBounds(180, 160, 30, 30);
		this.add(jtbtn);
		jtbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showPassword = jtbtn.isSelected();
				jpf.setEchoChar(showPassword ? (char) 0 : '*');
			}
		});

		jlblMessage = new JLabel("");
		jlblMessage.setBounds(30, 200, 300, 25);
		this.add(jlblMessage);

		JButton jbtnCheck = new JButton("Check");
		jbtnCheck.setBounds(80, 250, 110, 25);
		jbtnCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				checkPassword();
			}
		});
		this.add(jbtnCheck);

		JButton jbtnRegister = new JButton("Register");
		jbtnRegister.setBounds(80, 300, 110, 25);
		jbtnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				registerUser();
			}
		});
		this.add(jbtnRegister);
	}

	private void checkPassword() {
		String pwd = String.valueOf(jpf.getPassword());
		regChecker.validatePassword(pwd);
		if (regChecker.allPassed()) {
			jlblMessage.setText("Passed!");
		} else {
			jlblMessage.setText(regChecker.getViolations().get(0));
			regChecker.reset();
		}
	}

	private void registerUser() {
		String pwd = String.valueOf(jpf.getPassword());
		regChecker.validatePassword(pwd);

		if (regChecker.allPassed()) {
			User user = new User();
			user.setId(userId);
			user.setUsername(jtfUserName.getText());
			user.setPassword(pwd);
			if (dba != null) {
				dba.updateUser(user);
				jlblMessage.setText("Registered!");

				context.request(RegistrationPanel.this, MChatStateEnum.Login);

			} else {
				jlblMessage.setText("DB Error");
			}
		} else {
			jlblMessage.setText(regChecker.getViolations().get(0));
			regChecker.reset();
		}
	}

	public void setDba(mChatDBA dba) {
		this.dba = dba;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
