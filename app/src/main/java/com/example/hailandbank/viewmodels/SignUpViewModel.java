package com.example.hailandbank.viewmodels;

import android.util.Log;

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
import com.example.hailandbank.utils.InputData;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUpViewModel extends ViewModel implements Callback<Result.Success<AuthToken>> {


    private final MutableLiveData<Result.Success<AuthToken>> successResult = new MutableLiveData<>();

    private final MutableLiveData<Result.Success<Map<String, InputData>>> inputErrorResult = new MutableLiveData<>();

    private final MutableLiveData<Result.Error<Void>>  errorResult = new MutableLiveData<>();


    public LiveData<Result.Success<AuthToken>> getSuccessResult() {
        return successResult;
    }

    public LiveData<Result.Success<Map<String, InputData>>> getInputErrorResult() {
        return inputErrorResult;
    }

    public LiveData<Result.Error<Void>> getErrorResult() {
        return errorResult;
    }


    public void validateData(String firstName, String lastName, String phoneNumber, String pin) {

        Result.Success<Map<String, InputData>> data = new Result.Success<>();

        data.setData(new HashMap<>());

        if (firstName == null || firstName.trim().isEmpty()) {
            data.getData().put("first_name", InputData.with(firstName, "Invalid"));
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            data.getData().put("last_name", InputData.with(lastName, "Invalid"));
        }

        if (phoneNumber == null || phoneNumber.length() != 11) {
            data.getData().put("phone_number", InputData.with(phoneNumber, "Invalid"));
        }

        if (pin == null || !Pattern.matches("\\d{4}", pin)) {
            data.getData().put("pin", InputData.with(pin, "Invalid"));
        }

        inputErrorResult.setValue(data);
    }

    public void signUpUser(String type, String firstName, String lastName, String phoneNumber, String pin) {

        UserApi service = Api.getConnection().create(UserApi.class);

        Call<Result.Success<AuthToken>> call;

        if (type.equals(User.TYPE_CUSTOMER)) {
            Customer user = new Customer();
            user.setType(type);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            user.setPin(pin);
            call = service.customerSignUp(user);
        } else {
            Merchant user = new Merchant();
            user.setType(type);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhoneNumber(phoneNumber);
            user.setPin(pin);
            call = service.merchantSignUp(user);
        }

        call.enqueue(this);
    }

    @Override
    public void onResponse(@NotNull Call<Result.Success<AuthToken>> call, @NotNull Response<Result.Success<AuthToken>> response) {

        if (response.isSuccessful() && response.body() != null) {
            Result.Success<AuthToken> r = response.body();
            successResult.setValue(r);
        } else if (response.code() == 400) {
            Result.Success<Map<String, InputData>> r = ErrorParser.parseInputData(response);
            inputErrorResult.setValue(r);
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


