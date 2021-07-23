package com.example.hailandbank.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.hailandbank.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.USER_SP_KEY),  Context.MODE_PRIVATE);

        final boolean shouldWelcome = sharedPreferences.getString(getString(R.string.USER_PHONE_NUMBER_SP_KEY), null) != null;

    }



}




