package com.example.ecommerceguiv2;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LabeledTextField extends HBox {
    private Label label;
    private TextField textField;

    public LabeledTextField(String labelText) {
        // Create the label and text field
        label = new Label(labelText);
        textField = new TextField();

        // Customize the label and text field (you can also use CSS styling)
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        textField.setStyle("-fx-padding: 5px;");

        // Add the label and text field to the layout
        this.getChildren().addAll(label, textField);

        // Layout spacing and padding
        this.setSpacing(10);  // Add space between label and text field
        this.setPadding(new Insets(5));  // Padding around the entire container
    }
    public TextField getTextField() {
        return textField;
    }
    public String getText() {
        return textField.getText();
    }
    public void setText(String text) {
        textField.setText(text);
    }
    public Label getLabel() {
        return label;
    }
    public void setLabelText(String labelText) {
        label.setText(labelText);
    }
}
