package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.LabeledTextField;
import com.example.ecommerceguiv2.Models.Customer;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Person;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LoginScene extends ScenePage {

    public LoginScene(Database db, SceneController sc) {
        BorderPane rootLogin = new BorderPane();

        // Title Label
        Label l1 = new Label("Log In");
        l1.setStyle("-fx-font-family: Arial, sans-serif; -fx-font-weight: bold;");

        // VBox for text fields
        VBox textFieldsContainerLogin = new VBox(10);
        textFieldsContainerLogin.setStyle("-fx-alignment: center;");

        // Username and Password fields
        LabeledTextField usernameField = new LabeledTextField("Username:");
        LabeledTextField passwordField = new LabeledTextField("Password:");

        // Buttons
        Button submitButtonLogin = new Button("Log in");
        Button registerButton = new Button("If you don't have an account register");
        registerButton.getStyleClass().add("button-link");

        // Event Handlers
        registerButton.setOnAction(e -> sc.switchToScene("register"));
        submitButtonLogin.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            try {
                Person c1 = db.login(username, password);
                if (c1 != null) {
                    System.out.println("Logged in");
                    passwordField.getTextField().clear();
                    sc.switchToScene("dashboard");
                }
            } catch (Exception ex) {
                System.out.println("Incorrect Password");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed Login");
                alert.setHeaderText("Incorrect Username and Password");
                alert.showAndWait();
            }
        });

        // Add components to the VBox
        textFieldsContainerLogin.getChildren().addAll(l1, usernameField, passwordField, submitButtonLogin, registerButton);
        rootLogin.setCenter(textFieldsContainerLogin);

        // Create the scene
        Scene s = new Scene(rootLogin, 400, 250);
        try {
            s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/styles.css").toExternalForm());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        // Add dynamic font resizing
        s.widthProperty().addListener((observable, oldValue, newValue) -> {
            double scaleFactor = newValue.doubleValue() / 400.0;
            updateFontSizes(l1, textFieldsContainerLogin, submitButtonLogin, registerButton, scaleFactor);
        });

        s.heightProperty().addListener((observable, oldValue, newValue) -> {
            double scaleFactor = newValue.doubleValue() / 250.0;
            updateFontSizes(l1, textFieldsContainerLogin, submitButtonLogin, registerButton, scaleFactor);
        });

        setScene(s);
    }

    // Helper method to update font sizes
    private void updateFontSizes(Label titleLabel, VBox container, Button submitButton, Button registerButton, double scaleFactor) {
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
            }
        });

        submitButton.setStyle("-fx-font-size: " + normalFontSize + "px;");
        registerButton.setStyle("-fx-font-size: " + normalFontSize + "px;");
    }


    @Override
    public void refresh() {

    }
}