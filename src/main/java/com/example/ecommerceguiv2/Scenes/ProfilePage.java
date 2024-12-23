package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.NavigationBar;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Admin;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Person;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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
            BorderPane root = new BorderPane();
            root.setPadding(new Insets(20));

            // Header with NavigationBar and "Profile Page" title
            VBox headerBox = new VBox();
            Label profilePageLabel = new Label("Profile Page");
            profilePageLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            headerBox.getChildren().addAll(navigationBar, profilePageLabel);
            headerBox.setSpacing(10);
            headerBox.setAlignment(Pos.CENTER);

            // GridPane for profile details
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);

            String smallerLabelStyle = "-fx-font-size: 14px; -fx-font-weight: normal;";

            // Add profile details to the grid
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

            grid.add(new Label("Balance:") {{
                setStyle("-fx-font-size: 14px;");
            }}, 0, 3);
            grid.add(new Label("$" + String.format("%.2f", p.getBalance())) {{
                setStyle(smallerLabelStyle);
            }}, 1, 3);

            if (db.isAdmin()) {
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

            // Footer Buttons
            Button editProfileButton = new Button("Edit Profile");
            editProfileButton.setOnAction(event -> sc.switchToScene("editProfile"));

            Button logoutButton = new Button("Log out");
            logoutButton.setOnAction(event -> db.signOut());

            HBox buttonBox = new HBox(editProfileButton, logoutButton);

            buttonBox.setSpacing(20);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(10, 0, 10, 0));
            if(!db.isAdmin()){
                Button addCardButton = new Button("Add Card");
                addCardButton.setOnAction(e-> {
                    sceneController.addScene("addCard", new AddCardPage(), "Add Card");
                    sceneController.switchToScene("addCard");
                });
                buttonBox.getChildren().add(addCardButton);
            }
            // Assemble Root Layout
            root.setTop(headerBox);    // Header with NavigationBar and "Profile Page" title
            root.setCenter(grid);      // Profile details in the center
            root.setBottom(buttonBox); // Footer with buttons

            // Scene Setup
            Scene s = new Scene(root, 400, 500);

            // Add dynamic font resizing
            s.widthProperty().addListener((observable, oldValue, newValue) -> {
                double scaleFactor = newValue.doubleValue() / 400.0;
                double fontSize = Math.max(12, Math.min(24, 14 * scaleFactor));
                profilePageLabel.setStyle("-fx-font-size: " + Math.max(18, Math.min(30, 20 * scaleFactor)) + ";");
                grid.getChildren().forEach(node -> {
                    if (node instanceof Label) {
                        ((Label) node).setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: normal;");
                    }
                });
                editProfileButton.setStyle("-fx-font-size: " + Math.max(14, Math.min(20, 16 * scaleFactor)) + ";");
                logoutButton.setStyle("-fx-font-size: " + Math.max(14, Math.min(20, 16 * scaleFactor)) + ";");
            });

            s.heightProperty().addListener((observable, oldValue, newValue) -> {
                double scaleFactor = newValue.doubleValue() / 500.0;
                double fontSize = Math.max(12, Math.min(24, 14 * scaleFactor));
                profilePageLabel.setStyle("-fx-font-size: " + Math.max(18, Math.min(30, 20 * scaleFactor)) + ";");
                grid.getChildren().forEach(node -> {
                    if (node instanceof Label) {
                        ((Label) node).setStyle("-fx-font-size: " + fontSize + "; -fx-font-weight: normal;");
                    }
                });
                editProfileButton.setStyle("-fx-font-size: " + Math.max(14, Math.min(20, 16 * scaleFactor)) + ";");
                logoutButton.setStyle("-fx-font-size: " + Math.max(14, Math.min(20, 16 * scaleFactor)) + ";");
            });

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
