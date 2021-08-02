package com.example.hailandbank.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hailandbank.R;

public class CheckOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);


        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(null);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView toolbarText1 = findViewById(R.id.toolbar_text1);
        TextView toolbarText2 = findViewById(R.id.toolbar_text2);
        TextView toolbarText3 = findViewById(R.id.toolbar_text3);
        toolbarText1.setText(R.string.check_out);
        toolbarText2.setVisibility(View.GONE);
        toolbarText3.setVisibility(View.GONE);


    }




}


