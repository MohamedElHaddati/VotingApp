package com.example.votingapp.model;

import java.time.LocalDateTime;

public class Poll {
    private int id;
    private int userId;
    private String title;
    private String description;
    private LocalDateTime date;
    private LocalDateTime endDate;
    private int visibility;
    private String privateCode;

    // Constructors

    public Poll(int userId, String title, String description, LocalDateTime date, LocalDateTime endDate, int visibility, String privateCode) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.endDate = endDate;
        this.visibility = visibility;
        this.privateCode = privateCode;
    }


    // Getters and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public String getPrivateCode() {
        return privateCode;
    }

    public void setPrivateCode(String privateCode) {
        this.privateCode = privateCode;
    }

    // Example method: Create a new poll
    public static Poll createPoll(int userId, String title, String description,
                                  LocalDateTime date, LocalDateTime endDate,
                                  int visibility, String privateCode) {
        Poll poll = new Poll(userId, title, description, date, endDate, visibility, privateCode);
        return poll;
    }

    // Other methods: Update, delete, additional business logic
}
