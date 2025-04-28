package src.Entity;

import java.util.*;

public class ChatRoom {
    private int chatId;
    private String chatName;
    private String displayName;
    private byte[] profilePic;
    private boolean isGroupChat;
    private Date dateModified;
    private List<User> members = new ArrayList<>();

    public ChatRoom() {
        // Default constructor
    }

    public int getChatId() {
        return chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public byte[] getProfilePic() {
        return profilePic;
    }

    public boolean isGroupChat() {
        return isGroupChat;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }

    public void setGroupChat(boolean isGroupChat) {
        this.isGroupChat = isGroupChat;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

}