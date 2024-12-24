package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.NavigationBar;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Cart;
import com.example.ecommerceguiv2.Models.Category;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Product;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;

public class ProductDetailsPage extends ScenePage {

    public ProductDetailsPage(Product product, Database db, SceneController sc) {
        NavigationBar navigationBar = new NavigationBar(sc);
        // Create main layout
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));
        // Product details form
        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));
        mainLayout.setTop(navigationBar);
        // Product Name
        Label nameLabel = new Label("Product Name:");
        TextField nameField = new TextField(product.getName());
        nameField.setEditable(false); // Make the field non-editable


        // Product Price
        Label priceLabel = new Label("Price:");
        TextField priceField = new TextField(String.valueOf(product.getPrice()));
        priceField.setEditable(false); // Make the field non-editable
        // Category
        Label categoryLabel = new Label("Category:");
        TextField categoryField = new TextField(product.getCategory().getName());
        categoryField.setEditable(false); // Make the field non-editable


        // Product Description
        Label descriptionLabel = new Label("Description:");
        TextArea descriptionArea = new TextArea(product.getDescription());
        descriptionArea.setPrefRowCount(3);
        descriptionArea.setEditable(false); // Make the field non-editable
        Button b1 = new Button("Add to cart");
        b1.setOnAction(e -> {
            db.getLoggedCustomer().addToCart(product, 1, db);
        });
        // Add fields to form pane
        formPane.add(nameLabel, 0, 0);
        formPane.add(nameField, 1, 0);
        formPane.add(priceLabel, 0, 1);
        formPane.add(priceField, 1, 1);
        formPane.add(categoryLabel, 0, 2);
        formPane.add(categoryField, 1, 2);
        formPane.add(descriptionLabel, 0, 3);
        formPane.add(descriptionArea, 1, 3);

        mainLayout.setCenter(formPane);

        // Action buttons (only Cancel to close the page)
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setSpacing(10);
        buttonBox.setStyle("-fx-alignment: center-right;");
        if (db.isAdmin()) {
            //System.out.println("is admin");
            Button b2 = new Button("Edit");
            b2.setOnAction(e -> {
                        AddProductPage productPage = new AddProductPage(db, product, sc);
                        sc.addScene("addProduct", productPage, product.getName());
                        sc.switchToScene("addProduct");
                    }
            );
            buttonBox.getChildren().add(b2);
            Button b3 = new Button("Remove");
            b3.setOnAction(e -> {
                        db.delete(Product.class, product);
                        sc.goBack();
                    }
            );
            buttonBox.getChildren().add(b3);
        }
        mainLayout.setBottom(buttonBox);
        // Create and set the scene
        Scene s = new Scene(mainLayout, 600, 500);
        setScene(s); // Use the ScenePage's setScene method
    }

    @Override
    public void refresh() {

    }
}
