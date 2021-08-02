
package com.example.hailandbank.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class Merchant extends User {
    
    public static final String STATUS_ACTIVE = "active";
    
    public static final String STATUS_INACTIVE = "inactive";
    
    public static enum Level {
        
        ONE(1, 100000),
        
        TWO(2, 1000000),
        
        THREE(3, -1);
        
        private int value;
        
        private double transactionLimit;

        private Level(int value, double transactionLimit) {
            this.value = value;
            this.transactionLimit = transactionLimit;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public double getTransactionLimit() {
            return transactionLimit;
        }

        public void setTransactionLimit(double transactionLimit) {
            this.transactionLimit = transactionLimit;
        }
        
    }

    @SerializedName(value = "merchant_id")
    private long merchantId;
    
    private String name;
    
    private String code;
    
    private int level;

    @SerializedName(value = "transaction_limit")
    private double transactionLimit;
    
    private String status;

    @SerializedName(value = "status_updated_at")
    private String statusUpdatedAt;

    @SerializedName(value = "settlement_accounts")
    private List<SettlementAccount> settlementAccounts;
    
    
    public long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(long merchantId) {
        this.merchantId = merchantId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(double transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatusUpdatedAt() {
        return statusUpdatedAt;
    }

    public void setStatusUpdatedAt(String statusUpdatedAt) {
        this.statusUpdatedAt = statusUpdatedAt;
    }

    public List<SettlementAccount> getSettlementAccounts() {
        return settlementAccounts;
    }

    public void setSettlementAccounts(List<SettlementAccount> settlementAccounts) {
        this.settlementAccounts = settlementAccounts;
    }

    public SettlementAccount getSettlementAccount(int i) {
        if (this.settlementAccounts == null || this.settlementAccounts.isEmpty())
            return null;
        return settlementAccounts.get(i);
    }

    public void addSettlementAccount(SettlementAccount account) {
        if (this.settlementAccounts == null)
            this.settlementAccounts = new ArrayList<>();
        this.settlementAccounts.add(account);
    }

}



