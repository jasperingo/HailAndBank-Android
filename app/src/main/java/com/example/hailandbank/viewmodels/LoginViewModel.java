package com.example.hailandbank.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hailandbank.api.Api;
import com.example.hailandbank.api.ErrorParser;
import com.example.hailandbank.api.Result;
import com.example.hailandbank.api.UserApi;
import com.example.hailandbank.models.AuthToken;
import com.example.hailandbank.models.Customer;
import com.example.hailandbank.models.Merchant;
import com.example.hailandbank.models.User;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginViewModel extends ViewModel implements Callback<Result.Success<AuthToken>> {


    private final MutableLiveData<Result.Success<AuthToken>> successResult = new MutableLiveData<>();

    private final MutableLiveData<Result.Error<Void>>  errorResult = new MutableLiveData<>();

    private final MutableLiveData<Boolean> inputErrorResult = new MutableLiveData<>();


    public LiveData<Result.Success<AuthToken>> getSuccessResult() {
        return successResult;
    }

    public LiveData<Result.Error<Void>> getErrorResult() {
        return errorResult;
    }

    public MutableLiveData<Boolean> getInputErrorResult() {
        return inputErrorResult;
    }

    public void validateData(String phoneNumber, String pin) {

        if (phoneNumber == null || phoneNumber.length() != 11 ||
                pin == null || !Pattern.matches("\\d{4}", pin)) {
            inputErrorResult.setValue(true);
        } else {
            inputErrorResult.setValue(false);
        }

    }

    public void signInUser(String type, String phoneNumber, String pin) {

        UserApi service = Api.getConnection().create(UserApi.class);

        Call<Result.Success<AuthToken>> call;

        if (type.equals(User.TYPE_CUSTOMER)) {
            Customer user = new Customer();
            user.setType(type);
            user.setPhoneNumber(phoneNumber);
            user.setPin(pin);
            call = service.customerSignIn(user);
        } else {
            Merchant user = new Merchant();
            user.setType(type);
            user.setPhoneNumber(phoneNumber);
            user.setPin(pin);
            call = service.merchantSignIn(user);
        }

        call.enqueue(this);
    }

    @Override
    public void onResponse(@NotNull Call<Result.Success<AuthToken>> call, @NotNull Response<Result.Success<AuthToken>> response) {

        if (response.isSuccessful() && response.body() != null) {
            Result.Success<AuthToken> r;
            r = response.body();
            successResult.setValue(r);
        } else if (response.code() == 400) {
            inputErrorResult.setValue(true);
        } else {
            Result.Error<Void> r = ErrorParser.parseError(response);
            errorResult.setValue(r);
        }
    }

    @Override
    public void onFailure(@NotNull Call<Result.Success<AuthToken>> call, @NotNull Throwable t) {
        Result.Error<Void> error = new Result.Error<>(new IOException(t));
        errorResult.setValue(error);
    }


}


