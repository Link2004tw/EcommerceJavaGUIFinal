package com.example.ecommerceguiv2.Models;

import java.util.Date;
import java.util.List;

class Order {
    public enum PaymentMethod {
        CREDIT_CARD,
        PAYPAL,
        BANK_TRANSFER,
        CASH,
        BALANCE
    }

    private static int nbOfOrders=0;
    private int orderId;
    private Customer customer;
    private Date orderDate;
    private List<Item> orderItems;
    private double total;
    private PaymentMethod paymentMethod;
    private String status;

    // constructor
    public Order(Customer customer, List<Item> products, double total, PaymentMethod paymentMethod) {
        this.customer = customer;
        this.orderId = nbOfOrders;
        this.orderItems = products;
        this.orderDate = new Date();
        this.total = total;
        this.paymentMethod = paymentMethod;
        this.status = "Not processed";
        nbOfOrders++;
    }

    // getters
    public int getId() {
        return orderId;
    }

    public List<Item> getProducts() {
        return orderItems;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTotal() {
        return total;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    // setters
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // methods
    public void processOrder() {
        for (Item orderItem : orderItems) {
            orderItem.getProduct().setStockQuantity(orderItem.getProduct().getStockQuantity() - orderItem.getQuantity()
            );
        }
        status = "processed";
    }

    public void cancelOrder() {
        if (status.equals("processed")) {
            for (Item orderItem : orderItems) {
                orderItem.getProduct().setStockQuantity(orderItem.getProduct().getStockQuantity() + orderItem.getQuantity()
                );

            }
        }
        System.out.println("Order canceled");
        status = "Cancelled";
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Your Order:" +
                "\nOrder Id: " + orderId +
                "\nOrder Date: " + orderDate +
                "\nFor: " + customer.getUsername() + "\nProducts: \n"
        );
        for (Item item: orderItems){
            sb.append(item.getProduct().getName() + "(" + item.getQuantity()  + ")\n");
        }
        sb.append("total: ").append(String.format("%.2f", total)).append("\npaymentMethod: " + paymentMethod);
        return sb.toString();
    }

}

