package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Cart;
import com.example.ecommerceguiv2.Models.Customer;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Order;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CheckOutPage extends ScenePage {
    private Database database;
    private SceneController sceneController;
    private Cart cart;
    private ToggleGroup paymentGroup;
    private Label totalLabel;
    private Customer customer;

    public CheckOutPage(Database db, SceneController sc) {
        this.database = db;
        this.sceneController = sc;
        customer = db.getLoggedCustomer();
        if (customer != null) {
            cart = customer.getCart();

            Label titleLabel = new Label("Checkout Page");
            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            Label paymentMethodLabel = new Label("Select Payment Method:");
            paymentGroup = new ToggleGroup();

            VBox paymentOptions = new VBox(10);
            paymentOptions.setPadding(new Insets(10));
            for (Order.PaymentMethod method : Order.PaymentMethod.values()) {
                RadioButton radioButton = new RadioButton(method.toString().replace("_", " "));
                radioButton.setToggleGroup(paymentGroup);
                paymentOptions.getChildren().add(radioButton);
            }
            ((RadioButton) paymentOptions.getChildren().getFirst()).setSelected(true);

            Label orderSummaryLabel = new Label("Your Order:");

            ListView<String> orderSummaryList = new ListView<>();
            cart.getProducts().forEach(item ->
                    orderSummaryList.getItems().add(item.getProduct().getName() + " (x" + item.getQuantity() + ") - $" + (item.getProduct().getPrice() * item.getQuantity()))
            );

            double totalPrice = cart.getTotalAmount();
            totalLabel = new Label("Total: $" + String.format("%.2f", totalPrice));

            Button processOrderButton = new Button("Process Order");
            processOrderButton.setOnAction(e -> processTheOrder(customer));

            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction(e -> sceneController.switchToScene("cart"));

            HBox buttonBox = new HBox(10, processOrderButton, cancelButton);
            buttonBox.setPadding(new Insets(10));

            VBox content = new VBox(10, titleLabel, paymentMethodLabel, paymentOptions, orderSummaryLabel, orderSummaryList, totalLabel, buttonBox);
            content.setPadding(new Insets(20));

            BorderPane root = new BorderPane();
            root.setCenter(content);

            Scene scene = new Scene(root, 800, 600);
            setScene(scene);
        }
    }

    private void processTheOrder(Customer customer) {
        RadioButton selectedButton = (RadioButton) paymentGroup.getSelectedToggle();
        if (selectedButton == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a payment method.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        String selectedMethodText = selectedButton.getText().replace(" ", "_").toUpperCase();
        Order.PaymentMethod selectedPaymentMethod = Order.PaymentMethod.valueOf(selectedMethodText);

        if (selectedPaymentMethod == Order.PaymentMethod.BALANCE) {
            if (customer.getBalance() < cart.getTotalAmount()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Insufficient balance. Defaulting to CASH.", ButtonType.OK);
                alert.showAndWait();
                selectedPaymentMethod = Order.PaymentMethod.CASH;
            } else {
                customer.setBalance(customer.getBalance() - cart.getTotalAmount());
                database.update(Customer.class, customer); // Update balance in the database
            }
        }
        customer.makeOrder(database,selectedPaymentMethod);


        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order processed successfully!", ButtonType.OK);
        alert.showAndWait();

        sceneController.switchToScene("ProductPage");
    }

    @Override
    public void refresh() {
        if (cart != null) {
            Label titleLabel = new Label("Checkout Page");
            titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            Label paymentMethodLabel = new Label("Select Payment Method:");
            paymentGroup = new ToggleGroup();

            VBox paymentOptions = new VBox(10);
            paymentOptions.setPadding(new Insets(10));
            for (Order.PaymentMethod method : Order.PaymentMethod.values()) {
                RadioButton radioButton = new RadioButton(method.toString().replace("_", " "));
                radioButton.setToggleGroup(paymentGroup);
                paymentOptions.getChildren().add(radioButton);
            }
            ((RadioButton) paymentOptions.getChildren().getFirst()).setSelected(true);

            Label orderSummaryLabel = new Label("Your Order:");

            ListView<String> orderSummaryList = new ListView<>();
            cart.getProducts().forEach(item ->
                    orderSummaryList.getItems().add(item.getProduct().getName() + " (x" + item.getQuantity() + ") - $" + (item.getProduct().getPrice() * item.getQuantity()))
            );

            double totalPrice = cart.getTotalAmount();
            totalLabel = new Label("Total: $" + String.format("%.2f", totalPrice));

            Button processOrderButton = new Button("Process Order");
            processOrderButton.setOnAction(e -> processTheOrder(customer));

            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction(e -> sceneController.switchToScene("cart"));

            HBox buttonBox = new HBox(10, processOrderButton, cancelButton);
            buttonBox.setPadding(new Insets(10));

            VBox content = new VBox(10, titleLabel, paymentMethodLabel, paymentOptions, orderSummaryLabel, orderSummaryList, totalLabel, buttonBox);
            content.setPadding(new Insets(20));

            BorderPane root = new BorderPane();
            root.setCenter(content);

            Scene scene = new Scene(root, 800, 600);
            setScene(scene);
        }
    }
}

