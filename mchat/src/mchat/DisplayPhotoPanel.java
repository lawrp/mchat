package mchat;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DisplayPhotoPanel extends AbsMChatPanel {
	private MChatContext context;
	
	public DisplayPhotoPanel(MChatContext context) {
		this.context = context;
		
		init();
	}

	private void init() {
		this.setLayout(null);
		
		ImageIcon imageIcon = new ImageIcon("image/MChatBlue.png");
		JLabel userPhoto = new JLabel(imageIcon);
			
		userPhoto.setBounds(0, 0, PANEL_DIM.width, PANEL_DIM.height);
		this.add(userPhoto);
	}
}
