package com.example.votingapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // JDBC URL for MySQL
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/votingapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = ""; // No password

    public static Connection getConnection() {
        Connection connection = null;

        try {
            // Registering the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establishing the connection
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            if (connection != null) {
                System.out.println("Connected to the database!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database!");
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.out.println("Error closing connection!");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Connection connection = getConnection();

        // Use the connection for database operations

        closeConnection(connection);
    }
}

