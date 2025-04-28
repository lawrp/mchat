package src.Entity;

import java.util.*;

public class Message {
    private User sender;
    private ChatRoom chatRoom;
    private String text;
    private Date timestamp;

    public Message(User sender, ChatRoom chatRoom, String text, Date timestamp) {
        this.sender = sender;
        this.chatRoom = chatRoom;
        this.text = text;
        this.timestamp = timestamp;
    }

    public User getSender() {
        return sender;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public String getText() {
        return text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}