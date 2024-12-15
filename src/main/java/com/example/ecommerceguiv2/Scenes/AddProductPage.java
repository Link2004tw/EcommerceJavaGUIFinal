package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Models.Database;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class AddProductPage extends ScenePage{

    public AddProductPage(Database db){
        Scene s = null;
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20)); // Padding around grid
        gridPane.setHgap(10); // Horizontal spacing
        gridPane.setVgap(10); // Vertical spacing

        // --- Form Elements ---
        // Product Name
        Label nameLabel = new Label("Product Name:");
        TextField nameField = new TextField();
        gridPane.add(nameLabel, 0, 0); // (column, row)
        gridPane.add(nameField, 1, 0);

        // Product Price
        Label priceLabel = new Label("Product Price:");
        TextField priceField = new TextField();
        gridPane.add(priceLabel, 0, 1);
        gridPane.add(priceField, 1, 1);

        // Product Quantity
        Label quantityLabel = new Label("Product Quantity:");
        TextField quantityField = new TextField();
        gridPane.add(quantityLabel, 0, 2);
        gridPane.add(quantityField, 1, 2);

        // --- Submit Button ---
        Button submitButton = new Button("Add Product");
        gridPane.add(submitButton, 1, 3);

        // --- Action Handling ---
        submitButton.setOnAction(e -> {
            // Retrieve input data
            String name = nameField.getText();
            String price = priceField.getText();
            String quantity = quantityField.getText();

            // Simple validation
            if (name.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
                System.out.println("Please fill in all the fields.");
            } else {
                System.out.println("Product Added:");
                System.out.println("Name: " + name);
                System.out.println("Price: " + price);
                System.out.println("Quantity: " + quantity);
            }
        });

        // --- Scene and Stage ---
        VBox root = new VBox(10); // Root layout
        root.getChildren().addAll(gridPane); // Add gridPane to VBox
        root.setPadding(new Insets(10));

        s = new Scene(root, 400, 250);
        setScene(s);
    }
}
