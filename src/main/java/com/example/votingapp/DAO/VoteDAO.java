package com.example.votingapp.DAO;

import com.example.votingapp.model.Vote;
import java.util.List;

public interface VoteDAO {
    // Create operation
    void addVote(Vote vote);

    // Read operation
    Vote getVoteById(int id);
    List<Vote> getAllVotesByUserId(int userId);

    // Update operation
    void updateVote(Vote vote);

    // Delete operation
    void deleteVote(int id);

    // Other methods based on application needs
    // Example: Get all votes for a specific poll
    List<Vote> getAllVotesForPoll(int pollId);
    List<Vote> getAllVotesForOption(int optionId);
}
