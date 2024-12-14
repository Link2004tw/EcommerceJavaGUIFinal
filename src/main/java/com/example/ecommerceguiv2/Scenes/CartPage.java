package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Models.*;
import javafx.application.Application;
import javafx.scene.layout.HBox;
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

    public CartPage(Database db, Customer customer) {
        Cart cart = customer.getCart();

        Category electronics = new Category("Electronics", "Electric devices");
        Category clothing = new Category("Clothing", "Clothes and wearables");
        Category groceries = new Category("Groceries", "Food and stuff like that");
        Category[] categories = {electronics, clothing, groceries};
        for (Category c : categories) {
            db.addCategory(c);
        }
        Product p1 = new Product("Laptop", "High-performance laptop", 999.99, 10, electronics);
        db.addProduct(p1);
        Product p2 =  new Product("Jeans", "Comfortable denim jeans", 39.99, 40, clothing);
        db.addProduct(p2);
        Product p3 = new Product("Bread", "Freshly baked bread", 2.99, 100, groceries);

        cart.addProduct(p1, 3, db);
        cart.addProduct(p2, 5, db);
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

        HBox buttonBox = new HBox(10, placeOrderButton, clearCartButton);
        buttonBox.setStyle("-fx-alignment: center;");

        VBox vbox = new VBox(10, titleLabel, table, buttonBox);
        vbox.setStyle("-fx-alignment: center;");

        Scene s = new Scene(vbox, 600, 400);
        s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/cart-styles.css").toExternalForm());
        setScene(s);
        }

    }
