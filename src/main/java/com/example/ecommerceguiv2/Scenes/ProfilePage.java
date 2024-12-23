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
            VBox root = new VBox();
            root.setPadding(new Insets(20));
            //root.setAlignment(Pos.CENTER);

            // Title Label
            Label titleLabel = new Label("Profile Page");

            // GridPane for profile details
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);

            String smallerLabelStyle = "-fx-font-size: 14px; -fx-font-weight: normal;";

            // Add customer details to the grid
            grid.add(new Label("Username:") {{
                setStyle("-fx-font-size: 14px;");
            }}, 0, 0);
            grid.add(new Label(p.getUsername()) {{
                setStyle(smallerLabelStyle);
            }}, 1, 0);

            grid.add(new Label("Date of Birth:") {{
                setStyle("-fx-font-size: 14px;");
            }}, 0, 1);
            grid.add(new Label(p.getDateOfBirth()) {{
                setStyle(smallerLabelStyle);
            }}, 1, 1);

            grid.add(new Label("Gender:") {{
                setStyle("-fx-font-size: 14px;");
            }}, 0, 2);
            grid.add(new Label(String.valueOf(p.getGender()).toLowerCase()) {{
                setStyle(smallerLabelStyle);
            }}, 1, 2);

            grid.add(new Label("Wallet:") {{
                setStyle("-fx-font-size: 14px;");
            }}, 0, 3);
            grid.add(new Label("$" + String.format("%.2f", p.getBalance())) {{
                setStyle(smallerLabelStyle);
            }}, 1, 3);

            if(db.isAdmin()){
                grid.add(new Label("Role:") {{
                    setStyle("-fx-font-size: 14px;");
                }}, 0, 4);
                grid.add(new Label(((Admin) p).getRole()) {{
                    setStyle(smallerLabelStyle);
                }}, 1, 4);

                grid.add(new Label("Working hours:") {{
                    setStyle("-fx-font-size: 14px;");
                }}, 0, 5);
                grid.add(new Label(String.valueOf(((Admin) p).getWorkingHours()) + " hours") {{
                    setStyle(smallerLabelStyle);
                }}, 1, 5);

            }
            // Edit Profile button
            Button editProfileButton = new Button("Edit Profile");
            editProfileButton.setOnAction(event -> {
                // Leave this empty for now
                sc.switchToScene("editProfile");
            });
            Button logoutButton = new Button("Log out");
            logoutButton.setOnAction(event -> {
                db.signOut();
            });
            HBox.setMargin(editProfileButton, new Insets(0, 20, 0, 20)); // Top, Right, Bottom, Left
            HBox.setMargin(logoutButton, new Insets(0, 20, 0, 20));
            HBox buttonBox = new HBox(editProfileButton, logoutButton);
            // Add components to the root layout
            buttonBox.setAlignment(Pos.CENTER);
            VBox.setMargin(buttonBox, new Insets(20, 0, 0, 0));

            root.getChildren().addAll(navigationBar, titleLabel, grid, buttonBox);
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
