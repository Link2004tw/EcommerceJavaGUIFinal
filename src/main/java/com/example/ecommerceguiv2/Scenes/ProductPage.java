//
//package com.example.ecommerceguiv2.Scenes;
//
//import com.example.ecommerceguiv2.Components.NavigationBar;
//import com.example.ecommerceguiv2.Components.ProductItem;
//import com.example.ecommerceguiv2.Components.SceneController;
//import com.example.ecommerceguiv2.Models.Category;
//import com.example.ecommerceguiv2.Models.Database;
//import com.example.ecommerceguiv2.Models.Product;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.*;
//import javafx.scene.Scene;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//
//public class ProductPage extends ScenePage {
//    private Database database;
//    private SceneController sceneController;
//
//    public ProductPage(Database db, SceneController sc) {
//        database = db;
//        sceneController = sc;
//    }
//
//    private void createPage(SceneController sc, Database db) {
//        NavigationBar navigationBar = new NavigationBar(sc);
//
//        // Title
//        Label titleLabel = new Label("Available Products");
//        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #000000;");
//
//        HBox titleContainer = new HBox(titleLabel);
//        titleContainer.setAlignment(Pos.CENTER);
//        titleContainer.setStyle("-fx-background-color: #F0F8FF;");
//
//        VBox productList = new VBox(10);
//        productList.setPadding(new Insets(10));
//        productList.setStyle("-fx-background-color: #F0F8FF;");
//
//        TextField searchBar = new TextField();
//        searchBar.setPromptText("Search products...");
//        searchBar.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-border-color: #0078d7; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-background-color: #ffffff;");
//
//        searchBar.setOnKeyReleased(e -> refreshProductList(searchBar.getText(), null, null, productList));
//
//        ComboBox<String> sortOptions = new ComboBox<>();
//        sortOptions.getItems().addAll("Sort by Name", "Sort by Price", "Sort by Category");
//        sortOptions.setOnAction(e -> refreshProductList(searchBar.getText(), sortOptions.getValue(), null, productList));
//
//        ComboBox<String> categoryFilter = new ComboBox<>();
//        categoryFilter.getItems().add("All Categories");
//        for (Category category : db.getCategories()) {
//
//            categoryFilter.getItems().add(category.getName()); // Assuming Database has a method getCategories()
//        }
//        categoryFilter.setValue("All Categories");
//        categoryFilter.setOnAction(e -> refreshProductList(searchBar.getText(), sortOptions.getValue(), categoryFilter.getValue(), productList));
//
//        HBox searchAndSortContainer = new HBox(10, searchBar, sortOptions, categoryFilter);
//        searchAndSortContainer.setAlignment(Pos.CENTER);
//        searchAndSortContainer.setPadding(new Insets(10));
//
//        ScrollPane scrollPane = new ScrollPane(productList);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setPadding(new Insets(10));
//
//        Button goToCartButton = new Button("Go to Cart");
//        goToCartButton.setOnAction(e -> {
//            sceneController.switchToScene("cart");
//        });
//        HBox buttonContainer = new HBox(goToCartButton);
//        buttonContainer.setAlignment(Pos.CENTER);
//        buttonContainer.setPadding(new Insets(10));
//
//        VBox root = new VBox(10);
//        root.getChildren().addAll(navigationBar, titleContainer, searchAndSortContainer, scrollPane);
//        if (!db.isAdmin()) {
//            root.getChildren().add(buttonContainer);
//        }
//        Scene scene = new Scene(root, 800, 600);
//        setScene(scene);
//    }
//
//    @Override
//    public void refresh() {
//        createPage(sceneController, database);
//    }
//
//    private void refreshProductList(String searchQuery, String sortOption, String category, VBox productList) {
//        productList.getChildren().clear();
//
//        var filteredProducts = database.getProducts().stream()
//                .filter(product -> (searchQuery == null || product.getName().toLowerCase().contains(searchQuery.toLowerCase())) &&
//                        (category == null || category.equals("All Categories") || product.getCategory().getName().equalsIgnoreCase(category)))
//                .sorted((p1, p2) -> {
//                    if ("Sort by Name".equals(sortOption)) {
//                        return p1.getName().compareToIgnoreCase(p2.getName());
//                    } else if ("Sort by Price".equals(sortOption)) {
//                        return Double.compare(p1.getPrice(), p2.getPrice());
//                    } else if ("Sort by Category".equals(sortOption)) {
//                        return p1.getCategory().getName().compareToIgnoreCase(p2.getCategory().getName());
//                    }
//                    return 0;
//                })
//                .toList();
//
//        for (Product product : filteredProducts) {
//            if (product.getStockQuantity() > 0) {
//                ProductItem productItem = new ProductItem(product, database, sceneController);
//                productList.getChildren().add(productItem);
//            }
//        }
//    }
//}
package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.NavigationBar;
import com.example.ecommerceguiv2.Components.ProductItem;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Category;
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

    public ProductPage(Database db, SceneController sc) {
        database = db;
        sceneController = sc;
    }

    private void createPage(SceneController sc, Database db) {
        NavigationBar navigationBar = new NavigationBar(sc);

        // Title
        Label titleLabel = new Label("Available Products");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #000000;");

        HBox titleContainer = new HBox(titleLabel);
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.setStyle("-fx-background-color: #F0F8FF;");

        VBox productList = new VBox(10);
        productList.setPadding(new Insets(10));
        productList.setStyle("-fx-background-color: #F0F8FF;");

        // Search Bar
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search products...");
        searchBar.setStyle("-fx-font-size: 14px; -fx-padding: 5px; -fx-border-color: #0078d7; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-background-color: #ffffff;");

        // Sort Options
        ComboBox<String> sortOptions = new ComboBox<>();
        sortOptions.getItems().addAll("Sort by Name", "Sort by Price", "Sort by Category");
        sortOptions.setValue(null); // Default to no sort

        // Category Filter
        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.getItems().add("All Categories");
        for (Category category : db.getCategories()) {
            categoryFilter.getItems().add(category.getName());
        }
        categoryFilter.setValue("All Categories");

        // Event Handlers
        searchBar.setOnKeyReleased(e -> refreshProductList(searchBar.getText(), sortOptions.getValue(), categoryFilter.getValue(), productList));
        sortOptions.setOnAction(e -> refreshProductList(searchBar.getText(), sortOptions.getValue(), categoryFilter.getValue(), productList));
        categoryFilter.setOnAction(e -> refreshProductList(searchBar.getText(), sortOptions.getValue(), categoryFilter.getValue(), productList));

        HBox searchAndSortContainer = new HBox(10, searchBar, sortOptions, categoryFilter);
        searchAndSortContainer.setAlignment(Pos.CENTER);
        searchAndSortContainer.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane(productList);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));

        Button goToCartButton = new Button("Go to Cart");
        goToCartButton.setOnAction(e -> {
            sceneController.switchToScene("cart");
        });
        HBox buttonContainer = new HBox(goToCartButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));

        VBox root = new VBox(10);
        root.getChildren().addAll(navigationBar, titleContainer, searchAndSortContainer, scrollPane);
        if (!db.isAdmin()) {
            root.getChildren().add(buttonContainer);
        }

        // Refresh product list initially with default values
        refreshProductList(null, null, "All Categories", productList);

        Scene scene = new Scene(root, 800, 600);
        setScene(scene);
    }

    @Override
    public void refresh() {
        createPage(sceneController, database);
    }

    private void refreshProductList(String searchQuery, String sortOption, String category, VBox productList) {
        productList.getChildren().clear();

        var filteredProducts = database.getProducts().stream()
                .filter(product -> (searchQuery == null || product.getName().toLowerCase().contains(searchQuery.toLowerCase())) &&
                        (category == null || category.equals("All Categories") || product.getCategory().getName().equalsIgnoreCase(category)))
                .sorted((p1, p2) -> {
                    if ("Sort by Name".equals(sortOption)) {
                        return p1.getName().compareToIgnoreCase(p2.getName());
                    } else if ("Sort by Price".equals(sortOption)) {
                        return Double.compare(p1.getPrice(), p2.getPrice());
                    } else if ("Sort by Category".equals(sortOption)) {
                        return p1.getCategory().getName().compareToIgnoreCase(p2.getCategory().getName());
                    }
                    return 0;
                })
                .toList();

        for (Product product : filteredProducts) {
            if (product.getStockQuantity() > 0) {
                ProductItem productItem = new ProductItem(product, database, sceneController);
                productList.getChildren().add(productItem);
            }
        }
    }
}
