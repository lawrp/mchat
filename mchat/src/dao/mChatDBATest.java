package src.dao;

import java.util.List;
import src.Entity.*;

public class mChatDBATest {
    public static void main(String[] args) {
        // Create an instance of mChatDBA
        mChatDBA dba = new mChatDBA();

        // Test getAllUsers
        System.out.println("\n===== Testing getAllUsers =====");
        List<User> allUsers = dba.getAllUsers();
        System.out.println("Found " + allUsers.size() + " users");

        if (!allUsers.isEmpty()) {
            // Get first user to use for further tests
            User testUser = allUsers.get(0);
            String testUserId = testUser.getUserId();

            // Test getUserById
            System.out.println("\n===== Testing getUserById =====");
            User user = dba.getUserById(testUserId);
            if (user != null) {
                System.out.println("Found user: " + user.getUsername());

                String DrZhaoId = "MZ-734";
                User DrZhao = dba.getUserById(DrZhaoId);
                System.out.println("Found user: " + DrZhao.getUsername());
                ChatRoom classroom = dba.getChatRoomById(1);
                System.out.println("Found chat room: " + classroom.getChatName());
                List<User> members = dba.getUsersByChatId(1);
                System.out.println("Chat room " + classroom.getChatName() + " has " + members.size() + " members");
                for (User member : members) {
                    System.out.println("Member: " + member.getUsername());
                }
                System.out.println("-------------------TESTING getMessageHistory-------------------");
                List<Message> chatHistory = dba.getMessageHistory(DrZhao, classroom);
                for (Message message : chatHistory) {
                    System.out.println("Time: " + message.getTimestamp() + " Sender: "
                            + message.getSender().getUsername() + " Message: " + message.getText());
                }

                System.out.println("-------------------TESTING isValidUserId-------------------");
                if (dba.isUserIdValid(DrZhaoId)) {
                    System.out.println("User ID " + DrZhaoId + " is valid.");
                } else {
                    System.out.println("User ID " + DrZhaoId + " is not valid.");
                }
            } else {
                System.out.println("Failed to find user with ID: " + testUserId);
            }
        } else {
            System.out.println("No users found in database");
        }

        System.out.println("\nAll tests completed.");
    }
}