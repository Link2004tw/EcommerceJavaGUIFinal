package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.LabeledTextField;
import com.example.ecommerceguiv2.Models.Customer;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.SceneController;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LoginScene {
    Scene scene;

    public LoginScene(Database db, SceneController sc) {
        BorderPane rootLogin = new BorderPane();
        Label l1 = new Label("Log In");
        l1.setStyle("font-family: Arial, sans-serif;-fx-font-size: 16px;-fx-font-weight: bold;");
        VBox textFieldsContainerLogin = new VBox(10); // Sub VBox for the text fields
        textFieldsContainerLogin.setStyle("-fx-alignment: center;");
        LabeledTextField usernameField = new LabeledTextField("Username:");
        LabeledTextField passwordField = new LabeledTextField("Password:");
        Button submitButtonLogin = new Button("Log in");
        Button registerButton = new Button("If you don't have an account register");
        registerButton.getStyleClass().add("button-link");

        registerButton.setOnAction(e -> {
            sc.switchToScene("register");
        });
        submitButtonLogin.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            try {

                Customer c1 = (Customer) db.login(username, password);
                if (c1 != null) {
                    System.out.println("Logged in");
                }
            } catch (Exception ex) {
                System.out.println("Incorrect Password");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failed Login");
                alert.setHeaderText("Incorrect Username and Password");

                alert.showAndWait();
            }
        });
        textFieldsContainerLogin.getChildren().addAll(l1, usernameField, passwordField, submitButtonLogin, registerButton);
        rootLogin.setCenter(textFieldsContainerLogin);

        scene = new Scene(rootLogin, 400, 200);
        try {
            scene.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/styles.css").toExternalForm()); // Add the CSS file
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    public Scene getScene() {
        return scene;
    }
}