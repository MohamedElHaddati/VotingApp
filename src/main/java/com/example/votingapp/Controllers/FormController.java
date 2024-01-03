package com.example.votingapp.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


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
    private Label choicesLabel;

    @FXML
    private Button createPollButton;

    // Initialize method for the controller
    public void initialize() {
        // Remove the duplicated entries
        visibilityComboBox.getItems().clear(); // Clear existing items
        visibilityComboBox.getItems().addAll("Public", "Private");
        visibilityComboBox.setValue("Public");

        // Use ArrayList to allow dynamic addition of values
        ArrayList<Integer> choicesList = new ArrayList<>();
        for (int i = 2; i <= 10; i++) {
            choicesList.add(i);
        }
        choicesComboBox.getItems().clear(); // Clear existing items
        choicesComboBox.getItems().addAll(choicesList);
        choicesComboBox.setValue(2);

        visibilityComboBox.setOnAction(event -> {
            if ("Private".equals(visibilityComboBox.getValue())) {
                privateCodeField.setDisable(false);
            } else {
                privateCodeField.setDisable(true);
            }
        });

        choicesComboBox.setOnAction(event -> {
            int numChoices = choicesComboBox.getValue();
            choicesLabel.setText("Choices:");

            // Clear previous choice fields
            choicesLabel.getParent().getChildrenUnmodifiable().filtered(node -> node instanceof TextField)
                    .forEach(node -> ((TextField) node).setText(""));

            // Add new choice fields
            for (int i = 0; i < numChoices; i++) {
                TextField choiceField = new TextField();
                choiceField.setPromptText("Choice " + (i + 1));
                // Add the new choice fields to the GridPane
                GridPane.setConstraints(choiceField, 1, i + 6); // Adjust the row index accordingly
                ((GridPane) choicesLabel.getParent()).getChildren().add(choiceField);
            }
        });
    }


    // Method to generate choice text fields dynamically
    private void generateChoiceFields(int numChoices) {
        // Remove previous choice fields
        choicesLabel.getParent().getChildrenUnmodifiable().filtered(node -> node instanceof TextField)
                .forEach(node -> ((TextField) node).setText(""));

        for (int i = 0; i < numChoices; i++) {
            TextField choiceField = new TextField();
            choiceField.setPromptText("Choice " + (i + 1));
            choicesLabel.getParent().getChildrenUnmodifiable().add(choiceField);
        }
    }

    @FXML
    private void onCreatePoll(ActionEvent event) {
        String title = titleField.getText();
        String description = descriptionArea.getText();
        String visibility = visibilityComboBox.getValue();
        String privateCode = privateCodeField.getText();
        int numChoices = choicesComboBox.getValue();

        // Retrieve choices
        StringBuilder choices = new StringBuilder();
        choicesLabel.getParent().getChildrenUnmodifiable().filtered(node -> node instanceof TextField)
                .forEach(node -> choices.append(((TextField) node).getText()).append("\n"));

        // Here, you can handle the creation of the poll with the gathered data
        // For example, you might create a Poll object and store the information
        // Or pass it to a service to handle the creation of the poll.
        // This example just prints the gathered information.
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Visibility: " + visibility);
        System.out.println("Private Code: " + privateCode);
        System.out.println("Number of Choices: " + numChoices);
        System.out.println("Choices:\n" + choices.toString());
    }
}

