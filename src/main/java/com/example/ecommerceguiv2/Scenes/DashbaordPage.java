package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Database;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DashbaordPage extends ScenePage {

    private VBox createStatsCard(String title, String value) {
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER);
        card.setSpacing(5);
        card.setPadding(new Insets(10));
        card.setPrefSize(150, 100);
        card.setStyle("-fx-background-color: #1ABC9C; -fx-background-radius: 8;");

        Label cardTitle = new Label(title);
        cardTitle.setFont(new Font("Arial", 16));
        cardTitle.setTextFill(Color.WHITE);

        Label cardValue = new Label(value);
        cardValue.setFont(new Font("Arial", 20));
        cardValue.setTextFill(Color.WHITE);

        card.getChildren().addAll(cardTitle, cardValue);
        return card;
    }

    public DashbaordPage(SceneController sc, Database db) {
        Scene s;
        BorderPane mainLayout = new BorderPane();

        // Top Navigation Bar
        HBox navigationBar = new HBox();
        navigationBar.setSpacing(20);
        navigationBar.setStyle("-fx-background-color: #2B579A; -fx-padding: 10px;");
        navigationBar.setAlignment(Pos.CENTER_LEFT);

        // Create navigation buttons
        Button shopButton = new Button(db.isAdmin() ? "Products" : "Shop");
        shopButton.setOnAction(e -> {
            sc.switchToScene("products");
        });
        Button cartButton = new Button("Cart");
        Button ordersButton = new Button("Orders");
        Button addButton = new Button("Add Product");
        addButton.setOnAction(e-> {
            sc.switchToScene("addProduct");
        });
        shopButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 5px 15px;");
        cartButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 5px 15px;");
        ordersButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 5px 15px;");
        addButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 5px 15px;");

        // Username button
        Button usernameButton = new Button(db.isAdmin() ? db.getLoggedAdmin().getUsername() : db.getLoggedCustomer().getUsername());
        usernameButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 5px 15px;");
        usernameButton.setAlignment(Pos.CENTER_RIGHT);
        if (db.isAdmin()) {
            navigationBar.getChildren().addAll(shopButton, addButton, ordersButton, usernameButton);

        } else {
            navigationBar.getChildren().addAll(shopButton, ordersButton, usernameButton);

        }
        // Add buttons to the navigation bar
        HBox.setHgrow(usernameButton, Priority.ALWAYS);

        // Placeholder content for the center
        Label placeholder = new Label("Welcome to the Customer Dashboard");
        placeholder.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        VBox centerContent = new VBox(placeholder);
        centerContent.setAlignment(Pos.CENTER);

        // Assign navigation bar and center content
        mainLayout.setTop(navigationBar);
        mainLayout.setCenter(centerContent);

        // Scene setup
        s = new Scene(mainLayout, 800, 600);
        //s.getStylesheets().add("dashboard.css");
        try {

            s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/dashboard.css").toExternalForm());
        } catch (RuntimeException e) {

        }

        setScene(s);
    }
}
