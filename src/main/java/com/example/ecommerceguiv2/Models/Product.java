package com.example.ecommerceguiv2.Models;

public class Product implements Validatable<Integer> {
    private static int nbOfProducts =0;
    private String name;
    private String description;
    private double price=9.99;
    private int stockQuantity=0;
    private Category category;
    private int id;
    public Product() {}
    public Product(String name,String description,double price,int stockQuantity, Category c){
        this.name=name;
        this.description=description;
        this.price=price;
        this.stockQuantity=stockQuantity;
        this.id = nbOfProducts;
        category = c;
        nbOfProducts++;
    }
    public Product(String name,String description,double price,int stockQuantity, Category c, int id){
        this.name=name;
        this.description=description;
        this.price=price;
        this.stockQuantity=stockQuantity;
        this.id = id;
        category = c;

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
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public int getId() {
        return id;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    public void setId(int id){this.id=id;}
    public void updateStock(int updatedQuantity)
    {
        setStockQuantity(updatedQuantity);
    }
    public void updatePrice(double newPrice){
        setPrice(newPrice);
    }

    @Override
    public boolean equals(Object o){
        return ((Product)o).id == id;
    }
    @Override
public String toString() {
        return "Id: " + id +
                "\nName: " + name +
                "\nDescription: " + this.description +
                "\nPrice: " + this.price + "$" +
                "\nCategory: " + this.category.getName();


    }

    @Override
    public boolean search(String value) {
        return this.name.equals(value);
    }

    @Override
    public boolean validate(Integer value) {
        // checks if product is in stock
        return this.stockQuantity >0;
    }
    public String showDetails(){
        return this.toString();
    }
    public String showMinDetails(){
        return "Id: " +id + " Name: " + name + " Price: " + price + (stockQuantity > 0 ? (" " + stockQuantity + " available"): "Sold out");
    }


}
