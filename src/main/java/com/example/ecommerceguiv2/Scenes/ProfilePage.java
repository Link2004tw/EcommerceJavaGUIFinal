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
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Locale;

public class ProfilePage extends ScenePage {
    Database database;
    SceneController sceneController;

    public ProfilePage(Database db, SceneController sc) {
        database = db;
        sceneController = sc;
        createProfilePage(db, sc);

    }

    private void createProfilePage(Database db, SceneController sc) {
        Person p = db.isAdmin() ? db.getLoggedAdmin() : db.getLoggedCustomer();
        NavigationBar navigationBar = new NavigationBar(sc);
        if (p != null) {
            sc.setTitle("profile", p.getUsername());
            // Root layout
            VBox root = new VBox(20);
            root.setPadding(new Insets(20));
            root.setAlignment(Pos.CENTER);

            // Title Label
            Label titleLabel = new Label("Profile Page");
            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            // GridPane for profile details
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);

            // Add customer details to the grid
            grid.add(new Label("Username:"), 0, 0);
            grid.add(new Label(p.getUsername()), 1, 0);

            grid.add(new Label("Date of Birth:"), 0, 1);
            grid.add(new Label(p.getDateOfBirth()), 1, 1);

            grid.add(new Label("Gender:"), 0, 2);
            grid.add(new Label(String.valueOf(p.getGender()).toLowerCase()), 1, 2);

            grid.add(new Label("Balance:"), 0, 3);
            grid.add(new Label("$" + String.format("%.2f", p.getBalance())), 1, 3);

            if(db.isAdmin()){
                grid.add(new Label("Role:"), 0, 4);
                grid.add(new Label(((Admin)p).getRole()), 1, 4);
                grid.add(new Label("Working hours:"), 0, 5);
                grid.add(new Label(String.valueOf(((Admin)p).getWorkingHours()) + " hours"), 1, 5);

            }
            // Edit Profile button
            Button editProfileButton = new Button("Edit Profile");
            editProfileButton.setOnAction(event -> {
                // Leave this empty for now
                sc.switchToScene("editProfile");
            });

            // Add components to the root layout
            root.getChildren().addAll(navigationBar, titleLabel, grid, editProfileButton);
            // Scene and Stage Setup
            Scene s = new Scene(root, 400, 500);
            setScene(s);
        }
    }


    @Override
    public void refresh() {
        if(database != null && sceneController != null){
            createProfilePage(database, sceneController);
        }
    }
}
