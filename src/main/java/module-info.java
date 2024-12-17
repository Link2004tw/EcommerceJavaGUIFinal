module com.example.ecommerceguiv2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens com.example.ecommerceguiv2 to javafx.fxml;
    exports com.example.ecommerceguiv2;
    exports com.example.ecommerceguiv2.Models;
    opens com.example.ecommerceguiv2.Models to javafx.fxml;
    exports com.example.ecommerceguiv2.Exceptions;
    opens com.example.ecommerceguiv2.Exceptions to javafx.fxml;
    exports com.example.ecommerceguiv2.Scenes;
    opens com.example.ecommerceguiv2.Scenes to javafx.fxml;
    exports com.example.ecommerceguiv2.Components;
    opens com.example.ecommerceguiv2.Components to javafx.fxml;
}