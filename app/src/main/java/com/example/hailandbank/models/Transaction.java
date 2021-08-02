
package com.example.hailandbank.models;


import com.google.gson.annotations.SerializedName;


public class Transaction extends Entity {

    public static final String STATUS_PENDING = "pending";
    
    public static final String STATUS_FAILED = "failed";
    
    public static final String STATUS_PROCESSING = "processing";
    
    public static final String STATUS_CANCELLED = "cancelled";
    
    public static final String STATUS_APPROVED = "approved";
    
    
    public static final String TYPE_FUND = "fund";
    
    public static final String TYPE_WITHDRAW = "withdraw";
    
    public static final String TYPE_ORDER_CHARGE = "order charge";
    
    public static final String TYPE_ORDER_PROFIT = "order profit";
    

    @SerializedName(value = "reference_code")
    private String referenceCode;
    
    private Order order;
    
    private Account account;
    
    private String type;
    
    private String status;
    
    private double amount;
    
    
    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

   
    
}


