package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.NavigationBar;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Admin;
import com.example.ecommerceguiv2.Models.Customer;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Person;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EditProfilePage extends ScenePage {
    Database database;
    SceneController sceneController;

    public EditProfilePage(Database db, SceneController sc) {
        database = db;
        sceneController = sc;
        createProfilePage(db, sc);

    }

    private void createProfilePage(Database db, SceneController sc) {
        Person p = db.isAdmin() ? db.getLoggedAdmin() : db.getLoggedCustomer();
        if (p != null) {
            sc.setTitle("profile", p.getUsername());
            // Root layout
            VBox root = new VBox(10);
            root.setPadding(new Insets(20));
            root.setAlignment(Pos.TOP_CENTER);

            // Title
            Label titleLabel = new Label("Your Profile");
            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            root.getChildren().add(titleLabel);

            // Username field
            Label usernameLabel = new Label("Username:");
            TextField usernameField = new TextField();
            usernameField.setPrefWidth(300);
            usernameField.setText(p.getUsername());
            // Password field
            Label passwordLabel = new Label("Password:");
            PasswordField passwordField = new PasswordField();
            passwordField.setPrefWidth(300);
            passwordField.setText(p.getPassword());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = formatter.format(p.getBirthday());

            // Date of Birth field
            Label dobLabel = new Label("Date of Birth:");
            TextField dobField = new TextField();
            dobField.setPrefWidth(300);
            dobField.setText(formattedDate);
            // Balance field
            Label genderLabel = new Label("Gender:");
            ComboBox<String> genderComboBox = new ComboBox<>();
            genderComboBox.getItems().addAll("Male", "Female");
            genderComboBox.setPrefWidth(300);
            genderComboBox.setValue(p.getGender().toString().equals("MALE") ? "Male" : "Female");

            // Form layout
            GridPane form = new GridPane();
            form.setHgap(10);
            form.setVgap(10);
            form.setAlignment(Pos.CENTER);
            form.add(usernameLabel, 0, 0);
            form.add(usernameField, 1, 0);
            form.add(passwordLabel, 0, 1);
            form.add(passwordField, 1, 1);
            form.add(dobLabel, 0, 2);
            form.add(dobField, 1, 2);
            form.add(genderLabel, 0, 3);
            form.add(genderComboBox, 1, 3);
            Label roleLabel = new Label("Role:");
            TextField roleField = new TextField();
            roleField.setPrefWidth(300);
            Label workingLabel = new Label("Working hours:");
            TextField workingField = new TextField();
            workingField.setPrefWidth(300);

            if (db.isAdmin()) {
                roleField.setText(((Admin) p).getRole());
                workingField.setText(String.valueOf(((Admin) p).getWorkingHours()));
                form.add(roleLabel, 0, 4);
                form.add(roleField, 1, 4);
                form.add(workingLabel, 0, 5);
                form.add(workingField, 1, 5);
            }
            root.getChildren().add(form);

            // Buttons
            Button saveButton = new Button("Save");
            Button cancelButton = new Button("Cancel");
            HBox buttonBox = new HBox(15, saveButton, cancelButton);
            buttonBox.setAlignment(Pos.CENTER);
            VBox.setMargin(buttonBox, new Insets(20, 0, 0, 0));

            root.getChildren().add(buttonBox);

            // Add action listeners for buttons
            saveButton.setOnAction(e -> {
                String username = usernameField.getText().trim();
                String password = passwordField.getText().trim();
                String dob = dobField.getText().trim();
                String gender = genderComboBox.getValue();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Date now = new Date();
                try {
                    // Convert string to date
                    date = dateFormat.parse(dob);

                } catch (ParseException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Date Format");
                    alert.setContentText("Please enter a valid date in the (yyyy-MM-dd) format");
                    alert.showAndWait();
                    return;
                }
                if (date.after(now)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Date");
                    alert.setContentText("Please enter a valid date before our current date");
                    alert.showAndWait();
                    return;
                }
                p.updateProfile(username, password, date, p.getBalance(), Person.Gender.valueOf(gender.toUpperCase()));

                if (db.isAdmin()) {
                    ((Admin) p).setRole(roleField.getText().trim());
                    ((Admin) p).setWorkingHours(Integer.parseInt(workingField.getText().trim()));

                    db.update(Admin.class, (Admin) p);
                } else {
                    db.update(Customer.class, (Customer) p);
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Profile Saved");
                alert.setContentText("Profile information has been saved successfully!");
                alert.showAndWait();
                sc.goBack();
            });

            cancelButton.setOnAction(e -> {
                sc.goBack();
            });
            // Create and set the scene
            Scene scene = new Scene(root, 550, 400);

            setScene(scene);
        }
    }

    @Override
    public void refresh() {
        if (database != null && sceneController != null) {
            createProfilePage(database, sceneController);
        }
    }
}
