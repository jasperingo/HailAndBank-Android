package com.example.hailandbank.api;

import com.example.hailandbank.models.AuthToken;
import com.example.hailandbank.models.Customer;
import com.example.hailandbank.models.Merchant;
import com.example.hailandbank.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface UserApi {

    @POST("customer/signup")
    Call<Result.Success<AuthToken>> customerSignUp(@Body Customer user);

    @POST("merchant/signup")
    Call<Result.Success<AuthToken>> merchantSignUp(@Body Merchant user);

    @POST("customer/signin")
    Call<Result.Success<AuthToken>> customerSignIn(@Body Customer user);

    @POST("merchant/signin")
    Call<Result.Success<AuthToken>> merchantSignIn(@Body Merchant user);

    @GET("customer")
    Call<Result.Success<Customer>> fetchCustomer();

    @GET("merchant")
    Call<Result.Success<Merchant>> fetchMerchant();

    @PUT("update-pin")
    Call<Result.Success<Void>> updatePin(@Body User user);

    @PUT("merchant/update-name")
    Call<Result.Success<Void>> updateMerchantName(@Body Merchant user);

    @PUT("merchant/update-address")
    Call<Result.Success<Void>> updateMerchantAddress(@Body User user);

    @PUT("customer/update-address")
    Call<Result.Success<Void>> updateCustomerAddress(@Body User user);


}


