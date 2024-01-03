package com.example.votingapp.Controllers;

import com.example.votingapp.DAOImplementation.PollImplementation;
import com.example.votingapp.model.Poll;
import com.example.votingapp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML
    private Label fullname;

    @FXML
    private Label username;

    @FXML
    private Label email;

    @FXML
    private VBox publicPollsContainer;

    @FXML
    private void initialize() {
        // Get the current user info and populate the labels
        UserSession userSession = UserSession.getInstance();
        User currentUser = userSession.getCurrentUser();

        fullname.setText(currentUser.getFullName());
        username.setText(currentUser.getUsername());
        email.setText(currentUser.getEmail());


        // Fetch public polls from the database
        PollImplementation pollImplementation = new PollImplementation();
        List<Poll> publicPolls = pollImplementation.getAllPublicPolls();

        if (publicPolls.isEmpty()) {
            // If there are no public polls, show a message
            Label noPollsLabel = new Label("No public polls available");
            publicPollsContainer.getChildren().add(noPollsLabel);
        } else {
            // If there are public polls, display each poll with buttons
            for (Poll poll : publicPolls) {
                HBox pollEntry = createPollEntry(poll); // Create a method to generate UI elements for each poll
                publicPollsContainer.getChildren().add(pollEntry);
            }
        }
    }

    // Method to create UI elements for each poll
    private HBox createPollEntry(Poll poll) {
        HBox entry = new HBox(10);

        Label pollTitle = new Label(poll.getTitle());
        Button voteButton = new Button("Vote");
        Button resultsButton = new Button("Results");

        entry.getChildren().addAll(pollTitle, voteButton, resultsButton);

        // Add functionality to voteButton and resultsButton as needed

        return entry;
    }
    @FXML
    private void gotoForm(ActionEvent event) throws IOException {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/votingapp/form.fxml"));
        Parent root = loader.load();

        // Create a new stage
        Stage newStage = new Stage();
        Scene scene = new Scene(root);

        // Set the scene in the new stage
        newStage.setScene(scene);
        newStage.show();
    }
}
