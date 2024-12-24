package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.NavigationBar;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Exceptions.CantPerformAction;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Item;
import com.example.ecommerceguiv2.Models.Order;
import com.example.ecommerceguiv2.Scenes.ScenePage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class OrderDetailsPage extends ScenePage {
    private Order o;
    private SceneController sc;
    private Database db; // Assuming the database instance is passed for updates

    public OrderDetailsPage(Order order, SceneController sceneController, Database database) {
        this.o = order;
        this.sc = sceneController;
        this.db = database;
        createPage();
}

    private void createPage() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));
        container.setAlignment(Pos.TOP_CENTER);

        // Title
        Label titleLabel = new Label("Order Details");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Order Details Grid
        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(10);
        detailsGrid.setVgap(10);
        detailsGrid.setPadding(new Insets(10));
        detailsGrid.setAlignment(Pos.CENTER);

        // Add labels for the order details
        detailsGrid.add(new Label("Order ID:"), 0, 0);
        detailsGrid.add(new Label(String.valueOf(o.getId())), 1, 0);

        detailsGrid.add(new Label("Status:"), 0, 1);
        Label statusLabel = new Label(o.getStatus());
        detailsGrid.add(statusLabel, 1, 1);

        detailsGrid.add(new Label("Total Price:"), 0, 2);
        detailsGrid.add(new Label("$" + String.format("%.2f", o.getTotal())), 1, 2);

        // Add items to the order details
//        detailsGrid.add(new Label("Items:"), 0, 3);
//        VBox itemsList = new VBox(5);
//        for (Item item : o.getProducts()) { // Assuming o.getItems() returns a list of items in the order
//            itemsList.getChildren().add(new Label(
//                    item.getProduct().getName() + " - $" +
//                            String.format("%.2f", item.getProduct().getPrice()) + "*" +
//                            item.getQuantity() + " = " + item.getProduct().getPrice() * item.getQuantity()
//            ));
//        }
//        detailsGrid.add(itemsList, 4, 5);

        // Cancel Order Button
        Button cancelOrderButton = new Button(db.isAdmin() ? "Process Order": "Cancel Order");

        cancelOrderButton.setOnAction(e -> {
            if(!db.isAdmin()) {
                try {
                    if (!o.getStatus().equalsIgnoreCase("Cancelled")) {

                        o.cancelOrder(db);

                        statusLabel.setText("Cancelled");

                        // Show confirmation
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Order Cancelled");
                        alert.setHeaderText(null);
                        alert.setContentText("The order has been successfully cancelled.");
                        alert.showAndWait();
                        sc.goBack();
                    } else {
                        // Show alert if already cancelled
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setTitle("Already Cancelled");
                        alert.setHeaderText(null);
                        alert.setContentText("This order is already cancelled.");
                        alert.showAndWait();
                    }
                } catch (CantPerformAction ex) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Already Process");
                    alert.setHeaderText(null);
                    alert.setContentText("This order is already process and can't be cancelled.");
                    alert.showAndWait();
                }
            }else {
                o.processOrder(db);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Order Processed");
                alert.setHeaderText(null);
                alert.setContentText("The order has been successfully processed.");
                alert.showAndWait();
                sc.goBack();
            }
        });

        container.getChildren().addAll(new NavigationBar(sc), titleLabel, detailsGrid, cancelOrderButton);

        // Create the scene
        Scene scene = new Scene(container, 400, 400);
        setScene(scene);
    }

    public void refresh() {
        // Refresh logic can be added here if necessary, e.g., reloading order details
        createPage();
    }
}
