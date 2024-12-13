package com.example.ecommerceguiv2;

import Scenes.LoginScene;
import Scenes.RegisterScene;
import com.example.ecommerceguiv2.Exceptions.NotFoundException;
import com.example.ecommerceguiv2.Models.Customer;
import com.example.ecommerceguiv2.Models.Database;
import com.example.ecommerceguiv2.Models.Person;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException, NotFoundException {

        SceneController sc = new SceneController(primaryStage);
        Database db = new Database();
        Date now = new Date();

        Customer customer = new Customer("Mark", "12345", now, 4000, Person.Gender.MALE);
        db.addCustomer(customer);
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));



        LoginScene loginScene = new LoginScene(db, sc);
        RegisterScene registerScene = new RegisterScene(db, sc);

        primaryStage.setTitle("Custom Labeled TextField Example");
        sc.addScene("login", loginScene.getScene());
        sc.addScene("register", registerScene.getScene());
        sc.switchToScene("login");
    }

    public static void main(String[] args) {
        launch();
    }
}