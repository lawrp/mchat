package src.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import src.Entity.*;

public class mChatDBA extends SqlServerDbAccessor {

    // Constructor using default connection settings
    public mChatDBA() {
        super();
        setDbName("CSC312TeamProject");
    }

    // Constructor with custom connection settings
    public mChatDBA(String serverName, String user, String pwd, String dbName) {
        super(serverName, user, pwd, dbName);
    }

    public Date getJoinDateForChat(User user, ChatRoom chatRoom) {
        String userId = user.getUserId();
        int chatId = chatRoom.getChatId();
        Date timestamp = null;
        try {
            connectToDb();
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT Since FROM CSC312TeamProject.dbo.ChatMembership WHERE UserId = ? AND ChatId = ?");
            ps.setString(1, userId);
            ps.setInt(2, chatId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                timestamp = rs.getDate("Since");
                System.out.println("Join date for chat " + chatId + ": " + timestamp);
            }

            rs.close();
            ps.close();
            closeConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    // method to get all users from the database
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            connectToDb();
            ResultSet rs = getStmt().executeQuery(
                    "SELECT UserID, UserName, DisplayName, Password, Photo, DateModified FROM CSC312TeamProject.dbo.ChatUser");

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("UserID"));
                user.setUsername(rs.getString("UserName"));
                user.setDisplayName(rs.getString("DisplayName"));
                user.setPassword(rs.getString("Password"));
                user.setProfilePic(rs.getBytes("Photo"));
                user.setDateModified(rs.getDate("DateModified"));
                users.add(user);
                System.out.println("UserID: " + user.getUserId() + ", Username: " + user.getUsername() +
                        ", DisplayName: " + user.getDisplayName() + ", DateModified: " + user.getDateModified());
            }

            rs.close();
            closeConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserByName(String userName) {
        try {
            connectToDb();
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT UserID, UserName, DisplayName, Password, Photo, DateModified FROM CSC312TeamProject.dbo.ChatUser WHERE UserName = ?");
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getString("UserID"));
                user.setUsername(rs.getString("UserName"));
                user.setDisplayName(rs.getString("DisplayName"));
                user.setPassword(rs.getString("Password"));
                user.setProfilePic(rs.getBytes("Photo"));
                user.setDateModified(rs.getDate("DateModified"));
                return user;
            }

            rs.close();
            ps.close();
            closeConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Return null if user not found
    }

    public void sendMessage(User user, ChatRoom chatRoom, String message) {
        String userId = user.getUserId();
        int chatId = chatRoom.getChatId();
        try {
            connectToDb();
            PreparedStatement ps = getConnection().prepareStatement(
                    "INSERT INTO Message (UserId, ChatId, Text, Timestamp) VALUES (?, ?, ?, GETDATE())");
            ps.setString(1, userId);
            ps.setInt(2, chatId);
            ps.setString(3, message);

            int rowsAffected = ps.executeUpdate();
            ps.close();

            if (rowsAffected > 0) {
                System.out.println("Message sent successfully.");
            } else {
                System.out.println("Failed to send message.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnections();
    }

    void sendPicture(User user, ChatRoom chatRoom, byte[] picture) {
        String userId = user.getUserId();
        int chatId = chatRoom.getChatId();
        try {
            connectToDb();
            PreparedStatement ps = getConnection().prepareStatement(
                    "INSERT INTO Message (UserId, ChatId, Picture, Timestamp) VALUES (?, ?, ?, GETDATE())");
            ps.setString(1, userId);
            ps.setInt(2, chatId);
            ps.setBytes(3, picture);

            int rowsAffected = ps.executeUpdate();
            ps.close();

            if (rowsAffected > 0) {
                System.out.println("Picture sent successfully.");
            } else {
                System.out.println("Failed to send picture.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeConnections();
    }

    List<String> getMembersForChat(ChatRoom chatRoom) {
        List<String> members = new ArrayList<>();
        int chatId = chatRoom.getChatId();
        try {
            connectToDb();
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT UserId FROM CSC312TeamProject.dbo.ChatMembership WHERE ChatId = ?");
            ps.setInt(1, chatId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                members.add(rs.getString("UserId"));
            }

            rs.close();
            ps.close();
            closeConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public List<Message> getMessageHistory(User user, ChatRoom chatRoom) {
        List<Message> messages = new ArrayList<>();
        int chatId = chatRoom.getChatId();
        Date joinDate = getJoinDateForChat(user, chatRoom);

        if (joinDate == null) {
            System.out.println("User is not a member of the chat room.");
            return messages; // Return empty list
        }

        try {
            connectToDb();
            // Query to get all messages in the chat since the user joined
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT MessageID, UserId, ChatId, Text, Timestamp " +
                            "FROM CSC312TeamProject.dbo.Message " +
                            "WHERE ChatId = ? AND Timestamp > ? " +
                            "ORDER BY Timestamp ASC");

            ps.setInt(1, chatId);
            ps.setTimestamp(2, new java.sql.Timestamp(joinDate.getTime()));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Get the sender's user ID from the result set
                String senderId = rs.getString("UserId");

                User sender = getUserById(senderId);

                // Get message text and timestamp
                String text = rs.getString("Text");
                java.sql.Timestamp timestamp = rs.getTimestamp("Timestamp");

                // Create a new Message object and add it to the list
                Message message = new Message(sender, chatRoom, text, timestamp);
                messages.add(message);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }

        return messages;
    }

    public boolean isUserIdValid(String userId) {
        List<String> validUserIds = new ArrayList<>();
        try {
            connectToDb();
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT UserId FROM CSC312TeamProject.dbo.ValidUserIds WHERE UserId = ?");
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                validUserIds.add(rs.getString("UserId"));
            }

            rs.close();
            ps.close();
            closeConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (validUserIds.contains(userId)) {
            return true;
        } else {
            System.out.println("Invalid user ID: " + userId);
            return false;
        }
    }

    User getUserById(String userId) {
        User user = null;
        try {
            connectToDb();
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT UserID, UserName, DisplayName, Password, Photo, DateModified FROM CSC312TeamProject.dbo.ChatUser WHERE UserID = ?");
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("UserID"));
                user.setUsername(rs.getString("UserName"));
                user.setDisplayName(rs.getString("DisplayName"));
                user.setPassword(rs.getString("Password"));
                user.setProfilePic(rs.getBytes("Photo"));
                user.setDateModified(rs.getDate("DateModified"));
            }

            rs.close();
            ps.close();
            closeConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<ChatRoom> getChatRoomsByUserId(String userId) {
        List<ChatRoom> chatRooms = new ArrayList<>();
        try {
            connectToDb();
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT Chat.ChatId, Chat.ChatName, Chat.DisplayName, Chat.Photo, Chat.IndividualChat, Chat.DateModified "
                            +
                            "FROM CSC312TeamProject.dbo.ChatMembership " +
                            "JOIN CSC312TeamProject.dbo.Chat ON ChatMembership.ChatId = Chat.ChatId " +
                            "WHERE UserId = ?");
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChatRoom chatRoom = new ChatRoom();
                chatRoom.setChatId(rs.getInt("ChatId"));
                chatRoom.setChatName(rs.getString("ChatName"));
                chatRoom.setDisplayName(rs.getString("DisplayName"));
                chatRoom.setProfilePic(rs.getBytes("Photo"));
                chatRoom.setGroupChat(rs.getBoolean("IndividualChat"));
                chatRoom.setDateModified(rs.getDate("DateModified"));
                chatRoom.setMembers(getUsersByChatId(chatRoom.getChatId()));
                chatRooms.add(chatRoom);
            }

            rs.close();
            ps.close();
            closeConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatRooms;
    }

    List<User> getUsersByChatId(int chatId) {
        List<User> users = new ArrayList<>();
        try {
            connectToDb();

            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT UserId FROM CSC312TeamProject.dbo.ChatMembership WHERE ChatId = ?");
            ps.setInt(1, chatId);
            ResultSet rs = ps.executeQuery();

            List<String> userIds = new ArrayList<>();
            while (rs.next()) {
                userIds.add(rs.getString("UserId"));
            }
            rs.close();
            ps.close();

            // If we found users, query for their details
            if (!userIds.isEmpty()) {
                StringBuilder placeholders = new StringBuilder();
                for (int i = 0; i < userIds.size(); i++) {
                    if (i > 0)
                        placeholders.append(",");
                    placeholders.append("?");
                }

                PreparedStatement ps2 = getConnection().prepareStatement(
                        "SELECT UserID, UserName, DisplayName, Password, Photo, DateModified " +
                                "FROM CSC312TeamProject.dbo.ChatUser WHERE UserID IN (" + placeholders.toString()
                                + ")");

                // Set each user ID as a parameter in the prepared statement
                for (int i = 0; i < userIds.size(); i++) {
                    ps2.setString(i + 1, userIds.get(i));
                }

                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    User user = new User();
                    user.setId(rs2.getString("UserID"));
                    user.setUsername(rs2.getString("UserName"));
                    user.setDisplayName(rs2.getString("DisplayName"));
                    user.setPassword(rs2.getString("Password"));
                    user.setProfilePic(rs2.getBytes("Photo"));
                    user.setDateModified(rs2.getDate("DateModified"));
                    users.add(user);
                }
                rs2.close();
                ps2.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return users;
    }

    public ChatRoom getChatRoomById(int chatId) {
        ChatRoom chatRoom = null;
        try {
            connectToDb();
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT ChatID, ChatName, DisplayName, Photo, IndividualChat, DateModified FROM CSC312TeamProject.dbo.Chat WHERE ChatID = ?");
            ps.setInt(1, chatId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                chatRoom = new ChatRoom();
                chatRoom.setChatId(rs.getInt("ChatID"));
                chatRoom.setChatName(rs.getString("ChatName"));
                chatRoom.setDisplayName(rs.getString("DisplayName"));
                chatRoom.setProfilePic(rs.getBytes("Photo"));
                chatRoom.setGroupChat(rs.getBoolean("IndividualChat"));
                chatRoom.setDateModified(rs.getDate("DateModified"));
            }

            rs.close();
            ps.close();
            closeConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatRoom;
    }

    public boolean updateUser(User user) {
        try {
            connectToDb();
            PreparedStatement ps = getConnection().prepareStatement(
                    "UPDATE ChatUser SET UserName = ?, DisplayName = ?, Photo = ?, Password = ? WHERE UserId = ?");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getDisplayName());
            ps.setBytes(3, user.getProfilePic());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getUserId());

            int rowsAffected = ps.executeUpdate();
            ps.close();
            closeConnections();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(String userId) {
        try {
            connectToDb();
            PreparedStatement ps = getConnection().prepareStatement(
                    "DELETE FROM ChatUser WHERE UserID = ?");
            ps.setString(1, userId);

            int rowsAffected = ps.executeUpdate();
            ps.close();
            closeConnections();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void updateDisplyName(User user, String newDisplayName) {
        user.setDisplayName(newDisplayName);
        updateUser(user);
    }

    public void updatePhoto(User user, byte[] picture) {
        user.setProfilePic(picture);
        updateUser(user);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        updateUser(user);
    }

    public void updateUsername(User user, String newUsername) {
        user.setUsername(newUsername);
        updateUser(user);
    }

    // Helper method to close all connections
    public void closeConnections() {
        try {
            if (getStmt() != null)
                getStmt().close();
            if (getConnection() != null)
                getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}