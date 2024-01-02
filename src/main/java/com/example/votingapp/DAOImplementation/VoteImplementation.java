package com.example.votingapp.DAOImplementation;

import com.example.votingapp.model.Vote;
import com.example.votingapp.DAO.VoteDAO;
import com.example.votingapp.db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VoteImplementation implements VoteDAO {

    @Override
    public void addVote(Vote vote) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "INSERT INTO Vote (user_id, poll_id, option_id) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, vote.getUserId());
                preparedStatement.setInt(2, vote.getPollId());
                preparedStatement.setInt(3, vote.getOptionId());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Creating vote failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }

    @Override
    public Vote getVoteById(int id) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM Vote WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractVoteFromResultSet(resultSet);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return null;
    }

    @Override
    public List<Vote> getAllVotesByUserId(int userId) {
        List<Vote> votes = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM Vote WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Vote vote = extractVoteFromResultSet(resultSet);
                        votes.add(vote);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return votes;
    }

    @Override
    public void updateVote(Vote vote) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "UPDATE Vote SET user_id = ?, poll_id = ?, option_id = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, vote.getUserId());
                preparedStatement.setInt(2, vote.getPollId());
                preparedStatement.setInt(3, vote.getOptionId());
                preparedStatement.setInt(4, vote.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Updating vote failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }

    @Override
    public void deleteVote(int id) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "DELETE FROM Vote WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Deleting vote failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }

    @Override
    public List<Vote> getAllVotesForPoll(int pollId) {
        List<Vote> votes = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM Vote WHERE poll_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, pollId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Vote vote = extractVoteFromResultSet(resultSet);
                        votes.add(vote);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return votes;
    }

    @Override
    public List<Vote> getAllVotesForOption(int optionId) {
        List<Vote> votes = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM Vote WHERE option_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, optionId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Vote vote = extractVoteFromResultSet(resultSet);
                        votes.add(vote);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return votes;
    }

    private Vote extractVoteFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int userId = resultSet.getInt("user_id");
        int pollId = resultSet.getInt("poll_id");
        int optionId = resultSet.getInt("option_id");

        Vote vote = new Vote(userId, pollId, optionId);
        vote.setId(id);
        return vote;
    }
}
