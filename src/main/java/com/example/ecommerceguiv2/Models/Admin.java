package com.example.ecommerceguiv2.Models;

import com.example.ecommerceguiv2.Exceptions.AlreadyExistsException;
import com.example.ecommerceguiv2.Exceptions.NotFoundException;

import java.util.Date;
import java.util.List;

public class Admin extends Person implements Validatable<String> {
    private String role;
    private int workingHours;

    public Admin(
            String username,
            String password,
            Date dateOfBirth,
            double balance,
            Gender gender,
            String r,
            int wh) {
        super(username, password, dateOfBirth, balance, gender);
        role = r;
        workingHours = wh;
    }

    @Override
    public double getBalance() {
        return super.getBalance(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public String getRole() {
        return role;
    }

    @Override
    public Gender getGender() {
        return super.getGender(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String getUsername() {
        return super.getUsername(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public int getWorkingHours() {
        return workingHours;
    }

    @Override
    public String toString() {
        return super.toString() + "\nRole: " + role + "\nWorking: " + workingHours;
    }

    public void showAllUsers(Database db) {
        List<Customer> customers = db.getCustomers();
        System.out.println("Customers");
        for (Customer customer : customers) {
            System.out.print(customer.toString());
        }

    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public void showAllProducts(Database db) {
        List<Product> products = db.getProducts();
        System.out.println("Products");
        for (Product product : products) {
            System.out.print(product.toString());
        }
    }

    public void showAllOrders(Database db) {
        List<Order> orders = db.getOrders();
        System.out.println("Orders");
        for (Order order : orders) {
            System.out.print(order.toString());
        }
    }
    public void showAllCategories(Database db){
        List<Category> categories = db.getCategories();
        System.out.println("Categories");
        for (Category category : categories) {
            System.out.print(category.toString());
            category.showProducts();
        }
    }

    @Override
    public boolean search(String value) {
        // searches if this username exists
        return this.username.equals(value);
    }

    @Override
    public boolean validate(String value) {
        return this.password.equals(value);
    }

    public <T> void addNew(T adding, Class<T> c, Database db) throws AlreadyExistsException {
        boolean found = false;
        if (c == Product.class) {
            for (Product p : db.getProducts())
                if (p.equals(adding))
                    found = true;
            if (!found)
                db.addProduct((Product) adding);
            else throw new AlreadyExistsException("product already exists");
        } else if (c == Category.class) {
            for (Category p : db.getCategories())
                if (p.equals(adding))
                    found = true;
            if (!found)
                db.addCategory((Category) adding);
            else throw new AlreadyExistsException("category already exists");
        }
    }

    public void update(double newPrice, int newStock, Product c, Database db) throws NotFoundException {
        boolean found = false;
        for (Product p : db.getProducts())
            if (p.equals(c))
                found = true;
        if (found) {
            c.setPrice(newPrice);
            c.setStockQuantity(newStock);
        } else throw new NotFoundException("product not found");
    }

    public void update(Category c, Product p, boolean willAdd) { //based on willAdd h-add or delete
        if (willAdd) {
            try {
                c.addProduct(p);
            } catch (AlreadyExistsException e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                c.deleteProduct(p);
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public <T> void read(T search, Class<T> c) throws NotFoundException{
        if(c==Category.class)
            ((Category)search).toString();
        else if (c==Product.class)
            ((Product)search).toString();
    }
    public <T> void deleteObject(T deleting, Class<T> c, Database db) throws NotFoundException {
        boolean found = false;
        if (c == Product.class) {
            for (Product p : db.getProducts())
                if (p.equals(deleting))
                    db.delete(Product.class, (Product) deleting);
                else throw new NotFoundException("product not found");
        } else if (c == Category.class) {
            for (Category p : db.getCategories())
                if (p.equals(deleting))
                    db.delete( Category.class, (Category) deleting);
                else throw new NotFoundException("category not found");
        }
    }

}
