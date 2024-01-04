package com.example.votingapp.Controllers;

import com.example.votingapp.DAOImplementation.OptionImplementation;
import com.example.votingapp.DAOImplementation.PollImplementation;
import com.example.votingapp.DAOImplementation.VoteImplementation;
import com.example.votingapp.model.Option;
import com.example.votingapp.model.Poll;
import com.example.votingapp.model.User;
import com.example.votingapp.model.Vote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.votingapp.DAOImplementation.UserImplementation; // Import your UserImplementation class

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


// ...

    private VBox createPollEntry(Poll poll) {
        VBox entry = new VBox(10); // Add spacing between polls

        Label pollTitle = new Label(poll.getTitle());
        Label createdBy = new Label("Created by: " + UserImplementation.getUsernameForUserId(poll.getUserId())); // Display the username of the poll creator

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedCreationDate = poll.getDate().format(formatter);
        String formattedEndDate = poll.getEndDate().format(formatter); // Format dates as needed

        Label creationDateLabel = new Label("Created: " + formattedCreationDate);
        Label endDateLabel = new Label("End Date: " + formattedEndDate); // Display creation and end dates

        HBox buttonContainer = new HBox(10);
        Button voteButton = new Button("Vote");
        Button resultsButton = new Button("Results");

        if (LocalDateTime.now().isBefore(poll.getEndDate())) {
            // If current date is before end date, display both "Vote" and "Results" buttons
            voteButton.setOnAction(event -> handleVoteButton(poll.getId()));
            resultsButton.setOnAction(event -> handleResultsButton(poll.getId()));
            buttonContainer.getChildren().addAll(voteButton, resultsButton);
        } else {
            // If current date is after end date, display only "Results" button
            resultsButton.setOnAction(event -> handleResultsButton(poll.getId()));
            buttonContainer.getChildren().add(resultsButton);
        }

        Separator separator = new Separator(); // Add a line separator between polls

        entry.getChildren().addAll(pollTitle, createdBy, creationDateLabel, endDateLabel, buttonContainer, separator);

        return entry;
    }





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
                VBox pollEntry = createPollEntry(poll); // Create a method to generate UI elements for each poll
                publicPollsContainer.getChildren().add(pollEntry);
            }
        }
    }

    private final PollImplementation pollImplementation = new PollImplementation();



    private void handleVoteButton(int pollId) {


        UserSession userSession = UserSession.getInstance();
        User currentUser = userSession.getCurrentUser();


        OptionImplementation optionImplementation = new OptionImplementation(); // Instantiate OptionImplementation
        VoteImplementation voteImplementation = new VoteImplementation();
        int userIdd = currentUser.getId();

        boolean hasVoted = voteImplementation.hasUserVoted(userIdd, pollId);

        if (hasVoted) {
            // User has already voted for this poll, display an error message and return
            displayErrorMessage("You have already voted for this poll.");
            return;
        }

        Poll poll = pollImplementation.getPollById(pollId);
        if (poll != null) {
            int userId = pollImplementation.getUserIdFromPollId(pollId);
            String username = pollImplementation.getUsernameForUserId(userId);

            Stage voteStage = new Stage();
            voteStage.setTitle("Vote on Poll");

            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(10));

            Label titleLabel = new Label("Title: " + poll.getTitle());
            Label descriptionLabel = new Label("Description: " + poll.getDescription());
            Label createdByLabel = new Label("Created by: " + username);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedCreationDate = poll.getDate().format(formatter);
            String formattedEndDate = poll.getEndDate().format(formatter);

            Label creationDateLabel = new Label("Created: " + formattedCreationDate);
            Label endDateLabel = new Label("End Date: " + formattedEndDate);

            vbox.getChildren().addAll(titleLabel, descriptionLabel, createdByLabel, creationDateLabel, endDateLabel);

            List<Option> options = optionImplementation.getAllOptionsByPollId(pollId); // Get options for the poll

            // ...

            ToggleGroup choicesGroup = new ToggleGroup();

            if (options != null) {
                for (Option option : options) {
                    RadioButton choiceRadioButton = new RadioButton(option.getContent());
                    choiceRadioButton.setToggleGroup(choicesGroup);
                    vbox.getChildren().add(choiceRadioButton);
                }
                vbox.layout(); // Refresh layout
            } else {
                System.out.println("No options found for this poll.");
            }

            Button voteButton = new Button("Vote");
            voteButton.setOnAction(event -> {
                RadioButton selectedRadioButton = (RadioButton) choicesGroup.getSelectedToggle();

                if (selectedRadioButton != null) {
                    String selectedOptionText = selectedRadioButton.getText();
                    Option selectedOption = options.stream()
                            .filter(option -> option.getContent().equals(selectedOptionText))
                            .findFirst()
                            .orElse(null);

                    if (selectedOption != null) {
                        int selectedOptionId = selectedOption.getId();
                        int currentUserId = currentUser.getId();/* Your logic to get the current user ID */;
                        int currentPollId = poll.getId();

                        Vote vote = new Vote(currentUserId, currentPollId, selectedOptionId);
                        VoteImplementation voteImplementation2 = new VoteImplementation();
                        voteImplementation2.addVote(vote);

                        // Display a success message after voting
                        displayVoteSuccessMessage();
                        voteStage.close();
                    }
                } else {
                    // Display an error message if no option is selected
                    displayErrorMessage("Please select an option to vote.");
                }
            });


            vbox.getChildren().add(voteButton);

            Scene scene = new Scene(vbox, 400, 400);
            voteStage.setScene(scene);
            voteStage.show();
        }
    }

    private void displayVoteSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Vote successful!");
        alert.showAndWait();
    }

    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }




    private void handleResultsButton(int pollId) {
        UserSession userSession = UserSession.getInstance();
        User currentUser = userSession.getCurrentUser();
        OptionImplementation optionImplementation = new OptionImplementation();
        VoteImplementation voteImplementation = new VoteImplementation();




        Poll poll = pollImplementation.getPollById(pollId);
        if (poll != null) {
            List<Option> options = optionImplementation.getAllOptionsByPollId(pollId);
            if (options != null && !options.isEmpty()) {
                Stage resultsStage = new Stage();
                resultsStage.setTitle("Results for Poll");

                // Create a PieChart for displaying the results
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

                int totalVotes = 0;
                for (Option option : options) {
                    List<Vote> votesForOption = voteImplementation.getAllVotesForOption(option.getId());
                    int voteCount = votesForOption != null ? votesForOption.size() : 0;
                    totalVotes += voteCount;
                    if (voteCount > 0) {
                        pieChartData.add(new PieChart.Data(option.getContent(), voteCount));
                    }
                }

                if (pieChartData.isEmpty()) {
                    Label noVotesLabel = new Label("No votes for this poll.");
                    VBox vbox = new VBox(noVotesLabel);
                    vbox.setAlignment(Pos.CENTER);
                    Scene scene = new Scene(vbox, 300, 200);
                    resultsStage.setScene(scene);
                } else {
                    PieChart pieChart = new PieChart(pieChartData);
                    pieChart.setTitle("Results for Poll: " + poll.getTitle());

                    for (PieChart.Data data : pieChart.getData()) {
                        double percentage = (data.getPieValue() / totalVotes) * 100;
                        data.setName(data.getName() + " (" + String.format("%.2f", percentage) + "%)");
                    }

                    pieChart.getData().forEach(data -> {
                        if (data.getPieValue() == 0) {
                            data.getNode().setVisible(false);
                        }
                    });

                    pieChart.setLabelLineLength(10);
                    pieChart.setLegendSide(Side.BOTTOM);

                    // Hide entries in the legend that are not visible in the chart
                    pieChart.getData().forEach(data -> {
                        if (!data.getNode().isVisible()) {
                            data.getNode().setVisible(false);
                        }
                    });

                    pieChart.setLegendVisible(true); // Set to true by default

                    Scene scene = new Scene(pieChart, 600, 400);
                    resultsStage.setScene(scene);
                }

                resultsStage.show();
            } else {
                System.out.println("No options found for this poll.");
            }
        }
    }




    // Method to create UI elements for each poll

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

    @FXML
    private void logout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/votingapp/login.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
