package com.example.ecommerceguiv2.Components;

import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Product;
import com.example.ecommerceguiv2.Scenes.AddProductPage;
import com.example.ecommerceguiv2.Scenes.ProductDetailsPage;
import com.example.ecommerceguiv2.Scenes.ScenePage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class ProductItem extends HBox {

    public ProductItem(Product p, Database db, SceneController sc) {

        // Button for product name
        Button labelButton = new Button(p.getName());
        labelButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-font-size: 14px; "
                + "-fx-padding: 0; -fx-border-color: transparent; -fx-cursor: default;");
        labelButton.setFocusTraversable(false);

        labelButton.setOnAction(e -> {
            ProductDetailsPage p1 = new ProductDetailsPage(p, db, sc);
            sc.addScene("details", p1, p.getName());
            sc.switchToScene("details");
        });

        // Button for Add to Cart or Edit
        Button addToCartButton = new Button(db.isAdmin() ? "Edit" : "Add to Cart");
        addToCartButton.setOnAction(e -> {
            if (db.isAdmin()) {
                AddProductPage productPage = new AddProductPage(db, p, sc);
                sc.addScene("addProduct", productPage, p.getName() + "Edit");
                sc.switchToScene("addProduct");
            } else {
                db.getLoggedCustomer().addToCart(p, 1, db);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(p.getName() + " added to cart!");
                alert.getButtonTypes().setAll(ButtonType.OK);

                alert.showAndWait();
            }
        });

        // Label for product price
        Label priceLabel = new Label(String.format("$%.2f", p.getPrice()));
        priceLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333; -fx-padding: 0 10px;");

        // Spacer for layout
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Add components to HBox
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.getChildren().addAll(labelButton, spacer, priceLabel, addToCartButton);
    }

    public static class SceneContainer {
        private String name;
        private ScenePage scene;
        private String title;

        public SceneContainer(String n, ScenePage s, String t){
            name = n;
            scene = s;
            title = t;
        }
    //    public SceneContainer(String n, Pane p, String t){
    //        name = n;
    //        scene = new Scene(p);
    //        title = t;
    //    }

        public Scene getScene() {
            return scene.getScene();
        }

        public String getName() {
            return name;
        }

        public String getTitle() {
            return title;
        }

        public void setScene(Scene scene) {
            this.scene.setScene(scene);
        }
        public void setScene(ScenePage scene) {
            this.scene= scene;
        }
        public void refresh(){
            scene.refresh();
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
