package com.example.hailandbank.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hailandbank.R;
import com.example.hailandbank.utils.MyApplication;


public class LogOutDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.confirm_log_out_msg))
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {
                    NavHostFragment.findNavController(LogOutDialogFragment.this)
                            .navigate(R.id.action_logOutDialogFragment_to_mainActivity);

                    ((MyApplication)requireActivity().getApplication()).setUser(null);

                    requireActivity().finish();
                })
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.dismiss())
                .create();
    }


}



