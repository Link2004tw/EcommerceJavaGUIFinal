package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.NavigationBar;
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
    private Database database;
    private SceneController sceneController;
    public ProductPage(Database db, SceneController sc) {

        database = db;
        sceneController = sc;
        // Add sample categories and products
        NavigationBar navigationBar = new NavigationBar(sc);
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
        root.getChildren().addAll(navigationBar, titleLabel, scrollPane);

        Scene scene = new Scene(root, 800, 600); // Set the size of the scene
        setScene(scene);
    }

    public void refresh(){
        Label titleLabel = new Label("Available Products");
        NavigationBar navigationBar = new NavigationBar(sceneController);

        // Product list container
        VBox productList = new VBox(10); // Spacing of 10 pixels
        productList.setPadding(new Insets(10));

        // Add ProductItem components to the VBox
        for (Product product : database.getProducts()) {
            ProductItem productItem = new ProductItem(product, database, sceneController);
            productList.getChildren().add(productItem);
        }

        // Wrap VBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(productList);
        scrollPane.setFitToWidth(true); // Allow the content to fit the width of the ScrollPane
        scrollPane.setPadding(new Insets(10)); // Add padding to the ScrollPane content

        // Create the scene
        VBox root = new VBox(10);
        root.getChildren().addAll(navigationBar ,titleLabel, scrollPane);

        Scene scene = new Scene(root, 800, 600); // Set the size of the scene
        setScene(scene);
    }
}
