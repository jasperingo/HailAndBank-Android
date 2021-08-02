
package com.example.hailandbank.models;


import com.google.gson.annotations.SerializedName;


public class Customer extends User {

    @SerializedName(value = "customer_id")
    private long customerId;

    @SerializedName(value = "preferred_merchant")
    private Merchant preferredMerchant;
    
    
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    
    public Merchant getPreferredMerchant() {
        return preferredMerchant;
    }

    public void setPreferredMerchant(Merchant preferredMerchant) {
        this.preferredMerchant = preferredMerchant;
    }
    
    public void setPreferredMerchant(long merchantId) {
        if (merchantId > 0) {
            Merchant m = new Merchant();
            m.setId(merchantId);
            this.preferredMerchant = m;
        }
    }
   
    
}


