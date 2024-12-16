package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.ProductItem;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Product;
import com.example.ecommerceguiv2.Models.Category;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ProductPage extends ScenePage {
    public ProductPage(Database db, SceneController sc) {
        // Add sample categories and products
        Category electronics = new Category("Electronics", "Electric devices");
        Category clothing = new Category("Clothing", "Clothes and wearables");
        Category groceries = new Category("Groceries", "Food and stuff like that");
        Category[] categories = {electronics, clothing, groceries};

        for (Category c : categories) {
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

        // Title
        Label titleLabel = new Label("Available Products");

        // Product list container
        VBox productList = new VBox(10); // Spacing of 10 pixels
        productList.setPadding(new Insets(10));

        // Add ProductItem components to the VBox
        for (Product product : db.getProducts()) {
            ProductItem productItem = new ProductItem(product, db, sc);
            productList.getChildren().add(productItem);
        }

        // Wrap VBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(productList);
        scrollPane.setFitToWidth(true); // Allow the content to fit the width of the ScrollPane
        scrollPane.setPadding(new Insets(10)); // Add padding to the ScrollPane content

        // Create the scene
        VBox root = new VBox(10);
        root.getChildren().addAll(titleLabel, scrollPane);

        Scene scene = new Scene(root, 800, 600); // Set the size of the scene
        setScene(scene);
    }
//    public void refresh(){
//        Label titleLabel = new Label("Available Products");
//
//        // Product list container
//        VBox productList = new VBox(10); // Spacing of 10 pixels
//        productList.setPadding(new Insets(10));
//
//        // Add ProductItem components to the VBox
//        for (Product product : db.getProducts()) {
//            ProductItem productItem = new ProductItem(product, db, sc);
//            productList.getChildren().add(productItem);
//        }
//
//        // Wrap VBox in a ScrollPane
//        ScrollPane scrollPane = new ScrollPane(productList);
//        scrollPane.setFitToWidth(true); // Allow the content to fit the width of the ScrollPane
//        scrollPane.setPadding(new Insets(10)); // Add padding to the ScrollPane content
//
//        // Create the scene
//        VBox root = new VBox(10);
//        root.getChildren().addAll(titleLabel, scrollPane);
//
//        Scene scene = new Scene(root, 800, 600); // Set the size of the scene
//        setScene(scene);
//    }
}
