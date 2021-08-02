package com.example.hailandbank.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hailandbank.R;
import com.example.hailandbank.api.Result;
import com.example.hailandbank.models.AuthToken;
import com.example.hailandbank.models.User;
import com.example.hailandbank.utils.MyApplication;
import com.example.hailandbank.viewmodels.LoginViewModel;

import org.jetbrains.annotations.NotNull;


public class LoginFragment extends AuthFragment implements View.OnClickListener {

    private LoginViewModel viewModel;


    public LoginFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        viewModel.getErrorResult().observe(getViewLifecycleOwner(), this::onLoginError);

        viewModel.getInputErrorResult().observe(getViewLifecycleOwner(), this::onInputResult);

        viewModel.getSuccessResult().observe(getViewLifecycleOwner(), this::onSuccessResult);

        submitButton.setOnClickListener(this);
        switchButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_switch) {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_signUpFragment);
        }

        if (v.getId() == R.id.button_submit) {
            if (((MyApplication)requireActivity().getApplication()).isOnline()) {

                viewModel.validateData(
                    phoneNumberEditText.getText().toString(),
                    pinEditText.getText().toString()
                );

            } else {
                Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showLoading(boolean isLoading) {
        callback.setEnabled(isLoading);
        phoneNumberEditText.setEnabled(!isLoading);
        pinEditText.setEnabled(!isLoading);
        submitButton.setEnabled(!isLoading);
        switchButton.setEnabled(!isLoading);
        customerRadioButton.setEnabled(!isLoading);
        merchantRadioButton.setEnabled(!isLoading);
        loadingProgressBar.setVisibility(isLoading? View.VISIBLE : View.GONE);
    }

    public void onLoginError(Result.Error<Void> error) {
        showLoading(false);
        Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void onInputResult(Boolean hasError) {
        if (!hasError) {
            showLoading(true);
            viewModel.signInUser(
                getAccountType(),
                phoneNumberEditText.getText().toString(),
                pinEditText.getText().toString()
            );
        } else {
            showLoading(false);
            Toast.makeText(requireContext(), getString(R.string.credentials_incorrect), Toast.LENGTH_LONG).show();
        }
    }

    public void onSuccessResult(Result.Success<AuthToken> result) {

        User user = new User();
        user.setType(getAccountType());
        user.setPhoneNumber(phoneNumberEditText.getText().toString());
        user.setAuthToken(result.getData());
        mainActivityViewModel.putUser(user);
    }




}