package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Category;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Product;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddProductPage extends ScenePage {
    // Admin page to add new products
    public AddProductPage(Database db, SceneController sc) {
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

        // Product Description
        Label descriptionLabel = new Label("Product Description:");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(4); // Set preferred row count for multiline input
        descriptionArea.setWrapText(true); // Enable text wrapping
        gridPane.add(descriptionLabel, 0, 3);
        gridPane.add(descriptionArea, 1, 3);

        // Product Category (Radio Buttons)
        Label categoryLabel = new Label("Category:");
        ToggleGroup categoryGroup = new ToggleGroup();
        VBox categoryBox = new VBox(5); // Layout for the radio buttons
        categoryBox.setPadding(new Insets(10, 0, 0, 0));

        for (Category category : db.getCategories()) {
            RadioButton radioButton = new RadioButton(category.getName());
            radioButton.setToggleGroup(categoryGroup);
            categoryBox.getChildren().add(radioButton);
        }

        gridPane.add(categoryLabel, 0, 4);
        gridPane.add(categoryBox, 1, 4);

        // --- Submit Button ---
        Button submitButton = new Button("Add Product");
        gridPane.add(submitButton, 1, 5);

        // --- Action Handling ---
        submitButton.setOnAction(e -> {
            // Retrieve input data
            String name = nameField.getText();
            String price = priceField.getText();
            String quantity = quantityField.getText();
            String description = descriptionArea.getText();
            RadioButton selectedCategoryButton = (RadioButton) categoryGroup.getSelectedToggle();
            String selectedCategory = selectedCategoryButton != null ? selectedCategoryButton.getText() : null;

            // Simple validation
            if (name.isEmpty() || price.isEmpty() || quantity.isEmpty() || description.isEmpty() || selectedCategory == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input!");
                alert.setContentText("Please fill in all the fields and select a category.");
                alert.showAndWait();
            } else {
//                System.out.println("Product Added:");
//                System.out.println("Name: " + name);
//                System.out.println("Price: " + price);
//                System.out.println("Quantity: " + quantity);
//                System.out.println("Description: " + description);
//                System.out.println("Category: " + selectedCategory);
                Category category = db.findByName(selectedCategory, Category.class);
                db.addProduct(new Product(name, description, Double.parseDouble(price), Integer.parseInt(quantity), category));
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Product Added Successfully!!!");
                alert.showAndWait();
                for(Product product: db.getProducts()){
                    System.out.println(product.toString());
                }
                ProductPage productPage = new ProductPage(db, sc);
                sc.addScene("products", productPage.getScene(), "Products");
                sc.switchToScene("products");
            }
        });

        // --- Scene and Stage ---
        VBox root = new VBox(10); // Root layout
        root.getChildren().addAll(gridPane); // Add gridPane to VBox
        root.setPadding(new Insets(10));

        s = new Scene(root, 700, 400); // Adjusted size for category selection
        setScene(s);
    }

    public AddProductPage(Database db, Product product, SceneController sc) {
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

        // Product Description
        Label descriptionLabel = new Label("Product Description:");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPrefRowCount(4); // Set preferred row count for multiline input
        descriptionArea.setWrapText(true); // Enable text wrapping
        gridPane.add(descriptionLabel, 0, 3);
        gridPane.add(descriptionArea, 1, 3);

        // Product Category (Radio Buttons)
        Label categoryLabel = new Label("Category:");
        ToggleGroup categoryGroup = new ToggleGroup();
        VBox categoryBox = new VBox(5); // Layout for the radio buttons
        categoryBox.setPadding(new Insets(10, 0, 0, 0));

        for (Category category : db.getCategories()) {
            RadioButton radioButton = new RadioButton(category.getName());
            radioButton.setToggleGroup(categoryGroup);
            categoryBox.getChildren().add(radioButton);

            // Pre-select category if editing
            if (product != null && product.getCategory().equals(category)) {
                radioButton.setSelected(true);
            }
        }

        gridPane.add(categoryLabel, 0, 4);
        gridPane.add(categoryBox, 1, 4);

        // Pre-fill fields if editing an existing product
        if (product != null) {
            nameField.setText(product.getName());
            priceField.setText(String.valueOf(product.getPrice()));
            quantityField.setText(String.valueOf(product.getStockQuantity()));
            descriptionArea.setText(product.getDescription());
        }

        // --- Submit Button ---
        Button submitButton = new Button(product == null ? "Add Product" : "Update Product");
        gridPane.add(submitButton, 1, 5);

        // --- Action Handling ---
        submitButton.setOnAction(e -> {
            // Retrieve input data
            String name = nameField.getText();
            String price = priceField.getText();
            String quantity = quantityField.getText();
            String description = descriptionArea.getText();
            RadioButton selectedCategoryButton = (RadioButton) categoryGroup.getSelectedToggle();
            String selectedCategory = selectedCategoryButton != null ? selectedCategoryButton.getText() : null;

            // Simple validation
            if (name.isEmpty() || price.isEmpty() || quantity.isEmpty() || description.isEmpty() || selectedCategory == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input!");
                alert.setContentText("Please fill in all the fields and select a category.");
                alert.showAndWait();
            } else {
                if (product == null) {
                    // Add new product
                    System.out.println("New Product Added:");
                } else {
                    // Update existing product
                    product.setName(name);
                    product.setPrice(Double.parseDouble(price));
                    product.setStockQuantity(Integer.parseInt(quantity));
                    product.setDescription(description);
                    product.setCategory(db.findByName(selectedCategory, Category.class));

                    db.update(Product.class, product);
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    AddProductPage productPage = new AddProductPage(db, sc);
                    sc.addScene("addProduct", productPage.getScene(), "Add Product");
                    ProductPage page = new ProductPage(db, sc);
                    sc.addScene("products", page.getScene(), "Products");

                    alert.setTitle("Product Edited Successfully!!!");
                    alert.showAndWait();
                    sc.switchToScene("products");

                }

//                System.out.println("Name: " + name);
//                System.out.println("Price: " + price);
//                System.out.println("Quantity: " + quantity);
//                System.out.println("Description: " + description);
//                System.out.println("Category: " + selectedCategory);
            }
        });

        // --- Scene and Stage ---
        VBox root = new VBox(10); // Root layout
        root.getChildren().addAll(gridPane); // Add gridPane to VBox
        root.setPadding(new Insets(10));

        s = new Scene(root, 700, 400); // Adjusted size for category selection
        setScene(s);
    }
}
