package com.example.ecommerceguiv2.Scenes;

import javafx.scene.Scene;

public abstract class ScenePage {
    private Scene scene;


    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public Scene getScene() {
        return scene;
    }
}
