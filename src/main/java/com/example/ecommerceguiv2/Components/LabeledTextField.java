package com.example.ecommerceguiv2.Components;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class LabeledTextField extends HBox {
    private final Label label;
    private final TextField textField;


    public LabeledTextField(String labelText) {
        this(labelText, false);
    }

    public LabeledTextField(String labelText, boolean isPasswordField) {
        label = new Label(labelText);
        if (isPasswordField) {
            textField = new PasswordField();
        } else {
            textField = new TextField();
        }
        this.getChildren().addAll(label, textField);
    }

    public String getText() {
        return textField.getText();
    }

    public void clear() {
        textField.clear();
    }

    public TextField getTextField() {
        return textField;
    }

    public Label getLabel() {
        return label;
    }
}
