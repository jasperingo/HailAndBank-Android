
package com.example.hailandbank.models;


import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;


public class User extends Entity {
    

    public static final String TYPE_CUSTOMER = "customer";
    
    public static final String TYPE_MERCHANT = "merchant";
    
    public static final String USERS_IMG_PATH = "/path/to/users/imgs/";
    
    public static final String USERS_DEFAULT_IMG = "user.jpg";


    protected String photo;

    protected String type;

    @SerializedName(value = "first_name")
    protected String firstName;

    @SerializedName(value = "last_name")
    protected String lastName;

    @SerializedName(value = "middle_name")
    protected String middleName;

    protected String email;

    @SerializedName(value = "phone_number")
    protected String phoneNumber;

    protected String pin;

    @SerializedName(value = "address_street")
    protected String addressStreet;

    @SerializedName(value = "address_city")
    protected String addressCity;

    @SerializedName(value = "address_state")
    protected String addressState;


    @SerializedName(value = "auth_token")
    protected AuthToken authToken;

    protected List<Account> accounts;
    
    /*@JsonbProperty("pin_reset")
    @XmlElement(name = "pin_reset")
    private PinReset pinReset;
    
    @JsonbProperty("phone_number_verification_otp")
    @XmlElement(name = "phone_number_verification_otp")
    private String phoneNumberVerificationOTP;*/

    @SerializedName(value = "new_pin")
    private String newPin;
    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = USERS_IMG_PATH+(photo==null?USERS_DEFAULT_IMG:photo);
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName.trim();
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
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

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
    
    /*public List<AuthToken> getAuthTokens() {
        return authTokens;
    }

    public void setAuthTokens(List<AuthToken> authTokens) {
        this.authTokens = authTokens;
    }*/

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
    
    public Account getAccount(int i) {
        if (this.accounts == null)
            return null;
        return accounts.get(i);
    }
    
    public void addAccount(Account account) {
        if (this.accounts == null)
            this.accounts = new ArrayList<>();
        this.accounts.add(account);
    }
    
    /*public PinReset getPinReset() {
        return pinReset;
    }

    public void setPinReset(PinReset pinReset) {
        this.pinReset = pinReset;
    }

    public String getPhoneNumberVerificationOTP() {
        return phoneNumberVerificationOTP;
    }

    public void setPhoneNumberVerificationOTP(String phoneNumberVerificationOTP) {
        this.phoneNumberVerificationOTP = phoneNumberVerificationOTP;
    }*/

    public String getNewPin() {
        return newPin;
    }

    public void setNewPin(String newPin) {
        this.newPin = newPin;
    }
    
    
}


