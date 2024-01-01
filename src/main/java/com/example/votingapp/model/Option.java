package com.example.votingapp.model;

public class Option {
    private int id;
    private int pollId;
    private String content;

    // Constructors

    public Option(int pollId, String content) {
        this.pollId = pollId;
        this.content = content;
    }
    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    // Example method: Create a new option for a poll
    public static Option createOption(int pollId, String content) {
        Option option = new Option(pollId, content);
        return option;
    }

    // Other methods: Update, delete, additional business logic
}
