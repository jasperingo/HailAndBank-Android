package com.example.hailandbank.ui;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hailandbank.R;
import com.example.hailandbank.models.User;
import com.example.hailandbank.utils.MyApplication;


public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        LinearLayout ll = findViewById(R.id.splash_screen_1);

        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.splash_screen);
        set.setTarget(ll);
        set.start();

    }

    @Override
    public void onResume() {
        super.onResume();

        new Thread(() -> {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            runOnUiThread(this::endSplash);

        }).start();
    }


    private void endSplash() {

        Intent intent;

        User user = ((MyApplication) getApplication()).getUser();

        if (user == null || user.getAuthToken() == null) {

            setUpUser();

            intent = new Intent(this, MainActivity.class);

        } else {
            intent = new Intent(this, DashboardActivity.class);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        else
            startActivity(intent);

        finish();
    }

    private void setUpUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(
                getString(R.string.USER_SP_KEY),  Context.MODE_PRIVATE);

        if (!sharedPreferences.getAll().isEmpty()) {

            String type = sharedPreferences.getString(getString(R.string.USER_TYPE_SP_KEY), null);
            //String firstName = sharedPreferences.getString(getString(R.string.USER_FIRST_NAME_SP_KEY), null);
            //String lastName = sharedPreferences.getString(getString(R.string.USER_lAST_NAME_SP_KEY), null);
            String phoneNumber = sharedPreferences.getString(getString(R.string.USER_PHONE_NUMBER_SP_KEY), null);

            User u = new User();
            u.setType(type);
            //u.setFirstName(firstName);
            //u.setLastName(lastName);
            u.setPhoneNumber(phoneNumber);

            ((MyApplication)getApplication()).setUser(u);
        }
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, R.string.chill, Toast.LENGTH_SHORT).show();
    }


}



