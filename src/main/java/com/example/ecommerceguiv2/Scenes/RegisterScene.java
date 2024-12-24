package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.LabeledTextField;
import com.example.ecommerceguiv2.Models.Customer;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Components.SceneController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;


import java.text.ParseException;
import java.util.InputMismatchException;

public class RegisterScene extends ScenePage {

    public RegisterScene(Database db, SceneController sc) {
        BorderPane rootRegister = new BorderPane();

        // Title Label
        Label l2 = new Label("Register");
        l2.setStyle("-fx-font-family: Arial, sans-serif; -fx-font-weight: bold;");

        // VBox for text fields and other components
        VBox textFieldsContainerRegister = new VBox(10);
        textFieldsContainerRegister.setAlignment(Pos.CENTER);

        // Input fields
        LabeledTextField usernameField = new LabeledTextField("Username:");
        LabeledTextField passwordField = new LabeledTextField("Password:");
        LabeledTextField dateField = new LabeledTextField("Date Of Birth:");

        textFieldsContainerRegister.setStyle("-fx-alignment: center;");
        textFieldsContainerRegister.setPadding(new Insets(20, 40, 20, 40)); // Add padding for better spacing

        // Gender selection
        ToggleGroup group = new ToggleGroup();
        RadioButton male = new RadioButton("Male");
        male.setToggleGroup(group);
        RadioButton female = new RadioButton("Female");
        female.setToggleGroup(group);
        Label selectedOption = new Label("Selected: None");

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selected = (RadioButton) newValue;
                selectedOption.setText("Selected: " + selected.getText());
            }
        });

        VBox genderBox = new VBox(10, male, female, selectedOption);
        genderBox.setAlignment(Pos.CENTER);

        // Register button
        Button submitButtonRegister = new Button("Register");
        submitButtonRegister.setOnAction(e -> {
            try {
                RadioButton selectedRadio;
                if (group.getSelectedToggle() != null) {
                    selectedRadio = (RadioButton) group.getSelectedToggle();
                } else {
                    throw new InputMismatchException("Select a gender");
                }
                System.out.println(selectedRadio.getText());
                Customer customer = db.register(
                        usernameField.getText().trim(),
                        dateField.getText().trim(),
                        selectedRadio.getText().trim(),
                        passwordField.getText().trim()
                );
                if (customer != null) {
                    System.out.println("Registered");
                    usernameField.getTextField().clear();
                    passwordField.getTextField().clear();
                    dateField.getTextField().clear();
                    sc.switchToScene("dashboard");
                }
            } catch (ParseException ex) {
                showAlert("Invalid Date Format", "Please enter the date in the format (yyyy-MM-dd)");
            } catch (InputMismatchException ex) {
                showAlert("Invalid Gender", "Please choose a gender.");
            }
        });

        // Login button
        Button loginingButton = new Button("If you already have an account, log in");
        loginingButton.getStyleClass().add("button-link");
        loginingButton.setOnAction(e -> sc.switchToScene("login"));

        // Add components to the VBox
        textFieldsContainerRegister.getChildren().addAll(
                l2, usernameField, passwordField, dateField, genderBox, submitButtonRegister, loginingButton
        );

        rootRegister.setCenter(textFieldsContainerRegister);

        // Create the scene
        Scene s = new Scene(rootRegister, 400, 400);
        try {
            s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/styles.css").toExternalForm());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        // Add dynamic font resizing
        s.widthProperty().addListener((observable, oldValue, newValue) -> {
            double scaleFactor = newValue.doubleValue() / 400.0;
            updateFontSizes(l2, textFieldsContainerRegister, submitButtonRegister, loginingButton, scaleFactor);
        });

        s.heightProperty().addListener((observable, oldValue, newValue) -> {
            double scaleFactor = newValue.doubleValue() / 400.0;
            updateFontSizes(l2, textFieldsContainerRegister, submitButtonRegister, loginingButton, scaleFactor);
        });

        setScene(s);
    }

    // Helper method to update font sizes
    private void updateFontSizes(Label titleLabel, VBox container, Button submitButton, Button loginButton, double scaleFactor) {
        // Adjust font size based on scale factor
        double titleFontSize = Math.max(16, Math.min(32, 16 * scaleFactor));
        double normalFontSize = Math.max(12, Math.min(20, 14 * scaleFactor));

        // Set font size for title
        titleLabel.setStyle("-fx-font-family: Arial, sans-serif; -fx-font-size: " + titleFontSize + "px; -fx-font-weight: bold;");

        // Set font size for buttons and text fields
        container.getChildren().forEach(node -> {
            if (node instanceof LabeledTextField) {
                ((LabeledTextField) node).getLabel().setStyle("-fx-font-size: " + normalFontSize + "px;");
                ((LabeledTextField) node).getTextField().setStyle("-fx-font-size: " + normalFontSize + "px;");
            } else if (node instanceof VBox) {
                ((VBox) node).getChildren().forEach(child -> {
                    if (child instanceof Label || child instanceof RadioButton) {
                        ((Labeled) child).setStyle("-fx-font-size: " + normalFontSize + "px;");
                    }
                });
            }
        });

        submitButton.setStyle("-fx-font-size: " + normalFontSize + "px;");
        loginButton.setStyle("-fx-font-size: " + normalFontSize + "px;");
    }

    // Helper method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    @Override
    public void refresh() {

    }
}
