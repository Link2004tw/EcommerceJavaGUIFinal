package com.example.ecommerceguiv2;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.*;
import com.example.ecommerceguiv2.Scenes.*;
import com.example.ecommerceguiv2.Exceptions.NotFoundException;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException, NotFoundException {

        SceneController sc = new SceneController(primaryStage);
        Database db = new Database(sc);
        Date now = new Date();

        Category electronics = new Category("Electronics", "Electric devices");
        Category clothing = new Category("Clothing", "Clothes and wearables");
        Category groceries = new Category("Groceries", "Food and stuff like that");
        Category[] categories = {electronics, clothing, groceries};

        for (Category c : categories) {
            db.addCategory(c);
        }

        Product[] products = new Product[]{
                // Electronics
                new Product("Laptop", "High-performance laptop", 999.99, 10, electronics),
                new Product("Smartphone", "Latest model smartphone", 799.99, 25, electronics),
                new Product("Headphones", "Noise-cancelling headphones", 199.99, 15, electronics),
                new Product("TV", "4K Ultra HD TV", 499.99, 8, electronics),
                new Product("Tablet", "10-inch display tablet", 299.99, 12, electronics),

                // Clothing
                new Product("T-shirt", "Cotton t-shirt", 19.99, 50, clothing),
                new Product("Jeans", "Comfortable denim jeans", 39.99, 40, clothing),
                new Product("Jacket", "Warm winter jacket", 79.99, 30, clothing),
                new Product("Sneakers", "Stylish sneakers", 59.99, 20, clothing),
                new Product("Shirt", "Formal shirt", 29.99, 25, clothing),

                // Groceries
                new Product("Bread", "Freshly baked bread", 2.99, 100, groceries),
                new Product("Milk", "1-liter milk carton", 1.49, 80, groceries),
                new Product("Cheese", "Cheddar cheese", 4.99, 60, groceries),
                new Product("Eggs", "12-pack eggs", 2.99, 75, groceries),
                new Product("Juice", "1-liter orange juice", 2.49, 50, groceries)
        };

        for (Product p : products) {
            db.addProduct(p);
        }

        Customer customer = new Customer("Mark", "12345", now, 4000, Person.Gender.MALE);
        Admin admin = new Admin("Admin", "12345", now, 4000, Person.Gender.MALE, "Manager", 40);

        db.addCustomer(customer);
        db.addAdmin(admin);
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        LoginScene loginScene = new LoginScene(db, sc);
        RegisterScene registerScene = new RegisterScene(db, sc);
        CheckOutPage checkOutPage = new CheckOutPage(db, sc);

        sc.addScene("login", loginScene, "Login Page");
        sc.addScene("register", registerScene, "Registeration Page");
        sc.addScene("order", checkOutPage, "Check Out");
        sc.displayNames();
        sc.switchToScene("login");
    }

    public static void main(String[] args) {
        launch();
    }
}