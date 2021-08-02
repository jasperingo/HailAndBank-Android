package com.example.hailandbank.utils;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.hailandbank.models.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MyApplication extends Application {

    public ExecutorService executorService = Executors.newFixedThreadPool(4);
    public Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    public User user;

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}


