package com.example.ecommerceguiv2.Models;

public class Item {
    private Product product;
    private int quantity = 0;
    Item(int q, Product p){
        product = p;
        quantity = q;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int q){
        quantity = q;
    }
}
