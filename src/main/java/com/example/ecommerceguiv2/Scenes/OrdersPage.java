package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.NavigationBar;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Order;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class OrdersPage extends ScenePage {
    Database database;
    SceneController sceneController;

    public OrdersPage(Database db, SceneController sc) {
        database = db;
        sceneController = sc;
        createPage(db, sc);
    }

    private void createPage(Database db, SceneController sc) {
        NavigationBar navigationBar = new NavigationBar(sc);
        List<Order> orders = new ArrayList<>();
        VBox container = new VBox(navigationBar);

        if (!db.isAdmin()) {
            for (Order order : db.getOrders()) {
                if (order.getCustomer() == db.getLoggedCustomer()) {
                    orders.add(order);
                }
            }
        } else {
            orders = db.getOrders();
        }

        if (orders.isEmpty()) {
            VBox vbox = new VBox(10); // Spacing between elements
            vbox.setAlignment(Pos.CENTER); // Center the elements

            // Create a label for "No orders yet"
            Label noOrdersLabel = new Label("No orders yet");

            // Create a button for "Continue Shopping"

            Button continueShoppingButton = new Button("Continue Shopping");
            continueShoppingButton.setOnAction(e -> {
                sc.switchToScene("products");
            });

            // Add the label and button to the VBox
            vbox.getChildren().add(noOrdersLabel);
            if(!db.isAdmin()){
                vbox.getChildren().add(continueShoppingButton);
            }
            container.getChildren().add(vbox);
        } else {
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(10));
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            // Add headers
            gridPane.add(new Label("Order ID"), 0, 0);
            gridPane.add(new Label("Status"), 1, 0);
            gridPane.add(new Label("Price"), 2, 0); // Price column header
            gridPane.add(new Label("Actions"), 3, 0); // Actions column header (for the buttons)

            // Add orders to the grid
            int i = 0;
            for (Order o : orders) {
                gridPane.add(new Label(String.valueOf(o.getId())), 0, i + 1);
                gridPane.add(new Label(o.getStatus()), 1, i + 1);
                gridPane.add(new Label("$" + String.format("%.2f", o.getTotal())), 2, i + 1); // Display total with 2 decimal places


                // Add "View Details" button
                Button viewDetailsButton = new Button("View Details");
                viewDetailsButton.setOnAction(e -> {
                    OrderDetailsPage orderDetailsPage = new OrderDetailsPage(o, sc, db);
                    sc.addScene("orderDetails", orderDetailsPage, "#"+o.getId());
                    sc.switchToScene("orderDetails");
                });

                gridPane.add(viewDetailsButton, 3, i + 1); // Add the button to the grid
                i++;
            }

            container.getChildren().add(gridPane);
        }

        // Wrap the VBox container in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true); // Ensure the content width matches the ScrollPane's width

        // Set up the scene and stage
        Scene scene = new Scene(scrollPane, 500, 300);
        setScene(scene);
    }


    @Override
    public void refresh() {
        createPage(database, sceneController);
    }
}
