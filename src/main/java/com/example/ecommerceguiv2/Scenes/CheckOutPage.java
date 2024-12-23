package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Cart;
import com.example.ecommerceguiv2.Models.Card;
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
import javafx.stage.Stage;

public class CheckOutPage extends ScenePage {
    private Database database;
    private SceneController sceneController;
    private Cart cart;
    private ToggleGroup paymentGroup;
    private Label totalLabel;

    public CheckOutPage(Database db, SceneController sc) {
        this.database = db;
        this.sceneController = sc;
        Customer customer = db.getLoggedCustomer();
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
                if (method == Order.PaymentMethod.CREDIT_CARD) {
                    radioButton.setOnAction(e -> showCreditCardOptions());
                    //paymentGroup.selectToggle(radioButton);
                    ((RadioButton) paymentOptions.getChildren().getFirst()).setSelected(true);
                }
            }

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

    private void showCreditCardOptions() {
        Alert cardOptionAlert = new Alert(Alert.AlertType.CONFIRMATION);
        cardOptionAlert.setTitle("Credit Card Options");
        cardOptionAlert.setHeaderText("Choose an Option");
        cardOptionAlert.setContentText("Do you want to use an existing card or add a new one?");
        cardOptionAlert.getButtonTypes().setAll(ButtonType.OK);
        ButtonType existingCardButton = new ButtonType("Existing Card");
        ButtonType newCardButton = new ButtonType("Add New Card");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        cardOptionAlert.getButtonTypes().setAll(existingCardButton, newCardButton, cancelButton);

        cardOptionAlert.showAndWait().ifPresent(response -> {
            if (response == existingCardButton) {
                showExistingCardOptions();
            } else if (response == newCardButton) {
                sceneController.switchToScene("addCard");
            }
        });
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
            }
        }
        customer.makeOrder(database, selectedPaymentMethod);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Order processed successfully!", ButtonType.OK);
        alert.showAndWait();

        sceneController.switchToScene("dashboard");
    }

    @Override
    public void refresh() {
        Customer customer = database.getLoggedCustomer();
        cart = customer.getCart();
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
                if (method == Order.PaymentMethod.CREDIT_CARD) {
                    radioButton.setOnAction(e -> showCreditCardOptions());
                }
                if (method == Order.PaymentMethod.BALANCE) {
                    radioButton.setSelected(true);
                }
            }

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
        } else {
            System.out.println("cart empty");
        }
    }

    private void showExistingCardOptions() {
        VBox existingCardBox = new VBox(10);
        existingCardBox.setPadding(new Insets(10));

        Label cardListLabel = new Label("Select a Credit Card:");
        existingCardBox.getChildren().add(cardListLabel);

        // Label to display the selected card
        Label selectedCardLabel = new Label("Selected Card: None");
        existingCardBox.getChildren().add(selectedCardLabel);

        if (database.getLoggedCustomer().getCards().isEmpty()) {
            Label noCardsLabel = new Label("No cards available. Please add a new card.");
            existingCardBox.getChildren().add(noCardsLabel);
            Button addCardButton = new Button("Add New Card");
            addCardButton.setOnAction(e -> {
                sceneController.switchToScene("addCard");

                System.out.println("Card");
                for (Card card : database.getLoggedCustomer().getCards()) {
                    System.out.println(card.toString());
                }
                // After adding the card, show the credit card options again
                //showExistingCardOptions();
                Stage cardStage = (Stage) addCardButton.getScene().getWindow();

                cardStage.close();

            });

            existingCardBox.getChildren().add(addCardButton);
        } else {
            ListView<String> cardListView = new ListView<>(FXCollections.observableArrayList(
                    database.getLoggedCustomer().getCards().stream().map(Card::toString).toList()
            ));
            existingCardBox.getChildren().add(cardListView);

            Button selectCardButton = new Button("Select Card");
            selectCardButton.setOnAction(e -> {
                String selectedCard = cardListView.getSelectionModel().getSelectedItem();
                if (selectedCard != null) {
                    // Update the selected card label with the selected card's details
                    selectedCardLabel.setText("Selected Card: " + selectedCard);
                    // Close the window after selecting the card
                    Stage cardStage = (Stage) selectCardButton.getScene().getWindow();
                    cardStage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a card.", ButtonType.OK);
                    alert.showAndWait();
                }
            });
            existingCardBox.getChildren().add(selectCardButton);
        }

        BorderPane cardRoot = new BorderPane();
        cardRoot.setCenter(existingCardBox);

        Scene cardScene = new Scene(cardRoot, 400, 300);
        Stage cardStage = new Stage();
        cardStage.setScene(cardScene);
        cardStage.setTitle("Existing Credit Cards");
        cardStage.show();
    }


}