package com.example.votingapp.Controllers;

import com.example.votingapp.model.User;

public class UserSession {
    private static UserSession instance;
    private User currentUser; // User object to store current user's information

    private UserSession() {
        // private constructor to prevent instantiation
    }

    public static UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}

