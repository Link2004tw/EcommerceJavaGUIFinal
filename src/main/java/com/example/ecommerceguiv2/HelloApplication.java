package com.example.ecommerceguiv2;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Scenes.*;
import com.example.ecommerceguiv2.Exceptions.NotFoundException;
import com.example.ecommerceguiv2.Models.Customer;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Person;
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

        Customer customer = new Customer("Mark", "12345", now, 4000, Person.Gender.MALE);
        db.addCustomer(customer);
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        LoginScene loginScene = new LoginScene(db, sc);
        RegisterScene registerScene = new RegisterScene(db, sc);
        ProductPage productPage = new ProductPage(db);


        sc.addScene("login", loginScene.getScene(), "Login Page");
        sc.addScene("register", registerScene.getScene(), "Registeration Page");
        sc.addScene("products", productPage.getScene(), "Product Page");
        sc.displayNames();
        sc.switchToScene("login");
    }

    public static void main(String[] args) {
        launch();
    }
}