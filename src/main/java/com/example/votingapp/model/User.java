package com.example.votingapp.model;

public class User {
    private int id;
    private String fullName;
    private String username;
    private String email;
    private String password;

    public User(String fullName, String username, String email, String password){
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    // Validator
    public boolean isValidUser() {
        return fullName != null && !fullName.isEmpty() &&
                username != null && !username.isEmpty() &&
                email != null && !email.isEmpty() &&
                password != null && !password.isEmpty();
    }

    public static User createUser(String fullName, String userName, String email, String password) {
        User user = new User(fullName, userName, email, password);
        return user;
    }
}
