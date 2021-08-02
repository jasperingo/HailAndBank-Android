package com.example.hailandbank.ui;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.hailandbank.R;
import com.example.hailandbank.models.User;
import com.example.hailandbank.utils.MyApplication;
import com.example.hailandbank.viewmodels.DashboardActivityViewModel;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;


public class DashboardActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener {

    private DrawerLayout mDrawer;

    private NavigationView drawerNav;

    private TextView toolbarText1, toolbarText2, toolbarText3;

    private DashboardActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        viewModel = new ViewModelProvider(this).get(DashboardActivityViewModel.class);

        viewModel.getReload().observe(this, this::onReloadData);

        viewModel.getUser().observe(this, this::onUserLoaded);

        viewModel.getErrorResult().observe(this, this::onErrorResult);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(null);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        toolbarText1 = findViewById(R.id.toolbar_text1);
        toolbarText2 = findViewById(R.id.toolbar_text2);
        toolbarText3 = findViewById(R.id.toolbar_text3);

        mDrawer = findViewById(R.id.drawer_view);

        drawerNav = findViewById(R.id.drawer_nav);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dashboard_activity_fragment);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(drawerNav, navController);
            navController.addOnDestinationChangedListener(this);
        }

        hideDrawerItems();

        getUserData();
    }


    private void hideDrawerItems() {
        if (((MyApplication)getApplication()).getUser().getType().equals(User.TYPE_CUSTOMER)) {
            Menu navMenu = drawerNav.getMenu();
            navMenu.findItem(R.id.pendingOrdersFragment).setVisible(false);
            navMenu.findItem(R.id.customerOrdersFragment).setVisible(false);
        }
    }

    private void onErrorResult(Integer integer) {

        if (integer == 401) {

            ((MyApplication)getApplication()).getUser().setAuthToken(null);

            Toast.makeText(this, getString(R.string.session_expired), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);

            finish();
        }
    }

    private void onUserLoaded(User user) {
        User user1 = ((MyApplication)getApplication()).getUser();
        user.setAuthToken(user1.getAuthToken());
        ((MyApplication)getApplication()).setUser(user);
    }

    private void getUserData() {
        User user = ((MyApplication) getApplication()).getUser();

        if (((MyApplication) getApplication()).isOnline()) {
           viewModel.fetchUser(user.getAuthToken().getToken(), user.getType());
        } else {
            viewModel.getErrorResult().setValue(500);
        }
    }

    public void onReloadData(Boolean b) {
        if (b) {
            getUserData();
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

        if (destination.getId() == R.id.profileFragment) {
            toolbarText1.setText(R.string.profile);
        } else if (destination.getId() == R.id.transactionsFragment) {
            toolbarText1.setText(R.string.transactions);
        } else if (destination.getId() == R.id.ordersFragment) {
            toolbarText1.setText(R.string.orders);
        } else if (destination.getId() == R.id.customerOrdersFragment) {
            toolbarText1.setText(R.string.customer_orders);
        } else {
            toolbarText1.setText(R.string.hail);
            toolbarText2.setVisibility(View.VISIBLE);
            toolbarText3.setVisibility(View.VISIBLE);
        }

    }





}




