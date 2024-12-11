package com.example.ecommerceguiv2.Models;

import com.example.ecommerceguiv2.Exceptions.IncorrectPasswordException;
import com.example.ecommerceguiv2.Exceptions.NotFoundException;
import com.example.ecommerceguiv2.Exceptions.RegisterFailException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Date;


public class Database {
    private List<Customer> customers;
    private List<Admin> admins;
    private List<Category> categories;
    private List<Product> products;
    private List<Order> orders;
    private List<Cart> carts;
    public Database() {
        admins = new ArrayList<>();
        categories = new ArrayList<>();
        products = new ArrayList<>();
        orders = new ArrayList<>();
        customers = new ArrayList<>();
    }
    public List<Customer> getCustomers(){
        return customers;
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void initializeDummyData(){}

    public void addCustomer(Customer c){
        customers.add(c);
        System.out.println("Added Successfully");
    }
    public void addAdmin(Admin a){
        admins.add(a);
        System.out.println("Added Successfully");
    }
    public void addProduct(Product p){
        products.add(p);
        boolean found=false;
        for(Category c:categories)
            if (c.equals(p.getCategory())){
                found=true;
                c.addProduct(p);
            }

        if(!found) {
            Category c1 = p.getCategory();
            c1.addProduct(p);
            categories.add(c1);

        }
        System.out.println("Added Successfully");
    }
    public void addCategory(Category ca){
        categories.add(ca);
        System.out.println("Added Successfully");
    }
    public void addOrder(Order o){
        orders.add(o);
        System.out.println("Added Successfully");
    }
    public boolean deleteOrderById(int id) {
        for (Order order : orders) {
            if (order.getId() == id) {
                orders.remove(order);
                return true; // Order found and removed
            }
        }
        return false; // Order with the given ID not found
    }
    public Customer getCustomerById(int id) throws NotFoundException {
        //needs to add validation
        for(Customer customer: customers){
            if(customer.getId() == id){
                return customer;
            }
        }
        throw new NotFoundException("Customer not found");
    }

    public Person login(String username, String password) throws NotFoundException, IncorrectPasswordException {

        for (Customer customer: customers){
            if (customer.search(username)){
                if (customer.validate(password)){
                    return customer;
                }
                throw new IncorrectPasswordException("Incorrect password entered");
            }
        }

        for (Admin admin: admins){
            if (admin.search(username)) {
                if (admin.validate(password)){
                    return admin ;
                }
                throw new IncorrectPasswordException("Incorrect password entered");
            }
        }
        throw new NotFoundException("Account not found!!");
    }

    public Customer register(String username, String password) throws NotFoundException, RegisterFailException,IllegalArgumentException, ParseException  {
        Date now = new Date();
        boolean found = false;
        for (Customer customer: customers){
            if (customer.search(username)){
                found = true;
                break;
            }
        }
        if (!found) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your date of birth(yyyy-MM-dd): ");
            String dateString = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            try {
                // Convert string to date
                date = dateFormat.parse(dateString);

            } catch (ParseException e) {
                throw new ParseException(e.getMessage(), 0);
            }
            if(date.after(now)){
                throw new IllegalArgumentException("Invalid date");
            }
            System.out.print("Enter your gender: ");
            String genderInput = scanner.nextLine();
            Person.Gender gender = Person.Gender.valueOf(genderInput.toUpperCase());

            Customer newCustomer = new Customer(username, password, date, 0,gender);
            customers.add(newCustomer);
            System.out.println("Registration successful!");
            return newCustomer;
        }
        throw new RegisterFailException("Username Already exists");

    }
    public <T> T findById(int id, Class<T> c) throws NotFoundException {
        if(c == Product.class){
            for(Product p : products){
                if(p.getId() == id){
                    return c.cast(p);
                }
            }
            throw new NotFoundException("Product not found");
        }
        else if(c == Customer.class){
            for(Customer customer : customers){
                if(customer.getId() == id){
                    return c.cast(customer);
                }
            }
            throw new NotFoundException("Customer not found");
        }
        else if(c == Admin.class){
            for(Admin admin : admins){
                if(admin.getId() == id){
                    return c.cast(admin);
                }
            }
            throw new NotFoundException("Admin not found");
        }
        else if(c == Cart.class){
            for(Cart cart : carts){
                if(cart.getId() == id){
                    return c.cast(cart);
                }
            }
            throw new NotFoundException("Cart not found");
        }
        else if(c == Order.class){
            for(Order order : orders){
                if(order.getId() == id){
                    return c.cast(order);
                }
            }
            throw new NotFoundException("Customer not found");
        }
        throw new NotFoundException("Class Doesn't exist");
    }

    public <T> List<T> findAll(Class<T> clazz) {
        List<T> result = new ArrayList<>();

        // Check the class type and return the appropriate list
        if (clazz == Product.class) {
            result.addAll((List<T>) products);
        } else if (clazz == Customer.class) {
            result.addAll((List<T>) customers);
        } else if (clazz == Order.class) {
            result.addAll((List<T>) orders);
        }
        else if (clazz == Admin.class){
            result.addAll((List<T>) admins);
        }
        else if(clazz == Cart.class){
            result.addAll((List<T>) carts);
        }

        return result;
    }
    private <T> int getIndex(Class<T> c, T object) throws NotFoundException{
        int index = -1;
        int i =0;
        if(c == Cart.class){
            for(Cart cart: carts){
                if(cart.equals(object)){
                    index = i;
                    break;
                }
                i++;
            }
        }
        else if(c == Admin.class){
            for(Admin admin: admins){
                if(admin.equals(object)){
                    index = i;
                    break;
                }
                i++;
            }
        }
        else if(c == Customer.class){
            for(Customer customer: customers){
                if(customer.equals(object)){
                    index = i;
                    break;
                }
                i++;
            }
        }
        else if(c == Category.class){
            for(Category category: categories){
                if(category.equals(object)){
                    index = i;
                    break;
                }
                i++;
            }
        }
        else if(c == Product.class){
            for(Product product: products){
                if(product.equals(object)){
                    index = i;
                    break;
                }
                i++;
            }
        }
        else if(c == Order.class){
            for(Order order: orders){
                if(order.equals(object)){
                    index = i;
                    break;
                }
                i++;
            }
        }
        if(index == -1) {
            throw new NotFoundException("Can't find it");
        }
        return index;
    }
    public <T> void update(Class<T> c, T object) throws NotFoundException {
        int index =getIndex(c, object);
        if(c == Cart.class){
            carts.set(index, (Cart)object);
        }
        else if(c == Admin.class){
            admins.set(index, (Admin) object);
        }
        else if(c == Customer.class){
            customers.set(index, (Customer)object);
        }
        else if(c == Category.class){

            categories.set(index, (Category) object);
        }
        else if(c == Product.class){

            products.set(index, (Product)object);
        }
        else if(c == Order.class){
            orders.set(index, (Order) object);
        }
    }

    public<T> void delete(Class<T> c, T object) throws NotFoundException {
        int index = getIndex(c, object);
        if(c == Cart.class){
            carts.remove(index);
        }
        else if(c == Admin.class){
            admins.remove(index);
        }
        else if(c == Customer.class){
            customers.remove(index);
        }
        else if(c == Category.class){
            categories.remove(index);
        }
        else if(c == Product.class){
            products.remove(index);
        }
        else if(c == Order.class){
            orders.remove(index);
        }
    }
    public <T> void deleteById(int id, Class<T> c){
        T o = findById(id, c);
        delete(c, o);
    }

    public List<Order> getOrders(int id){
        List<Order> customerOrder = new ArrayList<>();
        for(Order o : orders){
            if(o.getCustomer().getId() == id){
                customerOrder.add(o);
            }
        }
        return customerOrder;
    }

}

