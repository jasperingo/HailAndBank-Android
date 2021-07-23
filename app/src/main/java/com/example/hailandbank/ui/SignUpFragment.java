package com.example.hailandbank.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hailandbank.R;
import com.example.hailandbank.viewmodels.SignUpViewModel;

import org.jetbrains.annotations.NotNull;


public class SignUpFragment extends Fragment implements View.OnClickListener {

    private SignUpViewModel viewModel;

    private ProgressBar loadingProgressBar;

    private Button signUpButton, gotoSignIn;

    private RadioButton customerRadioButton, merchantRadioButton;

    private EditText firstNameEditText, lastNameEditText, phoneNumberEditText, pinEditText;

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

        viewModel.getFormData().observe(getViewLifecycleOwner(), formDataObserver);
        viewModel.getSignUpResult().observe(getViewLifecycleOwner(), signUpResultObserver);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        customerRadioButton = view.findViewById(R.id.radio_button_customer);
        merchantRadioButton = view.findViewById(R.id.radio_button_merchant);

        firstNameEditText = view.findViewById(R.id.edit_text_first_name);
        lastNameEditText = view.findViewById(R.id.edit_text_last_name);
        phoneNumberEditText = view.findViewById(R.id.edit_text_phone_number);
        pinEditText = view.findViewById(R.id.edit_text_pin);

        firstNameErrorTextView = view.findViewById(R.id.edit_text_first_name_error);
        lastNameErrorTextView = view.findViewById(R.id.edit_text_last_name_error);
        phoneNumberErrorTextView = view.findViewById(R.id.edit_text_phone_number_error);
        pinErrorTextView = view.findViewById(R.id.edit_text_pin_error);

        loadingProgressBar = view.findViewById(R.id.loading);

        signUpButton = view.findViewById(R.id.button_submit);
        gotoSignIn = view.findViewById(R.id.button_have_an_account);

        signUpButton.setOnClickListener(this);
        gotoSignIn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_have_an_account) {
            NavHostFragment.findNavController(this).navigate(R.id.action_signUpFragment_to_loginFragment);
        }

        if (v.getId() == R.id.button_submit) {
            resetInputErrors();
            viewModel.validateData(
                    firstNameEditText.getText().toString(),
                    lastNameEditText.getText().toString(),
                    phoneNumberEditText.getText().toString(),
                    pinEditText.getText().toString()
            );
        }

    }

    private String getAccountType() {
        return merchantRadioButton.isChecked() ?
            getString(R.string.merchant).toLowerCase() : getString(R.string.customer).toLowerCase();
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
        signUpButton.setEnabled(!isLoading);
        gotoSignIn.setEnabled(!isLoading);
        customerRadioButton.setEnabled(!isLoading);
        merchantRadioButton.setEnabled(!isLoading);
        loadingProgressBar.setVisibility(isLoading? View.VISIBLE : View.GONE);
    }

    private final OnBackPressedCallback callback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            Toast.makeText(requireActivity(), R.string.signing_up_back_pressed, Toast.LENGTH_LONG).show();
        }
    };

    private final Observer<SignUpViewModel.SignUpResult> signUpResultObserver = new Observer<SignUpViewModel.SignUpResult>() {
        @Override
        public void onChanged(SignUpViewModel.SignUpResult signUpResult) {
            showLoading(false);
            Toast.makeText(requireContext(), "Signed up", Toast.LENGTH_LONG).show();
        }
    };

    private final Observer<SignUpViewModel.FormData> formDataObserver = new Observer<SignUpViewModel.FormData>() {
        @Override
        public void onChanged(SignUpViewModel.FormData formData) {

            if (!formData.isValid()) {

                if (!formData.isFirstName()) firstNameErrorTextView.setVisibility(View.VISIBLE);

                if (!formData.isLastName()) lastNameErrorTextView.setVisibility(View.VISIBLE);

                if (!formData.isPhoneNumber()) phoneNumberErrorTextView.setVisibility(View.VISIBLE);

                if (!formData.isPin()) pinErrorTextView.setVisibility(View.VISIBLE);

            } else {

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
    };


}
















