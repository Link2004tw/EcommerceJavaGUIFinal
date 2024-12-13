package com.example.ecommerceguiv2;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.HashMap;
import java.util.Map;
public class SceneController {
    private final Stage stage;
    private final Map<String, Scene> scenes = new HashMap<>();
    public SceneController(Stage stage) {
        this.stage = stage;
    }
    // Add a scene programmatically
    public void addScene(String name, Pane layout) {
        Scene scene = new Scene(layout);
        scenes.put(name, scene);
    }
    public void addScene(String name, Scene s) {
        scenes.put(name, s);
    }
    // Switch to a specific scene by name
    public void switchToScene(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Scene " + name + " not found!");
        }
    }
}