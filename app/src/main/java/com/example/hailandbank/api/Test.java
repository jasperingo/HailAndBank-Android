package com.example.hailandbank.api;

import retrofit2.Call;
import retrofit2.http.GET;


public interface Test {

    @GET("home")
    Call<Result> home();

}


