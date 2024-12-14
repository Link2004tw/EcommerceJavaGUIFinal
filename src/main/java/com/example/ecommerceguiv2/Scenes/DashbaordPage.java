package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.Database;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class DashbaordPage extends ScenePage {
    public DashbaordPage(SceneController sc, Database db){
        Scene s;
        if(db.getLoggedCustomer() == null){
            System.out.println("Something is wrong");
        }
        Button b1 = new Button("Go To Cart");

        b1.setOnAction(e -> {
            sc.displayNames();
            sc.switchToScene("cart");
        });

        Pane p = new Pane(b1);
        s = new Scene(p, 400, 400);
        setScene(s);
    }
}
