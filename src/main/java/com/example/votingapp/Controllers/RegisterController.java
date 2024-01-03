package com.example.votingapp.Controllers;

import com.example.votingapp.DAOImplementation.UserImplementation;
import com.example.votingapp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;



public class RegisterController {

    @FXML
    private TextField email;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private TextField fullname;

    @FXML
    private Button registerButton;

    @FXML
    private Button gotoLogin;

    @FXML
    private Label errorLabel;

    private UserImplementation userDAO;

    private String successMessage;

    // Method to set the success message
    public void setSuccessMessage(String message) {
        this.successMessage = message;
    }

    public RegisterController() {
        userDAO = new UserImplementation(); // Initialize in the constructor
    }


    private Stage primaryStage; // Inject your primary stage

    public void setUserDAO(UserImplementation userDAO) {
        this.userDAO = userDAO;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    void handleRegister(ActionEvent event) {
        String userEmail = email.getText();
        String fullName = fullname.getText();
        String userUsername = username.getText();
        String userPassword = password.getText();

        // Validate fields before proceeding
        if (validateFields(userEmail, fullName, userUsername, userPassword)) {
            if (userDAO != null) {
                User newUser = new User(fullName, userUsername, userEmail, userPassword);

                try {
                    userDAO.addUser(newUser);
                    setSuccessMessage("Registration successful!");
                    showSuccessView();
                } catch (Exception e) {
                    e.printStackTrace();
                    errorLabel.setText("Error occurred during registration.");
                    errorLabel.setVisible(true);
                }
            } else {
                errorLabel.setText("UserDAO is not initialized.");
                errorLabel.setVisible(true);
            }
        }
    }

    private boolean validateFields(String email, String fullName, String username, String password) {
        // Validate if fields are not empty
        if (email.isEmpty() || fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields must be filled!");
            errorLabel.setVisible(true);
            return false;
        }

        // Validate if the email or username already exist
        if (userDAO.getUserByEmail(email) != null) {
            errorLabel.setText("Email already exists!");
            errorLabel.setVisible(true);
            return false;
        }

        if (userDAO.getUserByUsername(username) != null) {
            errorLabel.setText("Username already exists!");
            errorLabel.setVisible(true);
            return false;
        }

        // Other validations as needed...

        return true;
    }


    /*@FXML
    void gotoLogin(ActionEvent event) {
        loadLoginView();
    }*/

    private Stage stage;
    private Scene scene;
    @FXML
    private void loadLoginView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/votingapp/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void showSuccessView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/votingapp/login.fxml"));
        Parent root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setSuccessMessage(successMessage); // Pass the success message to the login controller

        Stage stage = (Stage) registerButton.getScene().getWindow(); // Adjust this line as per your setup
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
