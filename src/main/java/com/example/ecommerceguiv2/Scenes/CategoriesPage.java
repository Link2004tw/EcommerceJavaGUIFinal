package com.example.ecommerceguiv2.Scenes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CategoriesPage extends ScenePage {

    private VBox layout;

    public CategoriesPage(Stage stage) {
        // Initialize the layout
        layout = new VBox(10); // 10px spacing between elements
        layout.setPadding(new Insets(20));

        // Add category buttons (example categories)
        Button electronicsButton = new Button("Electronics");
        Button clothingButton = new Button("Clothing");
        Button groceriesButton = new Button("Groceries");
        Button homeAppliancesButton = new Button("Home Appliances");

        // Add button actions
        electronicsButton.setOnAction(e -> openCategory("Electronics"));
        clothingButton.setOnAction(e -> openCategory("Clothing"));
        groceriesButton.setOnAction(e -> openCategory("Groceries"));
        homeAppliancesButton.setOnAction(e -> openCategory("Home Appliances"));

        // Add buttons to the layout
        layout.getChildren().addAll(electronicsButton, clothingButton, groceriesButton, homeAppliancesButton);

        // Create the scene and set it on the stage
        Scene scene = new Scene(layout, 400, 300); // 400x300 window size
        stage.setScene(scene);
        stage.setTitle("Categories Page");
    }

    @Override
    public void refresh() {
        // Example: Update the page layout dynamically
        System.out.println("Refreshing Categories Page...");
        layout.getChildren().clear();

        // Dynamically load or update categories (static for now)
        Button booksButton = new Button("Books");
        booksButton.setOnAction(e -> openCategory("Books"));

        layout.getChildren().add(booksButton);
    }

    private void openCategory(String categoryName) {
        // Navigate to the selected category
        System.out.println("Opening category: " + categoryName);
        // Logic to load the category-specific page can go here
    }
}
