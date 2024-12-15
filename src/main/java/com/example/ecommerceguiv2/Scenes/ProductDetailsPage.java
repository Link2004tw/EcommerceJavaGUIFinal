package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Models.Category;
import com.example.ecommerceguiv2.Models.Product;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import java.util.List;

public class ProductDetailsPage extends ScenePage {

    public ProductDetailsPage(Product product, List<Category> categoryList) {
        // Main layout
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));

        // Product details form
        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));

        // Product Name
        Label nameLabel = new Label("Product Name:");
        TextField nameField = new TextField(product.getName());
        nameField.setEditable(false);  // Make the field non-editable

        // Product ID
        Label idLabel = new Label("Product ID:");
        TextField idField = new TextField(String.valueOf(product.getId()));
        idField.setEditable(false);  // Make the field non-editable

        // Product Price
        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField(String.valueOf(product.getPrice()));
        priceField.setEditable(false);  // Make the field non-editable

        // Category
        Label categoryLabel = new Label("Category:");
        ComboBox<Category> categoryField = new ComboBox<>();
        categoryField.getItems().addAll(categoryList);
        categoryField.setValue(product.getCategory());  // Set the category of the product
        categoryField.setDisable(true);  // Disable the ComboBox so it's not editable

        // Product Description
        Label descriptionLabel = new Label("Description:");
        TextArea descriptionArea = new TextArea(product.getDescription());
        descriptionArea.setPrefRowCount(3);
        descriptionArea.setEditable(false);  // Make the field non-editable

        // Add fields to form pane
        formPane.add(nameLabel, 0, 0);
        formPane.add(nameField, 1, 0);
        formPane.add(idLabel, 0, 1);
        formPane.add(idField, 1, 1);
        formPane.add(priceLabel, 0, 2);
        formPane.add(priceField, 1, 2);
        formPane.add(categoryLabel, 0, 3);
        formPane.add(categoryField, 1, 3);
        formPane.add(descriptionLabel, 0, 4);
        formPane.add(descriptionArea, 1, 4);

        layout.setTop(formPane);

        // Action buttons (only Cancel to close the page)
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setSpacing(10);
        buttonBox.setStyle("-fx-alignment: center-right;");

//        Button cancelButton = new Button("Cancel");
//        cancelButton.setOnAction(e -> stage.close());  // Close the page when Cancel is clicked

        //buttonBox.getChildren().add(cancelButton);
        layout.setBottom(buttonBox);

        // Set the scene
        Scene s = new Scene(layout, 600, 500);
        setScene(s);
    }

}
