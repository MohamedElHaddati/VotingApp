package com.example.votingapp.DAOImplementation;

import com.example.votingapp.model.Poll;
import com.example.votingapp.db.DatabaseConnection;
import com.example.votingapp.DAO.PollDAO;

import java.sql.*;
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
    public int addPollAndGetId(Poll poll) {
        Connection connection = DatabaseConnection.getConnection();
        int generatedId = -1; // Initializing with an invalid ID
        if (connection != null) {
            String query = "INSERT INTO Poll (user_id, title, description, date, endDate, visibility, privateCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
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

                // Retrieve the generated ID after insertion
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating poll failed, no ID obtained.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return generatedId;
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

    @Override
    public int getUserIdFromCurrentPoll(Poll poll) {
        Connection connection = DatabaseConnection.getConnection();
        int userId = -1;
        if (connection != null) {
            String query = "SELECT user_id FROM Poll WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, poll.getId());

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userId = resultSet.getInt("user_id");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return userId;
    }

    public int getUserIdFromPollId(int pollId) {
        Connection connection = DatabaseConnection.getConnection();
        int userId = -1;
        if (connection != null) {
            String query = "SELECT user_id FROM Poll WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, pollId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        userId = resultSet.getInt("user_id");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return userId;
    }

    @Override
    public Poll getPollByPrivateCode(String privateCode) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM Poll WHERE privateCode = ? AND visibility = 0"; // Assuming visibility 0 represents private polls
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, privateCode);

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


    public String getUsernameForUserId(int userId) {
        Connection connection = DatabaseConnection.getConnection();
        String username = "";
        if (connection != null) {
            String query = "SELECT user_name FROM User WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        username = resultSet.getString("user_name");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return username;
    }
}
