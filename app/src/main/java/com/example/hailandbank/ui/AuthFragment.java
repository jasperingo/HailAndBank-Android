package com.example.hailandbank.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hailandbank.R;
import com.example.hailandbank.viewmodels.MainActivityViewModel;

import org.jetbrains.annotations.NotNull;


public class AuthFragment extends Fragment {

    protected MainActivityViewModel mainActivityViewModel;

    protected EditText phoneNumberEditText;
    protected EditText pinEditText;
    protected Button submitButton;
    protected Button switchButton;
    protected ProgressBar loadingProgressBar;

    protected RadioButton customerRadioButton, merchantRadioButton;


    protected final OnBackPressedCallback callback = new OnBackPressedCallback(false) {
        @Override
        public void handleOnBackPressed() {
            Toast.makeText(requireActivity(), R.string.please_wait, Toast.LENGTH_LONG).show();
        }
    };


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        customerRadioButton = view.findViewById(R.id.radio_button_customer);
        merchantRadioButton = view.findViewById(R.id.radio_button_merchant);

        phoneNumberEditText = view.findViewById(R.id.edit_text_phone_number);
        pinEditText = view.findViewById(R.id.edit_text_pin);

        submitButton = view.findViewById(R.id.button_submit);
        switchButton = view.findViewById(R.id.button_switch);

        loadingProgressBar = view.findViewById(R.id.loading);

    }

    protected String getAccountType() {
        return merchantRadioButton.isChecked() ?
                getString(R.string.merchant).toLowerCase() : getString(R.string.customer).toLowerCase();
    }



}

