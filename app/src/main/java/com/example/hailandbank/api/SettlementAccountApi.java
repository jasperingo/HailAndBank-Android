package com.example.hailandbank.api;

import com.example.hailandbank.models.SettlementAccount;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SettlementAccountApi {

    @POST("settlement-account/add")
    Call<Result.Success<SettlementAccount>> add(@Body SettlementAccount account);

}

