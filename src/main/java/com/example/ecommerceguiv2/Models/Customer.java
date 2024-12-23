package com.example.ecommerceguiv2.Models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer extends Person implements Validatable<String> {
    private List<Card> cards;

    private Cart cart;
    public Customer(
            String username,
            String password,
            Date dateOfBirth,
            double balance,
            Gender gender)
    {
        super(username, password, dateOfBirth, balance, gender);
        cart = new Cart(id);
        cards = new ArrayList<>();
    }
    public double viewBalance(){
        return super.getBalance();
    }

//    public void showProducts(Database db){
//        for(Product p: db.getProducts()){
//            System.out.println(p.showMinDetails());
//
//        }
//
//    }
    public void addToCart(int id,int q, Database db){
        Product p = db.findById(id, Product.class);
        cart.addProduct(p, q, db);
        System.out.println(cart.toString());
    }
    public void addToCart(Product p, int q, Database db){
        cart.addProduct(p, q, db);
        System.out.println(cart.toString());
    }

    public List<Card> getCards() {
        return cards;
    }

    public void makeOrder(Database db, Order.PaymentMethod paymentMethod){
        Order order = new Order(this, cart.getProducts(), cart.getTotalAmount(), paymentMethod);
        db.addOrder(order);
        cart.clearCart();
    }

//    public void newOrder(Customer customer, List<Item> products, double total, Order.PaymentMethod paymentMethod)
//    {
//        Order order = new Order(customer, products, total, paymentMethod);
//        cart.clearCart();
//    }


    @Override
    public boolean equals(Object obj) {
        return id == ((Customer) obj).id;
    }

    public List<Order> getOrders(Database db){
        return db.getOrders(id);

    }

    @Override
    public boolean search(String value) {
        // searches if this username exists
        return this.username.equals(value);
    }

    @Override
    public boolean validate(String value)
    {
        return this.password.equals(value);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void addCard(Card c){
        cards.add(c);
    }
}

