package com.example.hailandbank.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.hailandbank.R;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class DashboardActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private DrawerLayout mDrawer;

    private TextView toolbarText1, toolbarText2, toolbarText3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.main_toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle(null);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        toolbarText1 = findViewById(R.id.toolbar_text1);
        toolbarText2 = findViewById(R.id.toolbar_text2);
        toolbarText3 = findViewById(R.id.toolbar_text3);

        mDrawer = findViewById(R.id.drawer_view);

        NavigationView drawerNav = findViewById(R.id.drawer_nav);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dashboard_activity_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(drawerNav, navController);
            navController.addOnDestinationChangedListener(this);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {

        toolbarText2.setVisibility(View.GONE);
        toolbarText3.setVisibility(View.GONE);

        if (destination.getId() == R.id.dashboardFragment) {
            toolbarText1.setText(R.string.hail);
            toolbarText2.setVisibility(View.VISIBLE);
            toolbarText3.setVisibility(View.VISIBLE);
        } else if (destination.getId() == R.id.profileFragment) {
            toolbarText1.setText(R.string.profile);
        }

    }


}





