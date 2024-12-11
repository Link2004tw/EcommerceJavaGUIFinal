package com.example.ecommerceguiv2;

import com.example.ecommerceguiv2.Exceptions.NotFoundException;
import com.example.ecommerceguiv2.Models.Customer;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Person;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException, NotFoundException {
        Database db = new Database();
        Date now = new Date();
        Customer customer = new Customer("Mark", "12345", now, 4000, Person.Gender.MALE);
        db.addCustomer(customer);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        BorderPane root = new BorderPane();
        // Create instances of LabeledTextField
        Label l1 = new Label("Log in");
        l1.setStyle("font-family: Arial, sans-serif;-fx-font-size: 16px;-fx-font-weight: bold;");
        VBox textFieldsContainer = new VBox(10); // Sub VBox for the text fields
        textFieldsContainer.setStyle("-fx-alignment: center;");

        // Create labeled text fields
        LabeledTextField usernameField = new LabeledTextField("Username:");
        LabeledTextField passwordField = new LabeledTextField("Password:");
        Button submitButton = new Button("Log in");
        Button registerButton = new Button("If you don't have an account register");
        registerButton.getStyleClass().add("button-link");

        submitButton.setOnAction(e -> {
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


        textFieldsContainer.getChildren().addAll(l1, usernameField, passwordField, submitButton, registerButton);
        root.setCenter(textFieldsContainer);

        Scene scene = new Scene(root, 400, 200);
        try {
            scene.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/styles.css").toExternalForm()); // Add the CSS file

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        primaryStage.setTitle("Custom Labeled TextField Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}