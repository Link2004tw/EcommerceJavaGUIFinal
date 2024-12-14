package com.example.ecommerceguiv2.Components;
import com.example.ecommerceguiv2.Exceptions.AlreadyExistsException;
import com.example.ecommerceguiv2.Models.SceneContainer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SceneController {
    private final Stage stage;
    private List<SceneContainer> scenes = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    public SceneController(Stage stage) {
        this.stage = stage;
    }

    public void addScene(String name, Pane layout, String title) {
        for (String n: names){
            if(n.equals(name)){
                throw new AlreadyExistsException("Scene name already exists");
            }
        }
        names.add(name);
        SceneContainer sceneContainer = new SceneContainer(name, layout,title);
        scenes.add(sceneContainer);
    }

    public void addScene(String name, Scene s, String t) {
        for (SceneContainer n: scenes){
            if(n.getName().equals(name)){
                n.setScene(s);
            }
        }
        names.add(name);
        SceneContainer sceneContainer = new SceneContainer(name, s,t);
        scenes.add(sceneContainer);
    }
    // Switch to a specific scene by name
    public void switchToScene(String name) {
        Scene s = null;
        String t = null;
        for(SceneContainer sceneContainer: scenes){
            if(sceneContainer.getName().equals(name)){
                s = sceneContainer.getScene();
                t = sceneContainer.getTitle();
            }
        }
        if (s != null) {
            stage.setScene(s);
            stage.setTitle(t);
            stage.show();
        } else {
            System.out.println("Scene " + name + " not found!");
        }
    }
    public void displayNames() {
        for(String n: names){
            System.out.println(n);
        }
    }
}