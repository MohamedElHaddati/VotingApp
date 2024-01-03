package com.example.votingapp.DAO;

import com.example.votingapp.model.Option;
import java.util.List;

public interface OptionDAO {
    // Create operation
    void addOption(Option option);

    public int addOptionAndGetId(Option option);

    // Read operation
    Option getOptionById(int id);
    List<Option> getAllOptionsByPollId(int pollId);

    // Update operation
    void updateOption(Option option);

    // Delete operation
    void deleteOption(int id);

    // Other methods based on application needs
    // Example: Get all options for a specific poll
    List<Option> getAllOptionsForPoll(int pollId);
}
