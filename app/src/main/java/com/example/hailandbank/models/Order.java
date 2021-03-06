
package com.example.hailandbank.models;


import com.google.gson.annotations.SerializedName;


public class Order extends Entity {

    
    public static final String STATUS_PENDING = "pending";
    
    public static final String STATUS_FAILED = "failed";
    
    public static final String STATUS_PROCESSING = "processing";
    
    public static final String STATUS_CANCELLED = "cancelled";
    
    public static final String STATUS_FULFILLED = "fulfilled";
     
     
    public static enum Type {
        
        CASH_DELIVERY("withdrawal", "indoor"),
        
        CASH_PICK_UP("deposit", "indoor");
        
        private String value;
        
        private String mode;

        private Type(String value, String mode) {
            this.value = value;
            this.mode = mode;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }
        
    }

    
    private Account account;

    @SerializedName(value = "merchant_account")
    private Account merchantAccount;
    
    private String type;
    
    private String mode;
    
    private String status;
    
    private double amount;
    
    private double charge;

    @SerializedName(value = "address_street")
    private String addressStreet;

    @SerializedName(value = "address_city")
    private String addressCity;

    @SerializedName(value = "address_state")
    private String addressState;

    @SerializedName(value = "address_x")
    private double addressX;

    @SerializedName(value = "address_y")
    private double addressY;
    
    
   
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getMerchantAccount() {
        return merchantAccount;
    }

    public void setMerchantAccount(Account merchantAccount) {
        this.merchantAccount = merchantAccount;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }
    
    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressState() {
        return addressState;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public double getAddressX() {
        return addressX;
    }

    public void setAddressX(double addressX) {
        this.addressX = addressX;
    }

    public double getAddressY() {
        return addressY;
    }

    public void setAddressY(double addressY) {
        this.addressY = addressY;
    }
    
    
    
    
}


