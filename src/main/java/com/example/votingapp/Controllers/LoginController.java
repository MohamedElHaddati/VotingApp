package com.example.votingapp.Controllers;

import com.example.votingapp.DAOImplementation.UserImplementation;
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
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginButton;

    @FXML
    private Button gotoSignup;

    @FXML
    private Label errorLabel;

    private UserImplementation userDAO;

    public LoginController() {
        userDAO = new UserImplementation(); // Initialize in the constructor
    }

    public void setUserDAO(UserImplementation userDAO) {
        this.userDAO = userDAO;
    }



    // Method to set the success message
    public void setSuccessMessage(String message) {
        errorLabel.setTextFill(Color.GREEN);
        errorLabel.setText(message);
    }



    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String user = username.getText();
        String pass = password.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Please enter username and password.");
            errorLabel.setVisible(true);
            return;
        }

        if (userDAO != null && userDAO.verifyLogin(user, pass)) {
            // Successful login
            errorLabel.setVisible(false);
            showSuccessView(event);
        } else {
            // Invalid login
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Invalid username or password.");
            errorLabel.setVisible(true);
        }
    }
    @FXML
    private void gotoSignup(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/votingapp/sign-up.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void showSuccessView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/votingapp/success.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
