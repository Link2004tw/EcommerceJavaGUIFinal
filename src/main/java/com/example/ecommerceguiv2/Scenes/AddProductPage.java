package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.NavigationBar;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Category;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Product;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AddProductPage extends ScenePage {
    private Database database;
    private SceneController sceneController;
    private Product product;

    public AddProductPage(Database db, SceneController sc) {
        this(db, null, sc);
    }

    public AddProductPage(Database db, Product product, SceneController sc) {
        this.database = db;
        this.sceneController = sc;
        this.product = product;

        NavigationBar navigationBar = new NavigationBar(sceneController);
        VBox root = new VBox(10);
        root.getChildren().addAll(navigationBar, createProductForm());
        root.setPadding(new Insets(10));

        setScene(new Scene(root, 700, 400));
    }

    private GridPane createProductForm() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Form Fields
        TextField nameField = createTextField(gridPane, "Product Name:", 0, product != null ? product.getName() : "");
        TextField priceField = createTextField(gridPane, "Product Price:", 1, product != null ? String.valueOf(product.getPrice()) : "");
        TextField quantityField = createTextField(gridPane, "Product Quantity:", 2, product != null ? String.valueOf(product.getStockQuantity()) : "");
        TextArea descriptionArea = createTextArea(gridPane, "Product Description:", 3, product != null ? product.getDescription() : "");

        // Category Selection
        // Product Category (Radio Buttons)
        Label categoryLabel = new Label("Category:");
        ToggleGroup categoryGroup = new ToggleGroup();
        VBox categoryBox = new VBox(5); // Layout for the radio buttons
        categoryBox.setPadding(new Insets(10, 0, 0, 0));

// Existing categories
        for (Category category : database.getCategories()) {
            RadioButton radioButton = new RadioButton(category.getName());
            radioButton.setToggleGroup(categoryGroup);
            categoryBox.getChildren().add(radioButton);

            // Pre-select category if editing
            if (product != null && product.getCategory().equals(category)) {
                radioButton.setSelected(true);
            }
        }
        // New Category Input
        // TextField newCategoryField = new TextField();
        // newCategoryField.setPromptText("Enter new category name");
        // TextArea newCategoryDescription = createTextArea(gridPane, "Category Description: ", 5, "");
        // Button addCategoryButton = new Button("Add Category");

        // addCategoryButton.setOnAction(e -> {
        // String newCategoryName = newCategoryField.getText().trim();
        // if (!newCategoryName.isEmpty() && !newCategoryDescription.getText().isEmpty()) {
        // Add new category dynamically
        // Category newCategory = new Category(newCategoryName, newCategoryDescription.getText()); // Assuming Category class constructor takes name
        // database.addCategory(newCategory); // Add to database
        // RadioButton newRadioButton = new RadioButton(newCategory.getName());
        // newRadioButton.setToggleGroup(categoryGroup);
        // categoryBox.getChildren().add(newRadioButton);
        // newCategoryField.clear(); // Clear the input field
        // newCategoryDescription.clear();
        // } else {
        // Validation for empty input
        // Alert alert = new Alert(Alert.AlertType.ERROR);
        //  alert.setTitle("Invalid Category");
        //alert.setContentText("Category name and description cannot be empty.");
        //alert.showAndWait();
        //}
        // });

// Layout for Category Section
        VBox categorySection = new VBox(5);
        categorySection.getChildren().addAll(categoryBox);

        gridPane.add(categoryLabel, 0, 4);
        gridPane.add(categorySection, 1, 4);
        //gridPane.add(addCategoryButton, 0, 8);
        // Submit Button
        Button submitButton = new Button(product == null ? "Add Product" : "Update Product");
        submitButton.setOnAction(e -> handleFormSubmission(nameField, priceField, quantityField, descriptionArea, categoryGroup));
        gridPane.add(submitButton, 1, 9);

        return gridPane;
    }

    private TextField createTextField(GridPane grid, String label, int row, String prefill) {
        Label fieldLabel = new Label(label);
        TextField textField = new TextField(prefill);
        grid.add(fieldLabel, 0, row);
        grid.add(textField, 1, row);
        return textField;
    }

    private TextArea createTextArea(GridPane grid, String label, int row, String prefill) {
        Label fieldLabel = new Label(label);
        TextArea textArea = new TextArea(prefill);
        textArea.setPrefRowCount(4);
        textArea.setWrapText(true);
        grid.add(fieldLabel, 0, row);
        grid.add(textArea, 1, row);
        return textArea;
    }

    private void handleFormSubmission(TextField nameField, TextField priceField, TextField quantityField, TextArea descriptionArea, ToggleGroup categoryGroup) {
        String name = nameField.getText();
        String price = priceField.getText();
        String quantity = quantityField.getText();
        String description = descriptionArea.getText();
        RadioButton selectedCategoryButton = (RadioButton) categoryGroup.getSelectedToggle();
        String selectedCategory = selectedCategoryButton != null ? selectedCategoryButton.getText() : null;

        if (name.isEmpty() || price.isEmpty() || quantity.isEmpty() || description.isEmpty() || selectedCategory == null) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input!", "Please fill in all the fields and select a category.");
        } else {
            Category category = database.findByName(selectedCategory, Category.class);
            if (product == null) {
                // Add New Product
                database.addProduct(new Product(name, description, Double.parseDouble(price), Integer.parseInt(quantity), category));
                showAlert(Alert.AlertType.CONFIRMATION, "Success", "Product Added Successfully!");
            } else {
                // Update Existing Product
                product.setName(name);
                product.setPrice(Double.parseDouble(price));
                product.setStockQuantity(Integer.parseInt(quantity));
                product.setDescription(description);
                product.setCategory(category);
                database.update(Product.class, product);
                showAlert(Alert.AlertType.CONFIRMATION, "Success", "Product Updated Successfully!");
            }
            sceneController.switchToScene("products");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void refresh() {
        NavigationBar navigationBar = new NavigationBar(sceneController);
        VBox root = new VBox(10);
        root.getChildren().addAll(navigationBar, createProductForm());
        root.setPadding(new Insets(10));

        setScene(new Scene(root, 700, 500));
    }
}
