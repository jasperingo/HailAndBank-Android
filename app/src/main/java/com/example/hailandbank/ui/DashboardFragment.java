package com.example.hailandbank.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.hailandbank.R;
import com.example.hailandbank.api.Api;
import com.example.hailandbank.api.ErrorParser;
import com.example.hailandbank.api.Result;
import com.example.hailandbank.api.TransactionApi;
import com.example.hailandbank.models.Account;
import com.example.hailandbank.models.Merchant;
import com.example.hailandbank.models.Transaction;
import com.example.hailandbank.models.User;
import com.example.hailandbank.utils.InputData;
import com.example.hailandbank.utils.MyApplication;
import com.example.hailandbank.viewmodels.DashboardActivityViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardFragment extends Fragment implements View.OnClickListener {

    private DashboardActivityViewModel dashboardActivityViewModel;

    private ConstraintLayout topView, progressBar, reloadView;

    private TextView accountBalanceTextView, accountNumberTextView, ourServicesTextView;

    private Button withdrawButton, cashDeliveryServiceButton, cashPickUpButton;


    public DashboardFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dashboardActivityViewModel = new ViewModelProvider(requireActivity()).get(DashboardActivityViewModel.class);

        dashboardActivityViewModel.getUser().observe(getViewLifecycleOwner(), this::onUserLoaded);
        dashboardActivityViewModel.getErrorResult().observe(getViewLifecycleOwner(), this::onErrorResult);

        withdrawButton = view.findViewById(R.id.withdraw_button);
        withdrawButton.setOnClickListener(this);

        Button fundButton = view.findViewById(R.id.fund_button);
        fundButton.setOnClickListener(this);

        Button ordersButton = view.findViewById(R.id.order_notification_button);
        ordersButton.setOnClickListener(this);

        Button customerOrdersButton = view.findViewById(R.id.customer_order_notification_button);
        customerOrdersButton.setOnClickListener(this);

        cashDeliveryServiceButton = view.findViewById(R.id.cash_delivery_service_button);
        cashDeliveryServiceButton.setOnClickListener(this);

        cashPickUpButton = view.findViewById(R.id.cash_pick_up_service_button);
        cashPickUpButton.setOnClickListener(this);


        topView = view.findViewById(R.id.dash_board_top);

        accountBalanceTextView = view.findViewById(R.id.account_balance_text_view);

        accountNumberTextView = view.findViewById(R.id.account_number_text_view);

        ourServicesTextView = view.findViewById(R.id.our_services_text_view);

        progressBar = view.findViewById(R.id.progress_view);

        reloadView = view.findViewById(R.id.reload_view);

        Button reloadButton = view.findViewById(R.id.reload_button);

        reloadButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Bundle b = new Bundle();
        User u = dashboardActivityViewModel.getUser().getValue();
        if (u != null) b.putString("token", u.getAuthToken().getToken());

        if (v.getId() == R.id.withdraw_button) {

            if (((Merchant)dashboardActivityViewModel.getUser().getValue()).getStatus().equals(Merchant.STATUS_INACTIVE)) {
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_inActiveMerchantDialogFragment);
            } else if (((Merchant)dashboardActivityViewModel.getUser().getValue()).getSettlementAccount(0) == null) {
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_noSettlementAccountDialogFragment);
            } else {
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_withdrawDialogFragment, b);
            }

        } else if (v.getId() == R.id.fund_button) {

            NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_depositDialogFragment, b);

        } else if (v.getId() == R.id.order_notification_button) {

            NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_ordersFragment);

        } else if (v.getId() == R.id.customer_order_notification_button) {

            NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_customerOrdersFragment);

        } else if (v.getId() == R.id.cash_delivery_service_button || v.getId() == R.id.cash_pick_up_service_button) {

            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_dashboardFragment_to_checkOutActivity);

        } else if (v.getId() == R.id.reload_button) {
            reloadData();
        }

    }

    private void reloadData() {
        reloadView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        dashboardActivityViewModel.putReload(true);
    }

    private void onErrorResult(Integer integer) {
        progressBar.setVisibility(View.GONE);
        reloadView.setVisibility(View.VISIBLE);
    }

    public void onUserLoaded(User u) {

        Account account = u.getAccount(0);

        accountBalanceTextView.setText(
            String.format(getString(R.string.__amount), account.getBalance()
        ));
        accountNumberTextView.setText(account.getNumber());

        if (u.getType().equals(User.TYPE_MERCHANT))
            withdrawButton.setVisibility(View.VISIBLE);

        topView.setVisibility(View.VISIBLE);
        ourServicesTextView.setVisibility(View.VISIBLE);
        cashDeliveryServiceButton.setVisibility(View.VISIBLE);
        cashPickUpButton.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);

    }


    public static class NoSettlementAccountDialogFragment extends DialogFragment {

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            return new AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.no_settlement_account_prompt))
                    .setPositiveButton(getString(R.string.add), (dialog, which) -> {
                        if (getParentFragment() != null) {
                            NavHostFragment.findNavController(getParentFragment())
                                    .navigate(R.id.action_noSettlementAccountDialogFragment_to_profileFragment);
                        }
                    })
                    .setNegativeButton(getString(R.string.later), (dialog, which) -> dialog.dismiss())
                    .create();
        }

    }


    public static class WithdrawDialogFragment extends DialogFragment
            implements DialogInterface.OnShowListener, View.OnClickListener, Callback<Result.Success<Transaction>> {

        private ProgressBar progressBar;

        private EditText amountEditText;

        private Button okButton, badButton;

        private DashboardActivityViewModel viewModel;

        private double balance=0;


        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog a = new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.withdraw)
                    .setView(R.layout.create_transaction)
                    .setPositiveButton(getString(R.string.withdraw), null)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .create();

            a.setOnShowListener(this);

            return a;
        }


        @Override
        public void onShow(DialogInterface dialog) {

            viewModel = new ViewModelProvider(requireParentFragment().requireActivity()).get(DashboardActivityViewModel.class);

            if (viewModel.getUser().getValue() != null)
                balance = viewModel.getUser().getValue().getAccount(0).getBalance();

            ((Dialog) dialog).setCanceledOnTouchOutside(false);
            okButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
            okButton.setOnClickListener(this);
            badButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
            amountEditText = ((Dialog) dialog).findViewById(R.id.amount_edit_text);
            progressBar = ((Dialog) dialog).findViewById(R.id.loading);

        }


        @Override
        public void onClick(View v) {

            try {
                double amount = Double.parseDouble(amountEditText.getText().toString());

                if (amount > balance)
                    Toast.makeText(requireContext(), getString(R.string.withdraw_amount_maximum_invalid, balance), Toast.LENGTH_SHORT).show();
                else if (amount < 1000)
                    throw new NumberFormatException();
                else
                    sendRequest(amount);

            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), getString(R.string.withdraw_amount_minimum_invalid), Toast.LENGTH_SHORT).show();
            }

        }

        private void showLoading(boolean isLoading) {
            okButton.setEnabled(!isLoading);
            badButton.setEnabled(!isLoading);
            amountEditText.setEnabled(!isLoading);
            progressBar.setVisibility(isLoading? View.VISIBLE : View.GONE);
        }

        private void sendRequest(double amount) {

            if (getArguments() == null) return;

            if (((MyApplication)requireActivity().getApplication()).isOnline() && viewModel.getUser().getValue() != null) {
                showLoading(true);

                Transaction t = new Transaction();
                t.setAmount(amount);
                t.setAccount(viewModel.getUser().getValue().getAccount(0));

                TransactionApi service = Api.getConnection(getArguments().getString("token")).create(TransactionApi.class);

                Call<Result.Success<Transaction>> call = service.withdraw(t);

                call.enqueue(this);
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public void onResponse(@NotNull Call<Result.Success<Transaction>> call, @NotNull Response<Result.Success<Transaction>> response) {
            showLoading(false);

            if (response.isSuccessful() && response.body() != null) {

                Result.Success<Transaction> r = response.body();
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();

                viewModel.updateUserAccountBalance(r.getData().getAmount());

                if (getDialog() != null) getDialog().dismiss();

            } else if (response.code() == 400) {

                Result.Success<Map<String, InputData>> r = ErrorParser.parseInputData(response);
                //Map<String, InputData> data = r.getData();
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();

            } else if (response.code() == 401) {
                viewModel.getErrorResult().setValue(401);
            } else {

                Result.Error<Void> r = ErrorParser.parseError(response);
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(@NotNull Call<Result.Success<Transaction>> call, @NotNull Throwable t) {
            showLoading(false);
            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }


    public static class DepositDialogFragment extends DialogFragment
            implements DialogInterface.OnShowListener, View.OnClickListener, Callback<Result.Success<Transaction>> {

        private ProgressBar progressBar;

        private EditText amountEditText;

        private Button okButton, badButton;

        private DashboardActivityViewModel viewModel;


        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog a = new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.fund_account)
                    .setView(R.layout.create_transaction)
                    .setPositiveButton(getString(R.string.fund), null)
                    .setNegativeButton(getString(R.string.cancel), null)
                    .create();

            a.setOnShowListener(this);

            return a;
        }


        @Override
        public void onShow(DialogInterface dialog) {

            viewModel = new ViewModelProvider(requireParentFragment().requireActivity()).get(DashboardActivityViewModel.class);

            ((Dialog) dialog).setCanceledOnTouchOutside(false);
            okButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
            okButton.setOnClickListener(this);
            badButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);
            amountEditText = ((Dialog) dialog).findViewById(R.id.amount_edit_text);
            progressBar = ((Dialog) dialog).findViewById(R.id.loading);

        }


        @Override
        public void onClick(View v) {

            try {
                double amount = Double.parseDouble(amountEditText.getText().toString());

                if (amount < 100) throw new NumberFormatException();

                sendRequest(amount);

            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), getString(R.string.fund_amount_invalid), Toast.LENGTH_SHORT).show();
            }

        }

        private void showLoading(boolean isLoading) {
            okButton.setEnabled(!isLoading);
            badButton.setEnabled(!isLoading);
            amountEditText.setEnabled(!isLoading);
            progressBar.setVisibility(isLoading? View.VISIBLE : View.GONE);
        }


        private void sendRequest(double amount) {

            if (getArguments() == null) return;

            if (((MyApplication)requireActivity().getApplication()).isOnline() && viewModel.getUser().getValue() != null) {
                showLoading(true);

                Transaction t = new Transaction();
                t.setAmount(amount);
                t.setAccount(viewModel.getUser().getValue().getAccount(0));

                TransactionApi service = Api.getConnection(getArguments().getString("token")).create(TransactionApi.class);

                Call<Result.Success<Transaction>> call = service.fund(t);

                call.enqueue(this);
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }


        @Override
        public void onResponse(@NotNull Call<Result.Success<Transaction>> call, @NotNull Response<Result.Success<Transaction>> response) {
            showLoading(false);

            if (response.isSuccessful() && response.body() != null) {

                Result.Success<Transaction> r = response.body();
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();

                viewModel.updateUserAccountBalance(r.getData().getAmount());

                if (getDialog() != null) getDialog().dismiss();

            } else if (response.code() == 400) {

                Result.Success<Map<String, InputData>> r = ErrorParser.parseInputData(response);
                //Map<String, InputData> data = r.getData();
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();

            } else if (response.code() == 401) {
                viewModel.getErrorResult().setValue(401);
            } else {

                Result.Error<Void> r = ErrorParser.parseError(response);
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(@NotNull Call<Result.Success<Transaction>> call, @NotNull Throwable t) {
            showLoading(false);
            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }




}




