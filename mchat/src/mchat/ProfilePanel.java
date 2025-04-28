package src.mchat;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import src.dao.*;
import src.mchat.util.ImageUtil;
import src.Entity.*;
import java.awt.image.BufferedImage;

public class ProfilePanel extends AbsMChatPanel {
	private MChatContext context;
	private JLabel userPhoto;
	private JLabel userName;
	private JLabel userId;
	private JLabel displayName;
	private User user;
	private mChatDBA dba = new mChatDBA();

	boolean ownProfile = true;

	public ProfilePanel(MChatContext context) {
		this.context = context;
		user = context.getUser();
		init();
	}

	private void init() {
		this.setLayout(null);

		ImageIcon imageIcon = new ImageIcon("image/DummyProfileImageBig.jpg");

		if (user.getProfilePic() != null) {
			try {
				java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(user.getProfilePic());
				java.awt.image.BufferedImage bufferedImage = javax.imageio.ImageIO.read(bais);
				userPhoto = new JLabel(new ImageIcon(bufferedImage));
			} catch (java.io.IOException e) {
				e.printStackTrace();
				userPhoto = new JLabel(imageIcon); // Using default image if conversion fails
			}
		} else {
			userPhoto = new JLabel(imageIcon);
		}

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
		JButton backToLogiButton = new JButton("Back to Login");

		backToLogiButton.setBounds(60, 660, 200, 25);

		backToLogiButton.addActionListener(_ -> {
			context.request(this, MChatStateEnum.Login);
		});

		this.add(backToLogiButton);

		jbtnEdit.addActionListener(_ -> {
			createEditOptions();
		});

		if (ownProfile)
			this.add(jbtnEdit);
	}

	// Method to create edit options when Edit button is clicked
	private void createEditOptions() {
		removeExistingEditOptions();

		JButton editPictureBtn = new JButton("Change Profile Picture");
		JButton editNameBtn = new JButton("Change Display Name");
		JButton editPasswordBtn = new JButton("Change Password");
		JButton closeEdiButton = new JButton("Close Edit Options");

		editPictureBtn.setBounds(60, 500, 200, 25);
		editNameBtn.setBounds(60, 540, 200, 25);
		editPasswordBtn.setBounds(60, 580, 200, 25);
		closeEdiButton.setBounds(60, 620, 200, 25);

		this.add(editPictureBtn);
		this.add(editNameBtn);
		this.add(editPasswordBtn);
		this.add(closeEdiButton);

		this.revalidate();
		this.repaint();

		editPictureBtn.addActionListener(_ -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
					"Image files", "jpg", "jpeg", "png", "gif"));
			int returnValue = fileChooser.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				try {
					File selectedFile = fileChooser.getSelectedFile();
					BufferedImage resizedImage = ImageUtil.resizeImage(selectedFile, 200, 200);
					ImageIcon newImage = new ImageIcon(resizedImage);
					userPhoto.setIcon(newImage);
					java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
					ImageIO.write(resizedImage, "png", baos);
					byte[] imageBytes = baos.toByteArray();
					baos.close();
					user.setProfilePic(imageBytes);
					dba.updateUser(user);

				} catch (IOException ex) {
					ex.printStackTrace();
					javax.swing.JOptionPane.showMessageDialog(null,
							"Error processing image: " + ex.getMessage(),
							"Image Processing Error",
							javax.swing.JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		editNameBtn.addActionListener(_ -> {
			String newDisplayName = JOptionPane.showInputDialog("Enter new display name:");
			if (newDisplayName != null && !newDisplayName.trim().isEmpty()) {
				user.setDisplayName(newDisplayName);
				displayName.setText(newDisplayName);
				dba.updateUser(user);
			} else {
				JOptionPane.showMessageDialog(null, "Display name cannot be empty.",
						"Invalid Input", JOptionPane.ERROR_MESSAGE);
			}
		});

		editPasswordBtn.addActionListener(_ -> {
			String newPassword = JOptionPane.showInputDialog("Enter new Password:");
			if (newPassword != null && !newPassword.trim().isEmpty()) {
				user.setPassword(newPassword);
				dba.updateUser(user);
			} else {
				JOptionPane.showMessageDialog(null, "Password cannot be empty.",
						"Invalid Input", JOptionPane.ERROR_MESSAGE);
			}
		});

		closeEdiButton.addActionListener(_ -> {
			removeExistingEditOptions();
			this.revalidate();
			this.repaint();
		});
	}

	// Helper method to remove any existing edit option buttons
	private void removeExistingEditOptions() {
		for (java.awt.Component comp : this.getComponents()) {
			if (comp instanceof JButton) {
				JButton btn = (JButton) comp;
				if (btn.getText().equals("Change Profile Picture") ||
						btn.getText().equals("Change Display Name") || btn.getText().equals("Change Password")
						|| btn.getText().equals("Close Edit Options")) {
					this.remove(btn);
				}
			}
		}
	}
}