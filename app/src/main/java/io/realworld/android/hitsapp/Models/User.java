package io.realworld.android.hitsapp.Models;

public class User {
        private String profilePic;
        private String userName;
        private String email;
        private String password;
        private String userID;
        private String lastMessage;

    public User(String profilePic, String userName, String email, String password, String userID, String lastMessage) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.userID = userID;
        this.lastMessage = lastMessage;
    }
    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(){}

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
