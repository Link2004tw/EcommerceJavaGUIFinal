package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Card;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Scenes.ScenePage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.YearMonth;

public class AddCardPage extends ScenePage {

    Database database;
    SceneController sceneController;

    public AddCardPage(Database db, SceneController sc){
        database = db;
        sceneController = sc;
    }

    @Override
    public void refresh() {
        // Initialize the form

        // Create a grid layout for the form
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        // Add fields to the form
        Label nameLabel = new Label("Cardholder Name:");
        TextField nameField = new TextField();
        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);

        Label numberLabel = new Label("Card Number:");
        TextField numberField = new TextField();
        numberField.setPromptText("Enter 16-digit card number");
        grid.add(numberLabel, 0, 1);
        grid.add(numberField, 1, 1);

        Label expiryLabel = new Label("Expiry Date (MM/YY):");
        TextField expiryField = new TextField();
        expiryField.setPromptText("MM/YY");
        grid.add(expiryLabel, 0, 2);
        grid.add(expiryField, 1, 2);

        Label cvvLabel = new Label("CVV:");
        PasswordField cvvField = new PasswordField();
        cvvField.setPromptText("3-digit code");
        grid.add(cvvLabel, 0, 3);
        grid.add(cvvField, 1, 3);

        // Add a submit button
        Button submitButton = new Button("Submit");
        grid.add(submitButton, 1, 4);

        // Add a cancel button
        Button cancelButton = new Button("Cancel");
        grid.add(cancelButton, 0, 4); // Position it next to the submit button

        // Validation and event handling
        submitButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String cardNumber = numberField.getText().trim();
            String expiry = expiryField.getText().trim();
            String cvv = cvvField.getText().trim();

            if (!validateCardholderName(name)) {
                showAlert("Invalid Cardholder Name", "Name cannot be empty.");
                return;
            }

            if (!validateCardNumber(cardNumber)) {
                showAlert("Invalid Card Number", "Card number must be 16 digits.");
                return;
            }

            YearMonth expiryDate = parseExpiryDate(expiry);
            if (expiryDate == null) {
                showAlert("Invalid Expiry Date", "Expiry date must be in the format MM/YY.");
                return;
            }

            if (!validateCVV(cvv)) {
                showAlert("Invalid CVV", "CVV must be exactly 3 digits.");
                return;
            }

            // Save the card
            Card card = new Card(name, cardNumber, expiryDate, cvv);
            System.out.println("Card saved successfully: " + card);
            database.getLoggedCustomer().addCard(card);
            showAlert("Success", "Card added successfully!");
            sceneController.goBack();
        });

        // Handle the cancel button
        cancelButton.setOnAction(e -> sceneController.goBack());

        // Set the scene and show the stage
        Scene scene = new Scene(grid, 400, 300);
        setScene(scene);
    }

    // Validation methods
    private boolean validateCardholderName(String name) {
        return name != null && !name.isEmpty();
    }

    private boolean validateCardNumber(String number) {
        return number.matches("\\d{16}");
    }

    private YearMonth parseExpiryDate(String expiry) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            return YearMonth.parse(expiry, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private boolean validateCVV(String cvv) {
        return cvv.matches("\\d{3}");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
