package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Category;
import com.example.ecommerceguiv2.Models.Database;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CategoriesPage extends ScenePage {


    public CategoriesPage(Database db, SceneController sc) {

    }
    private void createPage(Database db, SceneController sc){
        VBox mainLayout; // Main layout for the scene

        mainLayout = new VBox(10); // Create a VBox layout with spacing
        ListView<HBox> categoryList = new ListView<>(); // Create a ListView for categories
        for (Category category : db.getCategories()) {
            HBox categoryRow = createCategoryRow(category,sc, db); // Create a row for each category
            categoryList.getItems().add(categoryRow); // Add it to the ListView
        }
        mainLayout.getChildren().add(categoryList); // Add the ListView to the layout
        Scene s = new Scene(mainLayout, 600, 400); // Create a scene with the layout

        // Call setScene and pass the created scene
        setScene(s);
    }
    private HBox createCategoryRow(Category category, SceneController sc,Database db) {
        HBox row = new HBox(10); // Create a horizontal row with spacing

        Label nameLabel = new Label(category.getName()); // Display category name
        Button detailsButton = new Button("View Details"); // Button to view details
        Button editButton = new Button("Edit"); // Button to edit category

        detailsButton.setOnAction(event -> {
            CategoryDetails categoryDetails = new CategoryDetails(category);
            sc.addScene("categoryDetails", categoryDetails, category.getName());
            sc.switchToScene("categoryDetails");
        });

        // Add the components to the row
        row.getChildren().addAll(nameLabel, detailsButton, editButton);
        return row;
    }

    @Override
    public void refresh() {
    }


}
