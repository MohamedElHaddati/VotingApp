package com.example.votingapp;

import com.example.votingapp.Controllers.LoginController;
import com.example.votingapp.Controllers.RegisterController;
import com.example.votingapp.DAOImplementation.UserImplementation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load your FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Get the controller instance
        LoginController loginController = loader.getController();

        // Create and set UserImplementation instance
        UserImplementation userImplementation = new UserImplementation();
        loginController.setUserDAO(userImplementation);

        // Show the scene
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


/*public class Main extends Application {

    private Stage primaryStage;
    private Scene loginScene;
    private Scene successScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        // Load login scene
        Parent loginRoot = FXMLLoader.load(getClass().getResource("sign-up.fxml"));
        loginScene = new Scene(loginRoot, 600, 400);

        // Load success scene
        Parent successRoot = FXMLLoader.load(getClass().getResource("success.fxml"));
        successScene = new Scene(successRoot, 600, 400);

        // Set the initial scene
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    // Method to switch to success scene
    public void switchToSuccessScene() {
        primaryStage.setScene(successScene);
        primaryStage.setTitle("Success");
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/


/*public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create an instance of UserImplementation (or any UserDAO implementation)
        UserImplementation userDAO = new UserImplementation();

        // Load sign-up.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sign-up.fxml"));
        StackPane registerLayout = loader.load();

        // Get the controller and set the userDAO
        RegisterController registerController = loader.getController();
        registerController.setUserDAO(userDAO);
        registerController.setPrimaryStage(primaryStage);

        // Set the scene using the layout from sign-up.fxml
        Scene scene = new Scene(registerLayout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Your Application Title");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}*/
