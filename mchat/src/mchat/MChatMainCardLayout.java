package mchat;

import java.awt.CardLayout;

import javax.swing.*;

public class MChatMainCardLayout extends JFrame {
    //JFrame frame;
    AbsMChatPanel currentPanel;
    JPanel cardPane;
    CardLayout card;

    MChatContext theContext;
    
    public MChatMainCardLayout() {
    	super("Let's Chat!");
    	theContext = new MChatContext();
    	theContext.setFrame(this);
    	
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("image/MChatBlue.png").getImage());
        //frame.setSize(500, 400);
        currentPanel = new LoginPanel(theContext);
        //pane2 = new JPanel();
        //pane3 = new JPanel();
        cardPane = new JPanel();
         
        card = new CardLayout();
        
        cardPane.setLayout(card);
        cardPane.add(currentPanel, "Login");    	
        this.add(cardPane);
        
        theContext.setState(MChatStateEnum.Login);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String args[]) {
    	MChatMainCardLayout test = new MChatMainCardLayout();
    }

	public void showProfilePanel() {
		theContext.setState(MChatStateEnum.ViewProfile);
		currentPanel = new ProfilePanel(theContext);
        cardPane.add(currentPanel, "ViewProfile");    	
        this.add(cardPane);
		card.show(cardPane, "ViewProfile");
        //card.next(cardPane);
		//this.repaint();
	}

	public void showChatListPanel() {
		theContext.setState(MChatStateEnum.ListChats);
		currentPanel = new ChatListPanel(theContext);
        cardPane.add(currentPanel, "ListChats");    	
        this.add(cardPane);
		card.show(cardPane, "ListChats");
        //card.next(cardPane);
		//this.repaint();
	}

	public void showMChatPanel() {
		theContext.setState(MChatStateEnum.DoChat);
		currentPanel = new MChatPanel(theContext);
        cardPane.add(currentPanel, "DoChat");    	
        this.add(cardPane);
		card.show(cardPane, "DoChat");
	}
}
