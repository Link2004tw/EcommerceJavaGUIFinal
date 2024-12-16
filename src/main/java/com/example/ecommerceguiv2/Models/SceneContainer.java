package com.example.ecommerceguiv2.Models;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class SceneContainer {
    private String name;
    private Scene scene;
    private String title;

    public SceneContainer(String n, Scene s, String t){
        name = n;
        scene = s;
        title = t;
    }
    public SceneContainer(String n, Pane p, String t){
        name = n;
        scene = new Scene(p);
        title = t;
    }

    public Scene getScene() {
        return scene;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
