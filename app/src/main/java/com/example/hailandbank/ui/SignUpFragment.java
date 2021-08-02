package com.example.hailandbank.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hailandbank.R;
import com.example.hailandbank.api.Result;
import com.example.hailandbank.models.AuthToken;
import com.example.hailandbank.models.User;
import com.example.hailandbank.utils.InputData;
import com.example.hailandbank.utils.MyApplication;
import com.example.hailandbank.viewmodels.SignUpViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class SignUpFragment extends AuthFragment implements View.OnClickListener {

    private SignUpViewModel viewModel;

    private EditText firstNameEditText, lastNameEditText;

    private TextView firstNameErrorTextView, lastNameErrorTextView, phoneNumberErrorTextView, pinErrorTextView;


    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

        viewModel.getSuccessResult().observe(getViewLifecycleOwner(), this::successResultObserver);
        viewModel.getInputErrorResult().observe(getViewLifecycleOwner(), this::inputErrorResultObserver);
        viewModel.getErrorResult().observe(getViewLifecycleOwner(), this::errorResultObserver);

        firstNameEditText = view.findViewById(R.id.edit_text_first_name);
        lastNameEditText = view.findViewById(R.id.edit_text_last_name);

        firstNameErrorTextView = view.findViewById(R.id.edit_text_first_name_error);
        lastNameErrorTextView = view.findViewById(R.id.edit_text_last_name_error);
        phoneNumberErrorTextView = view.findViewById(R.id.edit_text_phone_number_error);
        pinErrorTextView = view.findViewById(R.id.edit_text_pin_error);

        submitButton.setOnClickListener(this);
        switchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_switch) {
            NavHostFragment.findNavController(this).navigate(R.id.action_signUpFragment_to_loginFragment);
        }

        if (v.getId() == R.id.button_submit) {
            if (((MyApplication)requireActivity().getApplication()).isOnline()) {
                resetInputErrors();
                viewModel.validateData(
                    firstNameEditText.getText().toString(),
                    lastNameEditText.getText().toString(),
                    phoneNumberEditText.getText().toString(),
                    pinEditText.getText().toString()
                );
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
            
        }

    }

    private void resetInputErrors() {
        firstNameErrorTextView.setVisibility(View.GONE);
        lastNameErrorTextView.setVisibility(View.GONE);
        phoneNumberErrorTextView.setVisibility(View.GONE);
        pinErrorTextView.setVisibility(View.GONE);
    }

    private void showLoading(boolean isLoading) {
        callback.setEnabled(isLoading);
        firstNameEditText.setEnabled(!isLoading);
        lastNameEditText.setEnabled(!isLoading);
        phoneNumberEditText.setEnabled(!isLoading);
        pinEditText.setEnabled(!isLoading);
        submitButton.setEnabled(!isLoading);
        switchButton.setEnabled(!isLoading);
        customerRadioButton.setEnabled(!isLoading);
        merchantRadioButton.setEnabled(!isLoading);
        loadingProgressBar.setVisibility(isLoading? View.VISIBLE : View.GONE);
    }

    public void successResultObserver(Result.Success<AuthToken> result) {

        User user = new User();
        user.setType(getAccountType());
        user.setPhoneNumber(phoneNumberEditText.getText().toString());
        user.setAuthToken(result.getData());
        mainActivityViewModel.putUser(user);
    }

    public void inputErrorResultObserver(Result.Success<Map<String, InputData>> result) {
        showLoading(false);

        Map<String, InputData> data = result.getData();

        if (data.containsKey("first_name")) firstNameErrorTextView.setVisibility(View.VISIBLE);

        if (data.containsKey("last_name")) lastNameErrorTextView.setVisibility(View.VISIBLE);

        if (data.containsKey("phone_number")) phoneNumberErrorTextView.setVisibility(View.VISIBLE);

        if (data.containsKey("pin")) {
            pinErrorTextView.setVisibility(View.VISIBLE);
            if (!data.containsKey("phone_number"))
                phoneNumberErrorTextView.setVisibility(View.INVISIBLE);
        }

        if (data.isEmpty()) {
            showLoading(true);

            viewModel.signUpUser(
                    getAccountType(),
                    firstNameEditText.getText().toString(),
                    lastNameEditText.getText().toString(),
                    phoneNumberEditText.getText().toString(),
                    pinEditText.getText().toString()
            );
        }
    }

    public void errorResultObserver(Result.Error<Void> result) {
        showLoading(false);
        Toast.makeText(requireContext(), result.getMessage(), Toast.LENGTH_LONG).show();
    }


}






