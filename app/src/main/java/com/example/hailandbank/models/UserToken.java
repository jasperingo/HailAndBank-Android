
package com.example.hailandbank.models;


import java.time.LocalDateTime;


abstract public class UserToken extends Entity {
    

    protected User user; 
    
    protected String token;
    
    protected String expires;
    
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }
        
}

