package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.ProductItem;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Product;
import com.example.ecommerceguiv2.Models.Category;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ProductPage extends ScenePage{

    public ProductPage(Database db, SceneController sc){
        Category electronics = new Category("Electronics", "Electric devices");
        Category clothing = new Category("Clothing", "Clothes and wearables");
        Category groceries = new Category("Groceries", "Food and stuff like that");
        Category [] categories = {electronics,clothing,groceries};

        for (Category c: categories){
            db.addCategory(c);
        }

        Product[] products = new Product[]{
                // Electronics
                new Product("Laptop", "High-performance laptop", 999.99, 10, electronics),
                new Product("Smartphone", "Latest model smartphone", 799.99, 25, electronics),
                new Product("Headphones", "Noise-cancelling headphones", 199.99, 15, electronics),
                new Product("TV", "4K Ultra HD TV", 499.99, 8, electronics),
                new Product("Tablet", "10-inch display tablet", 299.99, 12, electronics),

                // Clothing
                new Product("T-shirt", "Cotton t-shirt", 19.99, 50, clothing),
                new Product("Jeans", "Comfortable denim jeans", 39.99, 40, clothing),
                new Product("Jacket", "Warm winter jacket", 79.99, 30, clothing),
                new Product("Sneakers", "Stylish sneakers", 59.99, 20, clothing),
                new Product("Shirt", "Formal shirt", 29.99, 25, clothing),

                // Groceries
                new Product("Bread", "Freshly baked bread", 2.99, 100, groceries),
                new Product("Milk", "1-liter milk carton", 1.49, 80, groceries),
                new Product("Cheese", "Cheddar cheese", 4.99, 60, groceries),
                new Product("Eggs", "12-pack eggs", 2.99, 75, groceries),
                new Product("Juice", "1-liter orange juice", 2.49, 50, groceries)
        };

        for (Product p : products) {
            db.addProduct(p);
        }
        Label titleLabel = new Label("Available Products");
//        Accordion accordion = new Accordion();
//        for (Category category : db.getCategories()) {
//            VBox productListBox = new VBox();
//            productListBox.setSpacing(5);
//
//            TableView<Product> tableView = new TableView<>();
//            tableView.setEditable(false);
//
//            TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
//            nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
//            TableColumn<Product, String> descriptionColumn = new TableColumn<>("Description");
//            descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
//            TableColumn<Product, Double> priceColumn = new TableColumn<>("Price($)");
//            priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
//
//            nameColumn.setMinWidth(150);
//            descriptionColumn.setMinWidth(200);
//            priceColumn.setMinWidth(100);
//            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//            tableView.getColumns().addAll(nameColumn, descriptionColumn, priceColumn);
//
//            ObservableList<Product> productList = FXCollections.observableArrayList(category.getProducts());
//            tableView.setItems(productList);
//
//            productListBox.getChildren().add(tableView);
//
//            TitledPane categoryPane = new TitledPane(category.getName(), productListBox);
//            accordion.getPanes().add(categoryPane);
//        }
//
//        BorderPane root = new BorderPane();
//        root.setTop(titleLabel);
//        root.setCenter(accordion);
//        BorderPane.setAlignment(titleLabel, Pos.CENTER);
        VBox productList = new VBox(10); // Spacing of 10 pixels
        productList.setPadding(new Insets(10));

        // Add ProductItem components to the VBox
        for (Product product : products) {
            ProductItem productItem = new ProductItem(product, db, sc);
            productList.getChildren().add(productItem);
        }

        // Add VBox to a ScrollPane to handle many items
        ScrollPane scrollPane = new ScrollPane(productList);
        scrollPane.setFitToWidth(true);

        // Create the scene and display it
        Scene scene = new Scene(scrollPane, 400, 300);
        Scene s = new Scene(productList, 800, 600);
        //s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/product-styles.css").toExternalForm());
        setScene(s);
    }


}
