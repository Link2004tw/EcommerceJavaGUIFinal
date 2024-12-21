package com.example.ecommerceguiv2.Exceptions;

public class CantPerformAction extends RuntimeException {
    public CantPerformAction(String message) {
        super(message);
    }
}
