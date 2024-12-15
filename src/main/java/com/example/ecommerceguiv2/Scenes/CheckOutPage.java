package com.example.ecommerceguiv2.Scenes;

import com.example.ecommerceguiv2.Components.SceneController;
import com.example.ecommerceguiv2.Models.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class CheckOutPage extends ScenePage{

    public CheckOutPage(Database db, SceneController sc){
        BorderPane root=new BorderPane();
        Customer customer = db.getLoggedCustomer();
        if (customer != null) {
            Cart cart = customer.getCart();
            if (!cart.isEmpty()){
                TableView<Item> table = setupTableView(cart);
                root.setCenter(table);
                TableView<Item> table = new TableView<>();
                TableColumn<Label, String> labelColumn = new TableColumn<>("Name");
            }
        }
    }
}
