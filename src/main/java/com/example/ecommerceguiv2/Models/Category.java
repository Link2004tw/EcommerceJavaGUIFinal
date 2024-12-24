package com.example.ecommerceguiv2.Models;


import com.example.ecommerceguiv2.Exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private String description;
    private List<Product> products;
    private int id;
    private static int nbOfCategories;
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
        this.products = new ArrayList<Product>();
        id = nbOfCategories;
        nbOfCategories++;
    }

    public Category(String name, String description, List<Product> products) {
        this.name = name;
        this.description = description;
        this.products = products;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        for (Product product : products) {
            product.setCategory(this);
        }
        this.products = products;
    }

    public void showProducts() {
        System.out.println("Products: ");
        for (Product p : products) {
            System.out.println(p.toString());
        }
    }

    public void addProduct(Product newProduct) {
        int i = 0;
        for (Product p : products) {
            if (p.equals(newProduct)) {
                products.set(i, newProduct);
                return;
            }
            i++;
        }
        products.add(newProduct);
        newProduct.setCategory(this);

        //else throw new AlreadyExistsException("product already exists");
    }

    public void deleteProduct(Product product) throws NotFoundException {
        int i = 0;
        for (Product p : products) {
            if (p.equals(product)) {
                products.remove(i);
                product = null;
                return;
            }
            i++;
        }
        throw new NotFoundException("product not found");
    }

    public void removeProduct(Product deletedProduct) {
        products.remove(deletedProduct);
    }

    public String toString() {
        return "Name: " + name +
                "\nDescription: " + description + "\n";
    }

    public void save(Database db) {
        db.addCategory(this);
        //System.out.println("Category added successfully");
    }

    public static Category findById(int id, Database db) {
        return db.findById(id, Category.class);
    }

    public static List<Category> findAll(Database db) {
        return db.findAll(Category.class);
    }

    public void update(Database db) {

        db.update(Category.class, this);
    }

    public void delete(Database db) throws NotFoundException {
        db.delete(Category.class, this);
    }

    static void deleteById(int id, Database db) {
        db.deleteById(id, Category.class);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object c) {
        return this.id == ((Category) c).getId();
    }
}
