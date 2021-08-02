package com.example.hailandbank.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hailandbank.R;
import com.example.hailandbank.models.User;
import com.example.hailandbank.utils.MyApplication;
import com.example.hailandbank.viewmodels.MainActivityViewModel;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActivityViewModel viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        viewModel.getUser().observe(this, this::onAuth);
        viewModel.getNullUser().observe(this, this::onUnAuth);

        sharedPreferences = getSharedPreferences(
                getString(R.string.USER_SP_KEY),  Context.MODE_PRIVATE);

        if (((MyApplication) getApplication()).getUser() == null) {
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.main_activity_fragment);
            if (navHostFragment != null) {
                navHostFragment.getNavController().navigate(R.id.action_welcomeFragment_to_loginFragment);
            }
        }
    }


    public void onAuth(User u) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(getString(R.string.USER_TYPE_SP_KEY), u.getType());
        //editor.putString(getString(R.string.USER_FIRST_NAME_SP_KEY), u.getFirstName());
        //editor.putString(getString(R.string.USER_lAST_NAME_SP_KEY), u.getLastName());
        editor.putString(getString(R.string.USER_PHONE_NUMBER_SP_KEY), u.getPhoneNumber());

        editor.apply();

        ((MyApplication)getApplication()).setUser(u);

        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    public void onUnAuth(Boolean b) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        ((MyApplication)getApplication()).setUser(null);

    }


}




