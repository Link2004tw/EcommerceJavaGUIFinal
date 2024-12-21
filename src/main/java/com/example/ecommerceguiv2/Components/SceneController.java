package com.example.ecommerceguiv2.Components;
import com.example.ecommerceguiv2.Exceptions.AlreadyExistsException;
import com.example.ecommerceguiv2.Models.SceneContainer;
import com.example.ecommerceguiv2.Scenes.ScenePage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SceneController {
    private final Stage stage;
    private List<SceneContainer> scenes = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private Stack<String> screneVisited = new Stack<>();
    public SceneController(Stage stage) {
        this.stage = stage;
    }



    public void addScene(String name, ScenePage s, String t) {
        for (SceneContainer n: scenes){
            if(n.getName().equals(name)){
                n.setScene(s);
            }
        }
        names.add(name);
        try {
            s.getScene().getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/main.css").toExternalForm());
        } catch (NullPointerException e) {
            System.out.println("Error");
        }
        SceneContainer sceneContainer = new SceneContainer(name, s,t);
        scenes.add(sceneContainer);
    }
    // Switch to a specific scene by name
    public void switchToScene(String name) {
        Scene s = null;
        String t = null;
        for(SceneContainer sceneContainer: scenes){
            if(sceneContainer.getName().equals(name)){
                refresh(name);
                s = sceneContainer.getScene();
                t = sceneContainer.getTitle();
                if (s != null) {
                    try {
                        s.getStylesheets().add(getClass().getResource("/com/example/ecommerceguiv2/main.css").toExternalForm());
                    } catch (NullPointerException e) {
                        System.out.println("Error applying stylesheet to scene: " + name);
                    }
                }
            }
        }
        if (s != null) {
            stage.setScene(s);
            stage.setTitle(t);
            screneVisited.push(name);
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
    public void goBack(){
        screneVisited.pop();
        String name = screneVisited.peek();
        this.switchToScene(name);
    }
    public void refresh(String name){
        ScenePage s = null;
        for(SceneContainer sceneContainer: scenes){
            if(sceneContainer.getName().equals(name)){
                sceneContainer.refresh();

            }
        }
    }
    public void setTitle(String sceneName, String title){
        for (SceneContainer sceneContainer: scenes){
            if(sceneContainer.getName().equals(sceneName)){
                sceneContainer.setTitle(title);
            }
        }
    }
}