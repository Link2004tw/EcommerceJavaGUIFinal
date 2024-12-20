package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.NavigationBar;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Category;
import com.example.ecommerceguiv2.Models.Database;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class AddCategoryPage extends ScenePage{
    private Database database;
    private SceneController sceneController;
    private Category category;
    public AddCategoryPage(Database db, SceneController sc) {
        this(db, null, sc);
    }

    public AddCategoryPage(Database db, Category category, SceneController sc) {

        this.database = db;
        this.sceneController = sc;
        this.category = category;

        NavigationBar navigationBar = new NavigationBar(sceneController);
        VBox root = new VBox(10);
        root.getChildren().addAll(navigationBar, createCategoryForm());
        root.setPadding(new Insets(10));

        setScene(new Scene(root, 700, 400));

    }

    private GridPane createCategoryForm() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField nameField = createTextField(gridPane, "Category Name:", 0, category != null ? category.getName() : "");
        TextArea descriptionArea = createTextArea(gridPane, "Category Description:", 1, category != null ? category.getDescription() : "");
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String categoryName = nameField.getText().trim();
            String categoryDescription = descriptionArea.getText().trim();

            if (categoryName.isEmpty() || categoryDescription.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Category");
                alert.setContentText("Category name and description cannot be empty.");
                alert.showAndWait();
            } else {

            }


        });

        gridPane.add(submitButton, 1, 2); // Add the submit button to the form
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
    private void handleFormSubmission(TextField nameField, TextArea descriptionArea) {
        String name = nameField.getText();
        String description = descriptionArea.getText();

        if (name.isEmpty()  || description.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input!", "Please fill in all the fields.");
        } else {
            if (category == null) {
                database.addCategory(new Category(name,description));
                showAlert(Alert.AlertType.CONFIRMATION, "Success", "Category Added Successfully!");
            } else {
                category.setName(name);
                category.setDescription(description);
                database.update(Category.class, category);
                showAlert(Alert.AlertType.CONFIRMATION, "Success", "Category Updated Successfully!");
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
        root.getChildren().addAll(navigationBar, createCategoryForm());
        root.setPadding(new Insets(10));

        setScene(new Scene(root, 700, 400));
    }
}
