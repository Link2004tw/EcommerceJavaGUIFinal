package com.example.ecommerceguiv2.Models;

import java.util.Date;

public abstract class Person {
    private static int nbOfPersons = 0;
    public enum Gender {
        MALE,
        FEMALE
    }

    protected String username;
    protected String password;
    protected Date dateOfBirth;
    protected double balance;
    protected Gender gender;
    protected int id;

    protected Person(String username, String password, Date dateOfBirth, double balance, Gender gender) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
        this.gender = gender;
        this.id = nbOfPersons;
        nbOfPersons++;
    }

    public int getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    public String getDateOfBirth() {
        return dateOfBirth.toString();
    }

    public Gender getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Username: " + username +
                "\nGender: " + gender +
                "\nBalance: " + balance +
                "\nDate of Birth: " + dateOfBirth.toString() +
                "\n";
    }

    public void updateProfile(String username, String password, Date dateOfBirth, double balance, Gender gender) {
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
        this.gender = gender;

    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
