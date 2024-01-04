package com.example.votingapp.DAO;

import com.example.votingapp.model.Poll;
import java.util.List;

public interface PollDAO {
    // Create operation
    void addPoll(Poll poll);

    // Read operation
    Poll getPollById(int id);
    List<Poll> getAllPollsByUserId(int userId);

    // Update operation
    void updatePoll(Poll poll);

    public int addPollAndGetId(Poll poll);

    // Delete operation
    void deletePoll(int id);

    // Other methods based on application needs
    // Example: Get all public polls
    List<Poll> getAllPublicPolls();

    public String getUsernameForUserId(int userId);

    public int getUserIdFromCurrentPoll(Poll poll);
}
