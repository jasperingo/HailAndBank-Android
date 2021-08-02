package com.example.hailandbank.api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Api {


    public static final String BASE_URL = "http://192.168.43.160:8080/hailandbank/api/v1/";


    private static Gson getGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    public static Retrofit getConnection() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

    public static Retrofit getConnection(String bearerToken) {

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(chain -> {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder()
                    .header("Authorization", "Bearer "+bearerToken);

            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }).build();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

}


