package src.mchat;

import java.awt.Dimension;

import javax.swing.JPanel;

public class AbsMChatPanel extends JPanel {
	// public static final Dimension PANEL_DIM = new Dimension(360, 600);
	public static final Dimension PANEL_DIM = new Dimension(440, 720);

	public AbsMChatPanel() {
		this.setMinimumSize(PANEL_DIM);
		this.setMaximumSize(PANEL_DIM);
		this.setPreferredSize(PANEL_DIM);
		// this.setBorder(new LineBorder(Color.BLACK, 2));
	}
}
