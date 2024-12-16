package com.example.ecommerceguiv2.Models;

import com.example.ecommerceguiv2.Scenes.ScenePage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class SceneContainer {
    private String name;
    private ScenePage scene;
    private String title;

    public SceneContainer(String n, ScenePage s, String t){
        name = n;
        scene = s;
        title = t;
    }
//    public SceneContainer(String n, Pane p, String t){
//        name = n;
//        scene = new Scene(p);
//        title = t;
//    }

    public Scene getScene() {
        return scene.getScene();
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public void setScene(Scene scene) {
        this.scene.setScene(scene);
    }
    public void setScene(ScenePage scene) {
        this.scene= scene;
    }
    public void refresh(){
        scene.refresh();
    }

}
