
package com.example.hailandbank.models;


import com.google.gson.annotations.SerializedName;


public class SettlementAccount extends Entity {

    
    private Merchant merchant;

    @SerializedName(value = "bank_name")
    private String bankName;
    
    private String number;
    
    private String type;
    

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}

