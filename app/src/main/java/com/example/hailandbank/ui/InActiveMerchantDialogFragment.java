package com.example.hailandbank.ui;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hailandbank.R;


public class InActiveMerchantDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.inactive_merchant_prompt))
                .setPositiveButton(getString(R.string.complete), (dialog, which) -> {
                    if (getParentFragment() != null) {
                        NavHostFragment.findNavController(getParentFragment())
                                .navigate(R.id.action_inActiveMerchantDialogFragment_to_profileFragment);
                    }
                })
                .setNegativeButton(getString(R.string.later), (dialog, which) -> dialog.dismiss())
                .create();
    }

}

