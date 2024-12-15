package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Database;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
        BorderPane borderPane = new BorderPane();

        // Create a title for the dashboard
        Label title = new Label("Customer Dashboard");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-alignment: center;");

        // Create a menu bar
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Options");
        MenuItem logout = new MenuItem("Logout");
        menu.getItems().add(logout);
        menuBar.getMenus().add(menu);

        // Create the center section (Product List)
        VBox productListBox = new VBox();
        Label productListTitle = new Label("Product List");
        productListTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Add products (in a real-world scenario, this would be dynamic)
        Button product1 = new Button("Product 1 - $10");
        Button product2 = new Button("Product 2 - $20");
        Button product3 = new Button("Product 3 - $30");

        productListBox.getChildren().addAll(productListTitle, product1, product2, product3);

        // Create the shopping cart section
        VBox cartBox = new VBox();
        Label cartTitle = new Label("Shopping Cart");
        cartTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Add Cart Items (dummy data for now)
        ListView<String> cartList = new ListView<>();
        cartList.getItems().addAll("Product 1 - $10", "Product 2 - $20");

        Button checkoutButton = new Button("Checkout");
        cartBox.getChildren().addAll(cartTitle, cartList, checkoutButton);

        // Create the order history section
        VBox orderHistoryBox = new VBox();
        Label orderHistoryTitle = new Label("Order History");
        orderHistoryTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Add some dummy order history data
        ListView<String> orderHistoryList = new ListView<>();
        orderHistoryList.getItems().addAll("Order #1 - $30", "Order #2 - $50");

        orderHistoryBox.getChildren().addAll(orderHistoryTitle, orderHistoryList);

        // Set layout positions
        borderPane.setTop(menuBar);
        borderPane.setLeft(productListBox);
        borderPane.setCenter(cartBox);
        borderPane.setRight(orderHistoryBox);
        borderPane.setTop(title);
        // Scene and Stage
        s = new Scene(borderPane, 800, 600);
        s.getStylesheets().add("dashboard.css");
        try {

            s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/dashboard.css").toExternalForm());
        } catch (RuntimeException e) {

        }

        setScene(s);
    }
}
