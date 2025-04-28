package src.mchat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import src.mchat.util.ImageUtil;

public class MChatPanel extends AbsMChatPanel {
	private MChatContext context;
	
	private Image[] testImages = new Image[12];
	
	private int bottomY = 0;
	private int hIndex = 1;
	
	// for testing : toggle btw guest & owner
	private boolean isGuest = false;
	private Random iconChooser = new Random();
	private int gIndex = 3;

	public Image loginSelfie;
	
	public void loadTestImages() {
		Image sndImage;
		Image nintendoImg = new ImageIcon("image/nintendo-icons-2.png").getImage();
				
		for (int i=0; i<2; i++) {
			for (int j=0; j<6; j++) {
				sndImage = ImageUtil.clipFromImage(nintendoImg, 
						new Point(2 + j * 95, i * 190), 
						new Dimension(84, 84), 2);
				//JOptionPane.showConfirmDialog(null, "Looks right?", "Image Clip Test", 
				//		0, 0, new ImageIcon(sndImage));
				testImages[i*6 + j] = sndImage;
			}
		}
	}

	public MChatPanel(MChatContext context) {
		this.context = context;
		loadTestImages();
		
		init();
	}

	private void init() {
		JPanel chatPanel = new JPanel();
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.Y_AXIS));
		//p.setLayout(null);
		
		String text = "Radial proximal end anteroposterior length: L"
				+ "inear distance of most anterior limit to most posterior "
				+ "limit of radius head.";
		//for (int i=0; i<10; i++)
		chatPanel.add(prepareGuestBox(text));
		
		text = "Sounds good...";
		chatPanel.add(prepareOwnerBox(text));
		
		//for (int i=0; i<10; i++)
		text = "A Chn123 database was created on the AWS (SQL Server) "
				+ "instance by a student team in an IST 220 class, which "
				+ "was populated with data of lessons, characters, and "
				+ "character composition info. ";
		chatPanel.add(prepareGuestBox(text));
		
		// more stuff
		// replacing p with a scroll pane
		JScrollPane scrollP = new JScrollPane(chatPanel);
		Dimension chatDim = new Dimension(430, 580);
		//scrollP.setMaximumSize(chatDim);
		//scrollP.setMinimumSize(chatDim);
		scrollP.setPreferredSize(chatDim = new Dimension(430, 640));
		scrollP.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		this.add(scrollP);
		
		JPanel controlP = new JPanel();
		Dimension cntrDim = new Dimension(430, 70);
		controlP.setMaximumSize(cntrDim);
		controlP.setMinimumSize(cntrDim);
		controlP.setPreferredSize(cntrDim);
		controlP.setBorder(LineBorder.createGrayLineBorder());

		controlP.setLayout(null);
		
		// create toggle button for testing
		JButton jbtnToggle = new JButton("@");
		jbtnToggle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				isGuest = !isGuest;
				jbtnToggle.setText(isGuest?"*":"@");
			}
			
		});		
		jbtnToggle.setBounds(10, 10, 50, 50);
		controlP.add(jbtnToggle);
		
		// add msg box and send button
		JTextField msgField = new JTextField();
		msgField.setBounds(70, 10, 200, 50);
		controlP.add(msgField);
		
		// add send button
		JButton jbtnSend = new JButton("Send");
		jbtnSend.setBounds(280, 10, 80, 50);
		jbtnSend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = msgField.getText();
				if (isGuest)
					chatPanel.add(prepareGuestBox(text));
				else
					chatPanel.add(prepareOwnerBox(text));
				msgField.setText("");
           		
				if (bottomY > 580) {
					Point upperLeftCornerViewPort = new Point(0, bottomY - 580);
					scrollP.getViewport().setViewPosition(upperLeftCornerViewPort);
				}
				else {
					Point upperLeftCornerViewPort = new Point(0, 1);
					scrollP.getViewport().setViewPosition(upperLeftCornerViewPort);
				}
				scrollP.repaint();
				MChatPanel.this.repaint();
			}
		});
		controlP.add(jbtnSend);
		
		// add post photo button
		JButton jbtnPhoto = new JButton("...");
		jbtnPhoto.setBounds(370, 10, 50, 50);
		jbtnPhoto.addActionListener(new ActionListener() {

			JFileChooser jfc = new JFileChooser();
			@Override
			public void actionPerformed(ActionEvent e) {
				int userSelection = jfc.showOpenDialog(MChatPanel.this);
                    
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File file = jfc.getSelectedFile();
				    System.out.println("Selected file: " + file.getAbsolutePath());
					if (isGuest)
						chatPanel.add(prepareGuestPhoto(file));
					else
						chatPanel.add(prepareOwnerPhoto(file));
				}
               		
				if (bottomY > 580) {
					Point upperLeftCornerViewPort = new Point(0, bottomY - 580);
					scrollP.getViewport().setViewPosition(upperLeftCornerViewPort);
				}
				else {
					Point upperLeftCornerViewPort = new Point(0, 1);
					scrollP.getViewport().setViewPosition(upperLeftCornerViewPort);
				}
				scrollP.repaint();
				//System.out.println("repainting: " + scrollP);
				
				MChatPanel.this.repaint();
			}
		});
		controlP.add(jbtnPhoto);

		// add control panel to window
		this.add(controlP, BorderLayout.SOUTH);

	}

	private Component prepareOwnerBox(String text) {
		JPanel p = new JPanel();
		p.setLayout(null);
		
		JLabel sndIcon = new JLabel() {
			public void paintComponent(Graphics g) {
				g.drawImage(testImages[hIndex], 3, 3, null);
			}
		};
		sndIcon.setBounds(375, 10, 50, 50);
		sndIcon.setBorder(LineBorder.createGrayLineBorder());
		p.add(sndIcon);
		// add friend message in the middle
		JTextArea textField = new JTextArea();
		textField.setBackground(Color.GREEN);
		textField.setText(text);
		textField.setLineWrap(true);
		textField.setWrapStyleWord(true);
		// calculate text width in display
		//int textWidth = text.length() * 6;
				// cannot get graphics like this
				//p.getGraphics().getFontMetrics().
				//stringWidth(textField.getText());
		// assuming 5 pixels per char
		int length = text.length();
		int fieldWidth, fieldHeight = 30;
		if (length > 55) {
			fieldWidth = 275;
			fieldHeight += (length / 55) * 30;
			textField.setBounds(80, 10, fieldWidth, fieldHeight);
		}
		else {
			fieldWidth = length * 6;			
			textField.setBounds(80 + 275 - fieldWidth, 10, 
					fieldWidth, fieldHeight);
		}
		//textField.setBounds(80, 35, fieldWidth, fieldHeight);
		//textField.setBounds(80 + 275 - textWidth, 10, textWidth, 25);
		p.add(textField);
		//p.setBorder(LineBorder.createGrayLineBorder());
		int boxHeight = Math.max(10 + 50 + 10, textField.getHeight());
		p.setPreferredSize(new Dimension(100, boxHeight));
		p.setMaximumSize(new Dimension(900, boxHeight));
		bottomY += boxHeight;
	    System.out.println("Bottom Y: " + bottomY);
		return p;
	}

	private Component prepareGuestBox(String text) {
		JPanel p = new JPanel();
		p.setLayout(null);
		
		gIndex = iconChooser.nextInt(testImages.length);
		while (gIndex == hIndex)
			gIndex = iconChooser.nextInt(testImages.length);
		JLabel sndName = new JLabel("Guest-" + gIndex);
		sndName.setBounds(80, 10, 100, 20);
		sndName.setBorder(LineBorder.createGrayLineBorder());
		p.add(sndName);
		JLabel sndIcon = new JLabel("" + gIndex) {
			public void paintComponent(Graphics g) {
				int guest = Integer.parseInt(getText());
				g.drawImage(testImages[guest], 3, 3, null);
			}
		};
		sndIcon.setBounds(10, 10, 50, 50);
		sndIcon.setBorder(LineBorder.createGrayLineBorder());
		p.add(sndIcon);
		// add friend message in the middle
		Color guestColor = Color.WHITE;
		JTextArea textField = new JTextArea();
		textField.setBackground(guestColor);
		//String msg = msgField.getText();
		textField.setText(text);
		textField.setLineWrap(true);
		textField.setWrapStyleWord(true);
		// w=275 may hold up to 55 letters
		// h=85 is good for 3 lines
		//textField.setBounds(80, 35, 275, 90);
		int length = text.length();
		int fieldWidth, fieldHeight = 30;
		if (length > 55) {
			fieldWidth = 275;
			fieldHeight = calculateMessageBoxHeight(text);
			//fieldHeight += (length / 55) * 30;
		}
		else {
			fieldWidth = length * 6;			
		}
		textField.setBounds(80, 35, fieldWidth, fieldHeight);
					
		p.add(textField);
		int boxHeight = Math.max(10 + 50 + 10, textField.getHeight());
		p.setPreferredSize(new Dimension(100, boxHeight));
		p.setMaximumSize(new Dimension(900, boxHeight));
		
		bottomY += boxHeight;
	    System.out.println("Bottom Y: " + bottomY);
		
		return p;
	}

	public int calculateMessageBoxHeight(String message) {
		int baseHeight = 50;
		int lineHeight = 20;
		int lines = (int) Math.ceil(message.length() / 30.0); // 30 chars per line
		return baseHeight + (lines * lineHeight);
	}

	protected Component prepareGuestPhoto(File file) {
		JLabel thumbnailLabel = new JLabel();

		JPanel p = new JPanel();
		p.setLayout(null);
	    Image image;
		try {
			image = ImageIO.read(file);
            Image thumbnail = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            thumbnailLabel.setIcon(new ImageIcon(thumbnail));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		thumbnailLabel.setBounds(80, 40, 100, 100);
		p.add(thumbnailLabel);
		
		// add guest icon and name 
		gIndex = iconChooser.nextInt(testImages.length);
		while (gIndex == hIndex)
			gIndex = iconChooser.nextInt(testImages.length);
		JLabel sndName = new JLabel("Guest-" + gIndex);
		sndName.setBounds(80, 10, 100, 20);
		sndName.setBorder(LineBorder.createGrayLineBorder());
		p.add(sndName);
		JLabel sndIcon = new JLabel("" + gIndex) {
			public void paintComponent(Graphics g) {
				int guest = Integer.parseInt(getText());
				g.drawImage(testImages[guest], 3, 3, null);
			}
		};
		sndIcon.setBounds(10, 10, 50, 50);
		sndIcon.setBorder(LineBorder.createGrayLineBorder());
		p.add(sndIcon);
		
		int boxHeight = 10 + 20 + 10 + 100 + 10;
		p.setPreferredSize(new Dimension(100, boxHeight));
		p.setMaximumSize(new Dimension(900, boxHeight));
		bottomY += boxHeight;
	    System.out.println("Bottom Y: " + bottomY);
		return p;
	}

	protected Component prepareOwnerPhoto(File file) {
		JLabel thumbnailLabel = new JLabel();

		JPanel p = new JPanel();
		p.setLayout(null);
	    Image image;
		try {
			image = ImageIO.read(file);
            Image thumbnail = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            thumbnailLabel.setIcon(new ImageIcon(thumbnail));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		thumbnailLabel.setBounds(265, 0, 100, 100);
		p.add(thumbnailLabel);
		JLabel sndIcon = new JLabel() {
			public void paintComponent(Graphics g) {
				g.drawImage(testImages[hIndex], 3, 3, null);
			}
		};
		sndIcon.setBounds(375, 10, 50, 50);
		sndIcon.setBorder(LineBorder.createGrayLineBorder());
		p.add(sndIcon);
		int boxHeight = 10 + 100 + 10;
		p.setPreferredSize(new Dimension(100, boxHeight));
		p.setMaximumSize(new Dimension(900, boxHeight));
		
		bottomY += boxHeight;
	    System.out.println("Bottom Y: " + bottomY);
		return p;
	}


}
