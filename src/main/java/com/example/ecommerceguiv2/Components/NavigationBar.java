package com.example.ecommerceguiv2.Components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class NavigationBar extends VBox {

    public NavigationBar(SceneController sceneController) {
        // Styling and layout settings
        this.setSpacing(10); // Space between elements
        this.setPadding(new Insets(10)); // Padding around the VBox
        this.setAlignment(Pos.CENTER_LEFT); // Align content to the left

        // Back button
        Button backButton = new Button("Back");
        backButton.setPrefWidth(100); // Set preferred width
        backButton.setOnAction(e -> {
            sceneController.goBack();
        }); // Trigger the back action when pressed

        // Add button to VBox
        this.getChildren().add(backButton);
    }
}
