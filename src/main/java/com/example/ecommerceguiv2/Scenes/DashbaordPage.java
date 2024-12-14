package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.SceneController;
import javafx.scene.control.Button;

public class DashbaordPage extends ScenePage {
    public DashbaordPage(SceneController sc){

        Button b1 = new Button("Go To Cart");
        b1.setOnAction(e -> {
            sc.switchToScene("cart");
        });
    }
}
