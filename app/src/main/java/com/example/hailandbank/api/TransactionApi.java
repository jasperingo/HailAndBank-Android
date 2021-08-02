package com.example.hailandbank.api;


import com.example.hailandbank.models.Transaction;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface TransactionApi {

    @POST("transaction/fund")
    Call<Result.Success<Transaction>> fund(@Body Transaction t);

    @POST("transaction/withdraw")
    Call<Result.Success<Transaction>> withdraw(@Body Transaction t);

}

