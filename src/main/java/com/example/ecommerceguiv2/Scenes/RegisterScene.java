package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.LabeledTextField;
import com.example.ecommerceguiv2.Models.Customer;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Components.SceneController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.text.ParseException;
import java.util.InputMismatchException;

public class RegisterScene extends ScenePage {

    public RegisterScene(Database db, SceneController sc) {
        BorderPane rootregister = new BorderPane();

        Label l2 = new Label("Register");
        l2.setStyle("font-family: Arial, sans-serif;-fx-font-size: 16px;-fx-font-weight: bold;");


        VBox textFieldsContainerRegister = new VBox(10); // Sub VBox for the text fields
        textFieldsContainerRegister.setStyle("-fx-alignment: center;");

        LabeledTextField usernameField = new LabeledTextField("Username:");
        LabeledTextField passwordField = new LabeledTextField("Password:");
        LabeledTextField dateField = new LabeledTextField("Date Of Birth:");

        //textFieldsContainerRegister.getChildren().addAll();

        ToggleGroup group = new ToggleGroup();
        RadioButton male = new RadioButton("Male");
        male.setToggleGroup(group); // Add to ToggleGroup
        RadioButton female = new RadioButton("Female");
        female.setToggleGroup(group); // Add to ToggleGroup
        Label selectedOption = new Label("Selected: None");
        Button submitButtonRegister = new Button("Register");

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selected = (RadioButton) newValue;
                selectedOption.setText("Selected: " + selected.getText());
            }
        });

        VBox genderBox = new VBox(10, male, female, selectedOption);

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
                    System.out.println("Logged in");

                    sc.switchToScene("dashboard");
                }
            } catch (ParseException ex) {
                System.out.println("Invalid Date Format");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Date Format");
                alert.setHeaderText("Please enter the date in the (yyyy-MM-dd)");

                alert.showAndWait();
            } catch (InputMismatchException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Gender");
                alert.setHeaderText("Please Chose a gender");

                alert.showAndWait();
            }
        });

        Button loginingButton = new Button("If you already have an account log in");

        loginingButton.getStyleClass().add("button-link");

        loginingButton.setOnAction(e -> {
            sc.switchToScene("login");
        });


        textFieldsContainerRegister.getChildren().addAll(l2, usernameField, passwordField, dateField, genderBox, submitButtonRegister, loginingButton);
        rootregister.setCenter(textFieldsContainerRegister);
        Scene s = new Scene(rootregister, 400, 400);
        try {
            s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/styles.css").toExternalForm()); // Add the CSS file
            setScene(s);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
