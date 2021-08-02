
package com.example.hailandbank.models;


import com.google.gson.annotations.SerializedName;



abstract public class Entity {
    
    protected long id;

    @SerializedName(value = "updated_at")
    protected String updatedAt;

    @SerializedName(value = "created_at")
    protected String createdAt;
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
}

