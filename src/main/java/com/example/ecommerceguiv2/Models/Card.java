package com.example.ecommerceguiv2.Models;

import java.time.YearMonth;

public class Card {
    private String cardNumber;
    private String cardHolderName;
    private YearMonth expiryDate;
    private String cvv;

    // Constructor
    public Card(String cardNumber, String cardHolderName, YearMonth expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    // Getters and Setters
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public YearMonth getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(YearMonth expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    // Validation methods
    public boolean isCardNumberValid() {
        return cardNumber != null && cardNumber.matches("\\d{16}");
    }

    public boolean isCardHolderNameValid() {
        return cardHolderName != null && !cardHolderName.trim().isEmpty();
    }

    public boolean isExpiryDateValid() {
        return expiryDate != null && expiryDate.isAfter(YearMonth.now());
    }

    public boolean isCvvValid() {
        return cvv != null && cvv.matches("\\d{3}");
    }

    public boolean isValidCard() {
        return isCardNumberValid() && isCardHolderNameValid() && isExpiryDateValid() && isCvvValid();
    }

    @Override
    public String toString() {
        return cardNumber.length() >= 4 ? cardNumber.substring(cardNumber.length() - 4) : cardNumber;

    }

    @Override
    public boolean equals(Object obj) {
        return ((Card) obj).cardNumber.equals(cardNumber);
    }
}

