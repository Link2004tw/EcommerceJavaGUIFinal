package com.example.ecommerceguiv2.Components;

import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Product;
import com.example.ecommerceguiv2.Scenes.ProductDetailsPage;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class ProductItem extends HBox {

    public ProductItem(Product p, Database db, SceneController sc){

        Button labelButton = new Button(p.getName());
        labelButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-size: 14px; "
                + "-fx-padding: 0; -fx-border-color: transparent; -fx-cursor: default;");
        labelButton.setFocusTraversable(false);

        labelButton.setOnAction(e -> {
            ProductDetailsPage p1 = new ProductDetailsPage(p, db);
            sc.addScene("details", p1.getScene(), p.getName());
            sc.switchToScene("details");
        });
        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; "
                + "-fx-padding: 5px 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        addToCartButton.setOnAction(e -> {
            db.getLoggedCustomer().addToCart(p, 1, db);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(p.getName() + " added to cart!");
            alert.showAndWait();

        });
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Add components to HBox
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.getChildren().addAll(labelButton, spacer, addToCartButton);

    }
}