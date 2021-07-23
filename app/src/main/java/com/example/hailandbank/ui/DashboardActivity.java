package com.example.hailandbank.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.hailandbank.R;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity {

    private DrawerLayout mDrawer;

    private NavigationView drawerNav;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.main_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawer = findViewById(R.id.drawer_view);

        drawerNav = findViewById(R.id.drawer_nav);

        //drawerNav.getMenu().getItem(1).setChecked(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}




