package com.example.votingapp.DAOImplementation;

import com.example.votingapp.model.Poll;
import com.example.votingapp.db.DatabaseConnection;
import com.example.votingapp.DAO.PollDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PollImplementation implements PollDAO {

    @Override
    public void addPoll(Poll poll) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "INSERT INTO Poll (user_id, title, description, date, endDate, visibility, privateCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, poll.getUserId());
                preparedStatement.setString(2, poll.getTitle());
                preparedStatement.setString(3, poll.getDescription());
                preparedStatement.setObject(4, poll.getDate());
                preparedStatement.setObject(5, poll.getEndDate());
                preparedStatement.setInt(6, poll.getVisibility());
                preparedStatement.setString(7, poll.getPrivateCode());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Creating poll failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }

    @Override
    public Poll getPollById(int id) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM Poll WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractPollFromResultSet(resultSet);
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
    public List<Poll> getAllPollsByUserId(int userId) {
        List<Poll> userPolls = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM Poll WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Poll poll = extractPollFromResultSet(resultSet);
                        userPolls.add(poll);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return userPolls;
    }

    @Override
    public void updatePoll(Poll poll) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "UPDATE Poll SET user_id = ?, title = ?, description = ?, date = ?, endDate = ?, visibility = ?, privateCode = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, poll.getUserId());
                preparedStatement.setString(2, poll.getTitle());
                preparedStatement.setString(3, poll.getDescription());
                preparedStatement.setObject(4, poll.getDate());
                preparedStatement.setObject(5, poll.getEndDate());
                preparedStatement.setInt(6, poll.getVisibility());
                preparedStatement.setString(7, poll.getPrivateCode());
                preparedStatement.setInt(8, poll.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Updating poll failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }

    @Override
    public void deletePoll(int id) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "DELETE FROM Poll WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Deleting poll failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }

    @Override
    public List<Poll> getAllPublicPolls() {
        List<Poll> publicPolls = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM Poll WHERE visibility = 1"; // Assuming visibility 1 represents public polls
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Poll poll = extractPollFromResultSet(resultSet);
                    publicPolls.add(poll);
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return publicPolls;
    }

    private Poll extractPollFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int userId = resultSet.getInt("user_id");
        String title = resultSet.getString("title");
        String description = resultSet.getString("description");
        LocalDateTime date = resultSet.getObject("date", LocalDateTime.class);
        LocalDateTime endDate = resultSet.getObject("endDate", LocalDateTime.class);
        int visibility = resultSet.getInt("visibility");
        String privateCode = resultSet.getString("privateCode");

        Poll poll = new Poll(userId, title, description, date, endDate, visibility, privateCode);
        poll.setId(id);
        return poll;
    }
}
