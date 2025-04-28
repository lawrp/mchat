package src.Entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class User {
    private String UserId;
    private String UserName;
    private String DisplayName;
    private String Password;
    private byte[] ProfilePic;
    private Date DateModified;
    Map<Integer, Date> chatIds = new HashMap<>();

    public User() {
        // Default constructor
    }

    public Map<Integer, Date> getGroupChatIds() {
        return chatIds;
    }

    public String getUserId() {
        return UserId;
    }

    public String getUsername() {
        return UserName;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public String getPassword() {
        return Password;
    }

    public byte[] getProfilePic() {
        return ProfilePic;
    }

    public Date getDateModified() {
        return DateModified;
    }

    public void setId(String id) {
        this.UserId = id;
    }

    public void setUsername(String username) {
        this.UserName = username;
    }

    public void setDisplayName(String displayName) {
        this.DisplayName = displayName;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public void setProfilePic(byte[] profilePic) {
        this.ProfilePic = profilePic;
    }

    public void setDateModified(Date dateModified) {
        this.DateModified = dateModified;
    }
}