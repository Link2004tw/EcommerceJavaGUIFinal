package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Customer;
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
import javafx.stage.Stage;

public class DashbaordPage extends ScenePage {
    private Database database;

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
        database = db;
        // Top Navigation Bar
        HBox navigationBar = new HBox();
        navigationBar.setSpacing(20);
        navigationBar.setStyle("-fx-background-color: #2B579A; -fx-padding: 10px;");
        navigationBar.setAlignment(Pos.CENTER_LEFT);

        // Create navigation buttons
        Button shopButton = new Button(db.isAdmin() ? "Products" : "Shop");
        Button addCategoryButton = new Button("Add Category");

        shopButton.setOnAction(e -> {
            sc.switchToScene("products");
        });
        addCategoryButton.setOnAction(e -> {
            AddCategoryPage addCategoryPage = new AddCategoryPage(db, sc);
            sc.addScene("addCategory", addCategoryPage, "Add Category");
            sc.switchToScene("addCategory");
        });
        Button cartButton = new Button("Cart");
        Button ordersButton = new Button("Orders");
        Button addButton = new Button("Add Product");
        addButton.setOnAction(e -> {
            AddProductPage addProductPage = new AddProductPage(db, sc);
            sc.addScene("addProduct", addProductPage, "Add Product");
            sc.switchToScene("addProduct");
        });
        shopButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 5px 15px;");
        cartButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 5px 15px;");
        ordersButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 5px 15px;");
        addButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 5px 15px;");
        addCategoryButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 5px 15px;");
        cartButton.setOnAction(e -> {
            sc.switchToScene("cart");
        });

        ordersButton.setOnAction(e -> {
            sc.switchToScene("orders");

        });
        Button viewCustomersButton = new Button("View Customers");
        viewCustomersButton.setOnAction(e -> showCustomersWindow());
        // Username button
        Button usernameButton = new Button(db.isAdmin() ? db.getLoggedAdmin().getUsername() : db.getLoggedCustomer().getUsername());
        usernameButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-padding: 5px 15px;");
        usernameButton.setOnAction(e -> {
            sc.switchToScene("profile");
        });
        usernameButton.setAlignment(Pos.CENTER_RIGHT);
        if (db.isAdmin()) {
            navigationBar.getChildren().addAll(shopButton, addButton, ordersButton, viewCustomersButton, addCategoryButton, usernameButton);

        } else {
            navigationBar.getChildren().addAll(shopButton, cartButton, ordersButton, usernameButton);

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

    private void showCustomersWindow() {
        Stage customerStage = new Stage();
        customerStage.setTitle("List of Customers");

        ListView<String> customerListView = new ListView<>();

        for (Customer customer : database.getCustomers()) {
            customerListView.getItems().add(
                    customer.getUsername()
            );
        }

        VBox layout = new VBox(10, new Label("Current Customers"), customerListView);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 500);
        customerStage.setScene(scene);
        customerStage.show();
    }

    @Override
    public void refresh() {

    }
}
