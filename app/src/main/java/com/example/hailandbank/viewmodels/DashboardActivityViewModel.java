package com.example.hailandbank.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hailandbank.api.Api;
import com.example.hailandbank.api.Result;
import com.example.hailandbank.api.UserApi;
import com.example.hailandbank.models.Account;
import com.example.hailandbank.models.Customer;
import com.example.hailandbank.models.Merchant;
import com.example.hailandbank.models.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivityViewModel extends ViewModel {

    private final MutableLiveData<User> user = new MutableLiveData<>();

    private final MutableLiveData<Boolean> reload = new MutableLiveData<>();

    private final MutableLiveData<Integer> errorResult = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<Boolean> getReload() {
        return reload;
    }

    public MutableLiveData<Integer> getErrorResult() {
        return errorResult;
    }

    public void putUser(User u) {
        user.setValue(u);
    }

    public void putReload(Boolean b) {
        reload.setValue(b);
    }

    public void updateUserAccountBalance(double amount) {
        if (user.getValue() == null) return;
        User u = user.getValue();
        Account a = u.getAccount(0);
        a.setBalance(a.getBalance()+amount);
        List<Account> list = new ArrayList<>();
        list.add(a);
        u.setAccounts(list);
        user.setValue(u);
    }

    public void fetchUser(String token, String type) {

        UserApi service = Api.getConnection(token).create(UserApi.class);

        if (type.equals(User.TYPE_CUSTOMER)) {
            Call<Result.Success<Customer>> call;
            call = service.fetchCustomer();
            call.enqueue(customerCallback);
        } else {
            Call<Result.Success<Merchant>> call;
            call = service.fetchMerchant();
            call.enqueue(merchantCallback);
        }

    }

    private final Callback<Result.Success<Customer>> customerCallback = new Callback<Result.Success<Customer>>() {
        @Override
        public void onResponse(@NotNull Call<Result.Success<Customer>> call, @NotNull Response<Result.Success<Customer>> response) {
            if (response.isSuccessful() && response.body() != null) {
                Result.Success<Customer> r = response.body();
                user.setValue(r.getData());
            } else if (response.code() == 401) {
                errorResult.setValue(401);
            } else {
                errorResult.setValue(500);
            }
        }

        @Override
        public void onFailure(@NotNull Call<Result.Success<Customer>> call, @NotNull Throwable t) {
            errorResult.setValue(500);
        }
    };

    private final Callback<Result.Success<Merchant>> merchantCallback = new Callback<Result.Success<Merchant>>() {
        @Override
        public void onResponse(@NotNull Call<Result.Success<Merchant>> call, @NotNull Response<Result.Success<Merchant>> response) {
            if (response.isSuccessful() && response.body() != null) {
                Result.Success<Merchant> r = response.body();
                user.setValue(r.getData());
            } else if (response.code() == 401) {
                errorResult.setValue(401);
            } else {
                errorResult.setValue(500);
            }
        }

        @Override
        public void onFailure(@NotNull Call<Result.Success<Merchant>> call, @NotNull Throwable t) {
            errorResult.setValue(500);
        }
    };

    public static String merchantStatus(Merchant m) {
        if (m.getStatus().equals(Merchant.STATUS_INACTIVE) && m.getAddressStreet() != null && m.getName() != null) {
            return Merchant.STATUS_ACTIVE;
        } else {
            return Merchant.STATUS_INACTIVE;
        }
    }

}


