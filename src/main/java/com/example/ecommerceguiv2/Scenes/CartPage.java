package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Models.*;
import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.collections.FXCollections;

public class CartPage extends ScenePage {

    public CartPage(Database db) {
        Customer customer = db.getLoggedCustomer();

        if (customer != null) {
            Cart cart = customer.getCart();
            if (!cart.isEmpty()) {
                TableView<Item> table = new TableView<>();

                TableColumn<Item, String> productNameColumn = new TableColumn<>("Name");
                productNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getProduct().getName()));

                TableColumn<Item, Double> productPriceColumn = new TableColumn<>("Price");
                productPriceColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getProduct().getPrice()));

                TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
                quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                productNameColumn.setMinWidth(150);
                productPriceColumn.setMinWidth(200);
                quantityColumn.setMinWidth(100);
                table.getColumns().add(productNameColumn);
                table.getColumns().add(productPriceColumn);
                table.getColumns().add(quantityColumn);
                TableColumn<Item, Void> removeColumn = new TableColumn<>("Remove");
                removeColumn.setCellFactory(new Callback<TableColumn<Item, Void>, TableCell<Item, Void>>() {
                    @Override
                    public TableCell<Item, Void> call(TableColumn<Item, Void> param) {
                        return new TableCell<Item, Void>() {
                            private final Button removeButton = new Button("Remove");

                            {
                                removeButton.setOnAction(event -> {
                                    Item item = getTableView().getItems().get(getIndex());
                                    cart.removeProduct(item.getProduct());
                                    table.setItems(FXCollections.observableArrayList(cart.getProducts()));
                                });
                            }

                            @Override
                            protected void updateItem(Void item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    setGraphic(removeButton);
                                }
                            }
                        };
                    }
                });
                removeColumn.setMinWidth(100);
                table.getColumns().add(removeColumn);
                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setItems(FXCollections.observableArrayList(cart.getProducts()));
                table.getSelectionModel().clearSelection();

                Label titleLabel = new Label("My Cart");

                Button placeOrderButton = new Button("Place Order");
                placeOrderButton.setOnAction(e -> {
                    cart.placeOrder(db);
                    table.setItems(FXCollections.observableArrayList(cart.getProducts()));
                });

                Button clearCartButton = new Button("Clear Cart");
                clearCartButton.setOnAction(e -> {
                    cart.clearCart();
                    table.setItems(FXCollections.observableArrayList(cart.getProducts()));
                });
                Button continueShoppingButton = new Button("Continue Shopping");
                continueShoppingButton.setOnAction(e -> {
                    // Code
                });
                HBox buttonBox = new HBox(10, placeOrderButton, clearCartButton, continueShoppingButton);
                buttonBox.setStyle("-fx-alignment: center;");

                VBox vbox = new VBox(10, titleLabel, table, buttonBox);
                vbox.setStyle("-fx-alignment: center;");
                Scene s = new Scene(vbox, 600, 400);
                try {
                    s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/cart-styles.css").toExternalForm());
                } catch (NullPointerException e) {
                    System.out.println("Error");
                }
                setScene(s);

            } else {
                // When the cart is empty
                Label titleLabel = new Label("My Cart");

                Label emptyCartLabel = new Label("Your cart is currently empty.");

                Button continueShoppingButton = new Button("Continue Shopping");
                continueShoppingButton.setOnAction(e -> {
                    // code

                });

                VBox vbox = new VBox(20, titleLabel, emptyCartLabel, continueShoppingButton);
                vbox.setStyle("-fx-alignment: center; -fx-padding: 20px;");
                Scene s = new Scene(vbox, 600, 400);
                try {
                    s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/cart-styles.css").toExternalForm());
                } catch (NullPointerException e) {
                    System.out.println("Error");
                }
                setScene(s);
            }
        }
    }
}
