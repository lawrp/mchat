package mchat;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class AbsMChatPanel  extends JPanel {
	//public static final Dimension PANEL_DIM = new Dimension(360, 600);
	public static final Dimension PANEL_DIM = new Dimension(430, 720);
	
	public AbsMChatPanel() {
		this.setMinimumSize(PANEL_DIM);
		this.setMaximumSize(PANEL_DIM);
		this.setPreferredSize(PANEL_DIM);
		//this.setBorder(new LineBorder(Color.BLACK, 2));
	}
}
