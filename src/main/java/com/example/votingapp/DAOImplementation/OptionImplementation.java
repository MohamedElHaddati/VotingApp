package com.example.votingapp.DAOImplementation;

import com.example.votingapp.model.Option;
import com.example.votingapp.db.DatabaseConnection;
import com.example.votingapp.DAO.OptionDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OptionImplementation implements OptionDAO {

    @Override
    public void addOption(Option option) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "INSERT INTO Option (Poll_id, content) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, option.getPollId());
                preparedStatement.setString(2, option.getContent());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Creating option failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }

    @Override
    public Option getOptionById(int id) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM Option WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return extractOptionFromResultSet(resultSet);
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
    public List<Option> getAllOptionsByPollId(int pollId) {
        List<Option> options = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "SELECT * FROM Option WHERE Poll_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, pollId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Option option = extractOptionFromResultSet(resultSet);
                        options.add(option);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
        return options;
    }

    @Override
    public void updateOption(Option option) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "UPDATE Option SET content = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, option.getContent());
                preparedStatement.setInt(2, option.getId());

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Updating option failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }

    @Override
    public void deleteOption(int id) {
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            String query = "DELETE FROM Option WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, id);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected == 0) {
                    throw new SQLException("Deleting option failed, no rows affected.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exceptions appropriately
            } finally {
                DatabaseConnection.closeConnection(connection);
            }
        }
    }

    @Override
    public List<Option> getAllOptionsForPoll(int pollId) {
        return getAllOptionsByPollId(pollId); // Reusing the same logic as getAllOptionsByPollId for simplicity
    }

    private Option extractOptionFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int pollId = resultSet.getInt("Poll_id");
        String content = resultSet.getString("content");

        Option option = new Option(pollId, content);
        option.setId(id);
        return option;
    }
}
