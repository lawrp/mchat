package workshop10.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import mchat.dao.SqlServerDbAccessor;
import workshop10.forgui.RegistrationChecker;

public class RegistrationPanel extends JPanel {
	private RegistrationChecker regChecker = new RegistrationChecker();
	
	private SqlServerDbAccessor dao;
	private String userId;

	private JPasswordField jpf;
	private boolean showPassword = false;
	
	public RegistrationPanel() {
		Dimension defaultDim = new Dimension(260, 600);
		this.setMaximumSize(defaultDim);
		this.setMinimumSize(defaultDim);
		this.setPreferredSize(defaultDim);
		init();
	}

	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		Image bkgrd = new ImageIcon("image/wallpaper4.gif").getImage();
		g.drawImage(bkgrd, 0, 0, null);
	}

	private void init() {
		this.setLayout(null);
		
		JLabel jlblUserName = new JLabel("User Name:");
		jlblUserName.setBounds(30, 30, 100, 25);
		JTextField jtfUserName = new JTextField(50);
		jtfUserName.setBounds(50, 70, 150, 25);
		this.add(jlblUserName);
		this.add(jtfUserName);
		
		JLabel jlblPassword = new JLabel("Password:");
		jlblPassword.setBounds(30, 120, 120, 25);
		jpf = new JPasswordField(25);
		jpf.setEchoChar(showPassword?(char)0:'*');
		jpf.setBounds(50, 160, 120, 25);
		this.add(jlblPassword);
		this.add(jpf);
		
		JCheckBox jtbtn = new JCheckBox();
		jtbtn.setBounds(180, 160, 30, 30);
		this.add(jtbtn);
		jtbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Show PWD?" + jtbtn.isSelected());
				//jtbtn.setSelected(!jtbtn.isSelected());
				//System.out.println("Show PWD?" + jtbtn.isSelected());
				showPassword = jtbtn.isSelected();
				System.out.println("Showing PWD==>" + showPassword);
				jpf.setEchoChar(showPassword?(char)0:'*');
			}
			
		});

		JLabel jlblMessage = new JLabel("test");
		jlblMessage.setBounds(30, 200, 200, 25);
		this.add(jlblMessage);
		
		JButton jbtnCheck = new JButton("Check");
		jbtnCheck.setBounds(80, 250, 110, 25);
		jbtnCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String pwd = String.valueOf(jpf.getPassword());
				regChecker.validatePassword(pwd);
				if (regChecker.allPassed())
					jlblMessage.setText("Passed!");
				else {
					jlblMessage.setText(regChecker.getViolations().get(0));
					regChecker.reset();
				}
				
				RegistrationPanel.this.repaint();
			}
			
		});
		this.add(jbtnCheck);
		
		JButton jbtnRegister = new JButton("Register");
		jbtnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String sql = "INSERT INTO ChatUser values ('"
						+ userId + "','" + jtfUserName.getText().trim() +
						"','" + jtfUserName.getText().trim() 
						+ "','" + jpf.getText().trim() + "', null, getDate())";
				System.out.println(sql);
				try {
					int rows = dao.getStmt().executeUpdate(sql);
					System.out.println(rows + " rows inserted.");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		jbtnRegister.setBounds(80, 300, 110, 25);
		this.add(jbtnRegister);
	}
	
	public static void main(String[] args) {
		SqlServerDbAccessor dao = new SqlServerDbAccessor();
		dao.setDbName("CSC312TeamProject");
		dao.connectToDb();
		
		JFrame f = new JFrame("Welcome");
        f.setIconImage(new ImageIcon("image/MChatBlue.png").getImage());
		final JTextField userIdField = new JTextField(30);
		CardLayout card = new CardLayout();
		
		JPanel cardPanel = new JPanel();
		cardPanel.setLayout(card);
		
		JPanel welcomePanel = new JPanel() {
			
			public void paintComponent(Graphics g) {
				this.setLayout(null);
				Image bkgrd = new ImageIcon("image/wallpaper.gif").getImage();
				g.drawImage(bkgrd, 0, 0, null);
				
				JLabel jlblPrompt = new JLabel("Please input your ID");
				jlblPrompt.setBounds(50, 50, 200, 25);
				this.add(jlblPrompt);
				userIdField.setBounds(50, 100, 150, 25);
				this.add(userIdField);
				
				JButton jbtnSubmit = new JButton("Submit");
				jbtnSubmit.setBounds(70, 200, 120, 30);
				this.add(jbtnSubmit);
				
				this.setPreferredSize(new Dimension(260, 450));
				
				jbtnSubmit.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String id = userIdField.getText();
						
						if(isValidId(id)) {
							f.setTitle("Registration");
							RegistrationPanel p = new RegistrationPanel();
							p.setDao(dao);
							p.setUserId(id);
							cardPanel.add(p, "Registration");
							card.show(cardPanel, "Registration");
							System.out.println(id);
						}
					}

					private boolean isValidId(String id) {
						boolean loggedIn = false;
						String sql = "SELECT * FROM ValidUserIds WHERE UserId = '"
								+ id + "'";
						int seqNo;
						try {
							ResultSet rset = dao.getStmt().executeQuery(sql);
							while (rset.next()) {
								seqNo = rset.getInt(1);
								System.out.println(seqNo);
								loggedIn = true;
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						return loggedIn;
					}
					
				});
			}
		};
		
		cardPanel.add(welcomePanel, "Welcome");
		
		f.add(cardPanel);
		/*
		f.add(welcomePanel, BorderLayout.PAGE_START);

		RegistrationPanel p = new RegistrationPanel();
		f.add(p);
		*/		
		//f.pack();
		f.setSize(260, 450);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

	protected void setDao(SqlServerDbAccessor dao) {
		this.dao = dao;
	}

	protected void setUserId(String userId) {
		this.userId = userId;
	}
}
