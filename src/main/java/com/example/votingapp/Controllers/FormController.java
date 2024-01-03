package com.example.votingapp.Controllers;

import com.example.votingapp.DAOImplementation.OptionImplementation;
import com.example.votingapp.DAOImplementation.PollImplementation;
import com.example.votingapp.model.Option;
import com.example.votingapp.model.Poll;
import com.example.votingapp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FormController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ComboBox<String> visibilityComboBox;

    @FXML
    private TextField privateCodeField;

    @FXML
    private ComboBox<Integer> choicesComboBox;

    @FXML
    private GridPane choicesGridPane;

    @FXML
    private ComboBox<String> deletionIntervalComboBox;

    public void initialize() {
        visibilityComboBox.getItems().addAll("Public", "Private");
        visibilityComboBox.setValue("Public");

        privateCodeField.setDisable(true);

        visibilityComboBox.setOnAction(event -> {
            if ("Private".equals(visibilityComboBox.getValue())) {
                privateCodeField.setDisable(false);
            } else {
                privateCodeField.setDisable(true);
            }
        });

        choicesComboBox.setOnAction(event -> {
            int numChoices = choicesComboBox.getValue();
            choicesGridPane.getChildren().clear();

            for (int i = 0; i < numChoices; i++) {
                TextField choiceField = new TextField();
                choiceField.setPromptText("Choice " + (i + 1));
                choiceField.setPrefWidth(200); // Set preferred width
                choiceField.setId("choiceField" + i); // Set unique FXID for each choice field
                choicesGridPane.add(choiceField, 0, i);
            }
        });
    }

    @FXML
    private Label successLabel;
    @FXML
    private void onCreatePoll(ActionEvent event) {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        String visibility = visibilityComboBox.getValue();
        String privateCode = privateCodeField.getText();
        int numChoices = choicesComboBox.getValue();

        StringBuilder choices = new StringBuilder();
        List<Integer> optionIds = new ArrayList<>();

        LocalDateTime currentDate = LocalDateTime.now();

        String selectedInterval = deletionIntervalComboBox.getValue();
        LocalDateTime endDateTime;

        switch (selectedInterval) {
            case "After an Hour":
                endDateTime = currentDate.plusHours(1);
                break;
            case "After a Day":
                endDateTime = currentDate.plusDays(1);
                break;
            case "After a Week":
                endDateTime = currentDate.plusWeeks(1);
                break;
            case "After a Month":
                endDateTime = currentDate.plusMonths(1);
                break;
            case "Never":
            default:
                endDateTime = LocalDateTime.MAX; // Set to the maximum possible value as "never"
                break;
        }

        UserSession userSession = UserSession.getInstance();
        User currentUser = userSession.getCurrentUser();

        int userId = currentUser.getId();

        // Create a Poll object with the gathered data
        Poll poll = new Poll(userId, title, description, currentDate, endDateTime,
                visibility.equals("Public") ? 1 : 0, privateCode);

        // Add the poll to the database and get its ID
        PollImplementation pollImplementation = new PollImplementation();
        int pollId = pollImplementation.addPollAndGetId(poll);

        // Add choices/options to the database and retrieve their IDs
        OptionImplementation optionImplementation = new OptionImplementation();
        for (int i = 0; i < numChoices; i++) {

            TextField choiceField = (TextField) choicesGridPane.lookup("#choiceField" + i);
            String choiceContent = choiceField.getText().trim();
            Option option = new Option(pollId, choiceContent);
            int optionId = optionImplementation.addOptionAndGetId(option);
            optionIds.add(optionId);
            choices.append(choiceContent).append("\n");
        }


        successLabel.setText("Poll created successfully!");
        successLabel.setVisible(true);


        // Here, you can handle any further actions or UI updates after creating the poll and choices
        System.out.println("Poll created successfully with choices: \n" + choices);
    }


}
