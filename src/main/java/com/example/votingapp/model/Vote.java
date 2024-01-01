package com.example.votingapp.model;

public class Vote {
    private int id;
    private int userId;
    private int pollId;
    private int optionId;

    // Constructors

    public Vote(int userId, int pollId, int optionId) {
        this.userId = userId;
        this.pollId = pollId;
        this.optionId = optionId;
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

    public int getPollId() {
        return pollId;
    }

    public void setPollId(int pollId) {
        this.pollId = pollId;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    // Example method: Create a new vote
    public static Vote createVote(int userId, int pollId, int optionId) {
        Vote vote = new Vote(userId, pollId, optionId);
        return vote;
    }

    // Other methods: Update, delete, additional business logic
}
