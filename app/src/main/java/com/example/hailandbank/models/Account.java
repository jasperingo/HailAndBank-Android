
package com.example.hailandbank.models;



public class Account extends Entity {

    
    public static final String TYPE_SAVINGS = "savings";

    
    private User user;
    
    private String type;
    
    private String number;
    
    private double balance;
    

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    
}

