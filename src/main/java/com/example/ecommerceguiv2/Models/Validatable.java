package com.example.ecommerceguiv2.Models;

interface Validatable<T> {
    boolean search(String value) ;
    boolean validate(T value1);

}
