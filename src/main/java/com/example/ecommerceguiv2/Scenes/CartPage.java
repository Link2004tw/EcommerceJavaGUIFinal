package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.NavigationBar;
import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableCell;
import javafx.collections.FXCollections;
import javafx.scene.control.ScrollPane;


public class CartPage extends ScenePage {

    Database database;
    SceneController sceneController;

    public CartPage(Database db, SceneController sc) {
        database = db;
        sceneController = sc;
        Customer customer = db.getLoggedCustomer();
        NavigationBar navigationBar = new NavigationBar(sceneController);

        if (customer != null) {
            Cart cart = customer.getCart();
            if (!cart.isEmpty()) {
                TableView<Item> table = new TableView<>();

                TableColumn<Item, String> productNameColumn = new TableColumn<>("Name");
                productNameColumn.setCellValueFactory(data ->
                        new javafx.beans.property.SimpleStringProperty(data.getValue().getProduct().getName()));

                TableColumn<Item, Double> productPriceColumn = new TableColumn<>("Price");
                productPriceColumn.setCellValueFactory(data ->
                        new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getProduct().getPrice()));

                TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
                quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

                productNameColumn.setMinWidth(150);
                productPriceColumn.setMinWidth(200);
                quantityColumn.setMinWidth(100);
                table.getColumns().addAll(productNameColumn, productPriceColumn, quantityColumn);

                TableColumn<Item, Void> removeColumn = new TableColumn<>("Remove");
                removeColumn.setCellFactory(param -> new TableCell<Item, Void>() {
                    private final Button removeButton = new Button("Remove");

                    {
                        removeButton.setOnAction(event -> {
                            Item item = getTableView().getItems().get(getIndex());
                            cart.removeProduct(item.getProduct());
                            sceneController.switchToScene("cart");
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : removeButton);
                    }
                });
                removeColumn.setMinWidth(100);
                table.getColumns().add(removeColumn);

                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setItems(FXCollections.observableArrayList(cart.getProducts()));
                table.getSelectionModel().clearSelection();

                // Wrap the TableView in a ScrollPane
                ScrollPane scrollPane = new ScrollPane(table);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);

                Label titleLabel = new Label("My Cart");

                Button placeOrderButton = new Button("Place Order");
                placeOrderButton.setOnAction(e -> {
                    table.setItems(FXCollections.observableArrayList(cart.getProducts()));
                    sc.switchToScene("order");
                });

                Button clearCartButton = new Button("Clear Cart");
                clearCartButton.setOnAction(e -> {
                    cart.clearCart();
                    sceneController.switchToScene("cart");
                });

                Button continueShoppingButton = new Button("Continue Shopping");
                continueShoppingButton.setOnAction(e -> {
                    sceneController.switchToScene("products");
                });

                HBox buttonBox = new HBox(10, placeOrderButton, clearCartButton, continueShoppingButton);
                buttonBox.setStyle("-fx-alignment: center;");

                VBox vbox = new VBox(10, titleLabel, scrollPane, buttonBox);
                vbox.setStyle("-fx-alignment: center;");

                // Bind font sizes dynamically
                vbox.scaleXProperty().bind(scrollPane.widthProperty().divide(800)); // Base width
                vbox.scaleYProperty().bind(scrollPane.heightProperty().divide(600)); // Base height

                Scene s = new Scene(vbox, 600, 400);
                try {
                    s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/cart-styles.css").toExternalForm());
                } catch (NullPointerException e) {
                    System.out.println("Error");
                }
                setScene(s);

            } else {
                // When the cart is empty
                Label titleLabel = new Label("My Cart");

                Label emptyCartLabel = new Label("Your cart is currently empty.");

                Button continueShoppingButton = new Button("Continue Shopping");
                continueShoppingButton.setOnAction(e -> {
                    sc.switchToScene("products");
                });

                VBox vbox = new VBox(20, navigationBar, titleLabel, emptyCartLabel, continueShoppingButton);
                vbox.setStyle("-fx-alignment: center; -fx-padding: 20px;");

                // Bind font sizes dynamically
                vbox.scaleXProperty().bind(vbox.widthProperty().divide(800)); // Base width
                vbox.scaleYProperty().bind(vbox.heightProperty().divide(600)); // Base height

                Scene s = new Scene(vbox, 600, 400);
                try {
                    s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/cart-styles.css").toExternalForm());
                } catch (NullPointerException e) {
                    System.out.println("Error");
                }
                setScene(s);
            }
        }
    }


    @Override
    public void refresh() {
        Customer customer = database.getLoggedCustomer();

        if (customer != null) {
            Cart cart = customer.getCart();
            if (!cart.isEmpty()) {
                TableView<Item> table = new TableView<>();

                TableColumn<Item, String> productNameColumn = new TableColumn<>("Name");
                productNameColumn.setCellValueFactory(data ->
                        new javafx.beans.property.SimpleStringProperty(data.getValue().getProduct().getName()));

                TableColumn<Item, Double> productPriceColumn = new TableColumn<>("Price");
                productPriceColumn.setCellValueFactory(data ->
                        new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getProduct().getPrice()));

                TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
                quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

                productNameColumn.setMinWidth(150);
                productPriceColumn.setMinWidth(200);
                quantityColumn.setMinWidth(100);
                table.getColumns().addAll(productNameColumn, productPriceColumn, quantityColumn);

                TableColumn<Item, Void> removeColumn = new TableColumn<>("Remove");
                removeColumn.setCellFactory(param -> new TableCell<Item, Void>() {
                    private final Button removeButton = new Button("Remove");

                    {
                        removeButton.setOnAction(event -> {
                            Item item = getTableView().getItems().get(getIndex());
                            cart.removeProduct(item.getProduct());
                            sceneController.switchToScene("cart");
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : removeButton);
                    }
                });
                removeColumn.setMinWidth(100);
                table.getColumns().add(removeColumn);

                table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
                table.setItems(FXCollections.observableArrayList(cart.getProducts()));
                table.getSelectionModel().clearSelection();

                // Wrap TableView in ScrollPane
                ScrollPane scrollPane = new ScrollPane(table);
                scrollPane.setFitToWidth(true);
                scrollPane.setFitToHeight(true);

                Label titleLabel = new Label("My Cart");
                titleLabel.getStyleClass().add("title");

                Button placeOrderButton = new Button("Place Order");
                placeOrderButton.setOnAction(e -> {
                    table.setItems(FXCollections.observableArrayList(cart.getProducts()));
                    sceneController.switchToScene("order");
                });

                Button clearCartButton = new Button("Clear Cart");
                clearCartButton.setOnAction(e -> {
                    cart.clearCart();
                    sceneController.switchToScene("cart");
                });

                Button continueShoppingButton = new Button("Continue Shopping");
                continueShoppingButton.setOnAction(e -> {
                    sceneController.switchToScene("products");
                });

                // Footer for buttons
                HBox footerBox = new HBox(10, placeOrderButton, clearCartButton, continueShoppingButton);
                footerBox.setStyle("-fx-alignment: center; -fx-padding: 10px;");

                VBox mainBox = new VBox(10, titleLabel, scrollPane);
                mainBox.setStyle("-fx-alignment: center; -fx-padding: 20px;");

                BorderPane layout = new BorderPane();
                layout.setCenter(mainBox);
                layout.setBottom(footerBox);

                Scene s = new Scene(layout, 600, 400);
                try {
                    s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/cart-styles.css").toExternalForm());
                } catch (NullPointerException e) {
                    System.out.println("Error");
                }
                setScene(s);

            } else {
                // When the cart is empty
                Label titleLabel = new Label("My Cart");
                titleLabel.getStyleClass().add("title");

                Label emptyCartLabel = new Label("Your cart is currently empty.");
                emptyCartLabel.getStyleClass().add("message");

                Button continueShoppingButton = new Button("Continue Shopping");
                continueShoppingButton.setOnAction(e -> {
                    sceneController.switchToScene("products");
                });

                VBox centerBox = new VBox(20, titleLabel, emptyCartLabel, continueShoppingButton);
                centerBox.setStyle("-fx-alignment: center; -fx-padding: 20px;");

                BorderPane layout = new BorderPane();
                layout.setCenter(centerBox);

                Scene s = new Scene(layout, 600, 400);
                try {
                    s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/cart-styles.css").toExternalForm());
                } catch (NullPointerException e) {
                    System.out.println("Error");
                }
                setScene(s);
            }
        }
    }
}
