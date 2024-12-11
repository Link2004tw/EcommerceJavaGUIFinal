package com.example.ecommerceguiv2.Models;

import com.example.ecommerceguiv2.Exceptions.NotEnoughException;
import com.example.ecommerceguiv2.Exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cart {
    private int customerId;
    private int id;
    private static int nbOfCarts = 0;
    private List<Item> items = new ArrayList<>() ;
    private double totalAmount;
    Cart (int customerId) {
        this.customerId = customerId;
        this.totalAmount =0;
        id = nbOfCarts;
        nbOfCarts++;
    }

    public int getId() {
        return id;
    }
    public double getTotalAmount(){
        return totalAmount;
    }

    public List<Item> getProducts() {
        return items;
    }

    public void addProduct (Product product, int q, Database db) throws NotEnoughException, NotFoundException {
        boolean found = false;
        Product stockedProduct = db.findById(product.getId(), Product.class);
        if(stockedProduct.getStockQuantity() >= q){
            for(Item item : items){
                if(item.getProduct().equals(product)){
                    item.setQuantity(item.getQuantity() + q);
                    found = true;

                }
            }
            if(!found){
                Item item = new Item(q, product); //need to add quantity 3edel
                items.add(item);
            }
            totalAmount += product.getPrice() * q;
            stockedProduct.setStockQuantity(stockedProduct.getStockQuantity() -q);
        }else {
            throw new NotEnoughException("Not enough in stock, will restock it soon");
        }
    }


    public void removeProduct (Product product) throws NotFoundException {
        int q=0;
        for(Item item: items){
            if(item.getProduct().equals(product)){
                q = item.getQuantity();
            }
        }
        items.removeIf(item -> item.getProduct().equals(product));
        totalAmount -= product.getPrice() *q;

    }
//    public double calculateTotal (){
//        double total=0;
//        for (Item item : items) {
//            total += item.product.getPrice() * item.quantity;
//        }
//        return total;
//    }

    public Order placeOrder (Database db) {
        System.out.println("Select payment method (CREDIT_CARD, DEBIT_CARD, PAYPAL, CASH, BALANCE)");
        Scanner var = new Scanner(System.in);
        String input = var.nextLine().toUpperCase();
        Order.PaymentMethod paymentMethod;
        try {
            paymentMethod = Order.PaymentMethod.valueOf(input);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid payment method. Defaulting to CASH.");
            paymentMethod = Order.PaymentMethod.CASH;
        }
        Customer customer = db.getCustomerById(customerId);
        if(paymentMethod == Order.PaymentMethod.BALANCE) {
            if (customer.getBalance() < totalAmount) {
                System.out.println("Invalid payment method. Defaulting to CASH. Not enough balance");
                paymentMethod = Order.PaymentMethod.CASH;
            }else {
                customer.setBalance(customer.getBalance() - totalAmount);
                db.update(Customer.class, customer);
            }
        }
        Order o = new Order(customer, items,totalAmount,paymentMethod);
        items.clear();
        return o;
    }

    public void clearCart()
    {
        items.clear();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Your Cart:");
        for (Item item: items){
            sb.append(item.getProduct().getName() + "(" + item.getQuantity()  + ")\n");
        }
        return sb.toString();

    }
}

