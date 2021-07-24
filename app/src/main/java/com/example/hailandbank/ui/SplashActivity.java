package com.example.hailandbank.ui;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hailandbank.R;


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

            runOnUiThread(() -> {

                Intent intent = new Intent(this, DashboardActivity.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                else
                    startActivity(intent);

                finish();

            });

        }).start();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, R.string.chill, Toast.LENGTH_SHORT).show();
    }


}



