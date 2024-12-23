package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.NavigationBar;
import com.example.ecommerceguiv2.Components.ProductItem;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductPage extends ScenePage {
    private Database database;
    private SceneController sceneController;
    //private VBox productList;

    public ProductPage(Database db, SceneController sc) {

        database = db;
        sceneController = sc;
        // Add sample categories and products

    }
    private void createPage(SceneController sc, Database db){
        NavigationBar navigationBar = new NavigationBar(sc);
        // Title
        Label titleLabel = new Label("Available Products");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #000000;"); // Style for the title label

        HBox titleContainer = new HBox(titleLabel);
        titleContainer.setAlignment(Pos.CENTER); // Center align the label
        titleContainer.setStyle("-fx-background-color: #F0F8FF;");

        VBox productList = new VBox(10); // Spacing of 10 pixels
        productList.setPadding(new Insets(10));
        productList.setStyle("-fx-background-color: #F0F8FF;");

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search products...");
        searchBar.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-border-color: #0078d7; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-background-color: #ffffff;");

        searchBar.setOnKeyReleased(e -> refreshProductList(searchBar.getText(), null, productList));

        ComboBox<String> sortOptions = new ComboBox<>();
        sortOptions.getItems().addAll("Sort by Name", "Sort by Price");
        sortOptions.setOnAction(e -> refreshProductList(searchBar.getText(), sortOptions.getValue(), productList));

        HBox searchAndSortContainer = new HBox(10, searchBar, sortOptions);
        searchAndSortContainer.setAlignment(Pos.CENTER);
        searchAndSortContainer.setPadding(new Insets(10));


        // Add ProductItem components to the VBox
        for (Product product : db.getProducts()) {
            if (product.getStockQuantity() > 0) {
                ProductItem productItem = new ProductItem(product, db, sc);
                productList.getChildren().add(productItem);
            }
        }

        // Wrap VBox in a ScrollPane
        ScrollPane scrollPane = new ScrollPane(productList);
        scrollPane.setFitToWidth(true); // Allow the content to fit the width of the ScrollPane
        scrollPane.setPadding(new Insets(10)); // Add padding to the ScrollPane content

        Button goToCartButton = new Button("Go to Cart");
        goToCartButton.setOnAction(e -> {
            sceneController.switchToScene("cart");
        });
        HBox buttonContainer = new HBox(goToCartButton);
        buttonContainer.setAlignment(Pos.CENTER); // Center align the button
        buttonContainer.setPadding(new Insets(10));

        // Create the scene
        VBox root = new VBox(10);
        root.getChildren().addAll(navigationBar ,titleContainer, searchAndSortContainer, scrollPane, buttonContainer);

        Scene scene = new Scene(root, 800, 600); // Set the size of the scene
        setScene(scene);
    }
    @Override
    public void refresh(){
        createPage(sceneController, database);
    }
    private void refreshProductList(String searchQuery, String sortOption, VBox productList) {
        productList.getChildren().clear();

        // Fetch and filter products
        var filteredProducts = database.getProducts().stream()
                .filter(product -> searchQuery == null || product.getName().toLowerCase().contains(searchQuery.toLowerCase()))
                .sorted((p1, p2) -> {
                    if ("Sort by Name".equals(sortOption)) {
                        return p1.getName().compareToIgnoreCase(p2.getName());
                    } else if ("Sort by Price".equals(sortOption)) {
                        return Double.compare(p1.getPrice(), p2.getPrice());
                    }
                    return 0;
                })
                .toList();

        // Add ProductItems to productList
        for (Product product : filteredProducts) {
            if (product.getStockQuantity() > 0) {
                ProductItem productItem = new ProductItem(product, database, sceneController);
                productList.getChildren().add(productItem);
            }
        }
    }
}