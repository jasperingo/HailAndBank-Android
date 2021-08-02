package com.example.hailandbank.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.example.hailandbank.api.SettlementAccountApi;
import com.example.hailandbank.api.UserApi;
import com.example.hailandbank.models.Merchant;
import com.example.hailandbank.models.SettlementAccount;
import com.example.hailandbank.models.User;
import com.example.hailandbank.utils.InputData;
import com.example.hailandbank.utils.MyApplication;
import com.example.hailandbank.viewmodels.DashboardActivityViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private DashboardActivityViewModel dashboardActivityViewModel;

    private ConstraintLayout progressBar, reloadView;

    private  ImageView imageView;

    private TextView accountTypeTextView, fullNameLabelTextView, fullNameTextView, phoneNumberLabelTextView, phoneNumberTextView,
            merchantCodeLabelTextView, merchantCodeTextView, merchantNameLabelTextView, merchantNameTextView, merchantStatusLabelTextView, merchantStatusTextView,
            pinLabelTextView, pinTextView, addressLabelTextView, addressStreetLabelTextView,
            addressStreetTextView, addressCityLabelTextView, addressCityTextView, addressStateLabelTextView, addressStateTextView,
            settlementAccountLabelTextView, settlementAccountBankNameLabelTextView, settlementAccountBankNameTextView,
            settlementAccountNumberLabelTextView, settlementAccountNumberTextView, settlementAccountTypeLabelTextView,
            settlementAccountTypeTextView;

    private ImageButton merchantNameEditButton, pinEditButton, addressEditButton, settlementAccountEditButton;


    public ProfileFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dashboardActivityViewModel = new ViewModelProvider(requireActivity()).get(DashboardActivityViewModel.class);

        dashboardActivityViewModel.getUser().observe(getViewLifecycleOwner(), this::onUserLoaded);
        dashboardActivityViewModel.getErrorResult().observe(getViewLifecycleOwner(), this::onErrorResult);

        progressBar = view.findViewById(R.id.progress_view);

        reloadView = view.findViewById(R.id.reload_view);

        Button reloadButton = view.findViewById(R.id.reload_button);

        reloadButton.setOnClickListener(this);

        imageView =  view.findViewById(R.id.profile_image_view);

        accountTypeTextView = view.findViewById(R.id.profile_account_type_text_view);

        fullNameLabelTextView = view.findViewById(R.id.profile_full_name_label_text_view);

        fullNameTextView = view.findViewById(R.id.profile_full_name_text_view);

        phoneNumberLabelTextView = view.findViewById(R.id.profile_phone_number_label_text_view);

        phoneNumberTextView = view.findViewById(R.id.profile_phone_number_text_view);

        merchantNameLabelTextView = view.findViewById(R.id.profile_merchant_name_label_text_view);

        merchantNameTextView = view.findViewById(R.id.profile_merchant_name_text_view);

        merchantCodeLabelTextView = view.findViewById(R.id.profile_merchant_code_label_text_view);

        merchantCodeTextView = view.findViewById(R.id.profile_merchant_code_text_view);

        merchantStatusLabelTextView = view.findViewById(R.id.profile_merchant_status_label_text_view);

        merchantStatusTextView = view.findViewById(R.id.profile_merchant_status_text_view);

        addressLabelTextView = view.findViewById(R.id.profile_address_label_text_view);

        pinLabelTextView = view.findViewById(R.id.profile_pin_label_text_view);

        pinTextView = view.findViewById(R.id.profile_pin_text_view);

        addressStreetLabelTextView = view.findViewById(R.id.profile_address_street_label_text_view);
        addressStreetTextView = view.findViewById(R.id.profile_address_street_text_view);
        addressCityLabelTextView = view.findViewById(R.id.profile_address_city_label_text_view);
        addressCityTextView = view.findViewById(R.id.profile_address_city_text_view);
        addressStateLabelTextView = view.findViewById(R.id.profile_address_state_label_text_view);
        addressStateTextView = view.findViewById(R.id.profile_address_state_text_view);

        settlementAccountLabelTextView = view.findViewById(R.id.profile_settlement_account_label_text_view);
        settlementAccountBankNameLabelTextView = view.findViewById(R.id.profile_settlement_account_bank_label_text_view);
        settlementAccountBankNameTextView = view.findViewById(R.id.profile_settlement_account_bank_text_view);
        settlementAccountNumberLabelTextView = view.findViewById(R.id.profile_settlement_account_number_label_text_view);
        settlementAccountNumberTextView = view.findViewById(R.id.profile_settlement_account_number_text_view);
        settlementAccountTypeLabelTextView = view.findViewById(R.id.profile_settlement_account_type_label_text_view);
        settlementAccountTypeTextView = view.findViewById(R.id.profile_settlement_account_type_text_view);

        merchantNameEditButton = view.findViewById(R.id.profile_merchant_name_edit_button);
        pinEditButton = view.findViewById(R.id.profile_pin_edit_button);
        addressEditButton = view.findViewById(R.id.profile_address_edit_button);
        settlementAccountEditButton = view.findViewById(R.id.profile_settlement_account_edit_button);

        merchantNameEditButton.setOnClickListener(this);
        pinEditButton.setOnClickListener(this);
        addressEditButton.setOnClickListener(this);
        settlementAccountEditButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        Bundle b = new Bundle();
        User u = dashboardActivityViewModel.getUser().getValue();
        if (u != null) b.putString("token", u.getAuthToken().getToken());

        if (v.getId() == R.id.reload_button) {
            reloadData();
        } else if (v.getId() == R.id.profile_pin_edit_button) {

            NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_editPinDialogFragment, b);

        } else if (v.getId() == R.id.profile_merchant_name_edit_button) {

            NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_editMerchantNameDialogFragment, b);

        } else if (v.getId() == R.id.profile_address_edit_button) {

            NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_editAddressDialogFragment, b);

        } else if (v.getId() == R.id.profile_settlement_account_edit_button) {

            NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_editSettlementAccountDialogFragment, b);

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

        imageView.setVisibility(View.VISIBLE);
        accountTypeTextView.setVisibility(View.VISIBLE);

        if (u.getType().equals(User.TYPE_MERCHANT)) {

            accountTypeTextView.setText(R.string.merchant);

            merchantNameLabelTextView.setVisibility(View.VISIBLE);
            merchantNameTextView.setVisibility(View.VISIBLE);
            merchantNameEditButton.setVisibility(View.VISIBLE);
            merchantNameTextView.setText(((Merchant) u).getName()==null?getString(R.string.__not_set__):((Merchant) u).getName());

            merchantCodeLabelTextView.setVisibility(View.VISIBLE);
            merchantCodeTextView.setVisibility(View.VISIBLE);
            merchantCodeTextView.setText(((Merchant) u).getCode());

            merchantStatusLabelTextView.setVisibility(View.VISIBLE);
            merchantStatusTextView.setVisibility(View.VISIBLE);
            merchantStatusTextView.setText(((Merchant) u).getStatus());

            settlementAccountLabelTextView.setVisibility(View.VISIBLE);
            settlementAccountBankNameLabelTextView.setVisibility(View.VISIBLE);
            settlementAccountBankNameTextView.setVisibility(View.VISIBLE);
            settlementAccountNumberLabelTextView.setVisibility(View.VISIBLE);
            settlementAccountNumberTextView.setVisibility(View.VISIBLE);
            settlementAccountTypeLabelTextView.setVisibility(View.VISIBLE);
            settlementAccountTypeTextView.setVisibility(View.VISIBLE);
            settlementAccountEditButton.setVisibility(View.VISIBLE);

            if (((Merchant) u).getSettlementAccount(0) != null) {
                SettlementAccount account = ((Merchant) u).getSettlementAccount(0);
                settlementAccountBankNameTextView.setText(account.getBankName());
                settlementAccountNumberTextView.setText(account.getNumber());
                settlementAccountTypeTextView.setText(account.getType());
            } else {
                settlementAccountBankNameTextView.setText(getString(R.string.__not_set__));
                settlementAccountNumberTextView.setText(getString(R.string.__not_set__));
                settlementAccountTypeTextView.setText(getString(R.string.__not_set__));
            }

        } else {
            accountTypeTextView.setText(R.string.customer);
        }

        fullNameLabelTextView.setVisibility(View.VISIBLE);
        fullNameTextView.setVisibility(View.VISIBLE);
        fullNameTextView.setText(String.format("%s %s", u.getFirstName(), u.getLastName()));

        phoneNumberLabelTextView.setVisibility(View.VISIBLE);
        phoneNumberTextView.setVisibility(View.VISIBLE);
        phoneNumberTextView.setText(u.getPhoneNumber());

        pinLabelTextView.setVisibility(View.VISIBLE);
        pinTextView.setVisibility(View.VISIBLE);
        pinEditButton.setVisibility(View.VISIBLE);

        addressLabelTextView.setVisibility(View.VISIBLE);
        addressEditButton.setVisibility(View.VISIBLE);
        addressStreetLabelTextView.setVisibility(View.VISIBLE);
        addressStreetTextView.setVisibility(View.VISIBLE);
        addressStreetTextView.setText(u.getAddressStreet()==null?getString(R.string.__not_set__):u.getAddressStreet());

        addressCityLabelTextView.setVisibility(View.VISIBLE);
        addressCityTextView.setVisibility(View.VISIBLE);
        addressCityTextView.setText(u.getAddressCity()==null?getString(R.string.__not_set__):u.getAddressCity());

        addressStateLabelTextView.setVisibility(View.VISIBLE);
        addressStateTextView.setVisibility(View.VISIBLE);
        addressStateTextView.setText(u.getAddressState()==null?getString(R.string.__not_set__):u.getAddressState());

        progressBar.setVisibility(View.GONE);

    }





    public static class EditMerchantNameDialogFragment extends DialogFragment
            implements DialogInterface.OnShowListener, View.OnClickListener, Callback<Result.Success<Void>> {

        private ProgressBar progressBar;

        private EditText nameEditText;

        private Button okButton, badButton;

        private DashboardActivityViewModel viewModel;


        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog a = new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.change_merchant_name)
                    .setView(R.layout.dialog_edit_merchant_name)
                    .setPositiveButton(getString(R.string.done), null)
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
            nameEditText = ((Dialog) dialog).findViewById(R.id.merchant_name_edit_text);
            progressBar = ((Dialog) dialog).findViewById(R.id.loading);

            if (viewModel.getUser().getValue() != null) {
                Merchant m = (Merchant) viewModel.getUser().getValue();
                nameEditText.setText(m.getName());
            }
        }

        @Override
        public void onClick(View v) {

            String name = nameEditText.getText().toString();

            if (name.length() < 3) {
                Toast.makeText(requireContext(), getString(R.string.pin_invalid), Toast.LENGTH_SHORT).show();
            } else {
                sendUpdateName(name);
            }
        }

        private void showLoading(boolean isLoading) {
            okButton.setEnabled(!isLoading);
            badButton.setEnabled(!isLoading);
            nameEditText.setEnabled(!isLoading);
            progressBar.setVisibility(isLoading? View.VISIBLE : View.GONE);
        }

        public void sendUpdateName(String name) {

            if (getArguments() == null) return;

            if (((MyApplication)requireActivity().getApplication()).isOnline()) {
                showLoading(true);

                Merchant user = new Merchant();
                user.setName(name);
                UserApi service = Api.getConnection(getArguments().getString("token")).create(UserApi.class);

                Call<Result.Success<Void>> call = service.updateMerchantName(user);

                call.enqueue(this);
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(@NotNull Call<Result.Success<Void>> call, @NotNull Response<Result.Success<Void>> response) {
            showLoading(false);

            if (response.isSuccessful() && response.body() != null) {

                Result.Success<Void> r = response.body();
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();

                if (viewModel.getUser().getValue() != null) {
                    Merchant m = (Merchant) viewModel.getUser().getValue();
                    m.setName(nameEditText.getText().toString());
                    m.setStatus(DashboardActivityViewModel.merchantStatus(m));
                    viewModel.putUser(m);
                }

                if (getDialog() != null) getDialog().dismiss();

            } else if (response.code() == 400) {

                Result.Success<Map<String, InputData>> r = ErrorParser.parseInputData(response);

                if (r.getData().containsKey("name")) {
                    Toast.makeText(requireContext(), getString(R.string.merchant_name_invalid), Toast.LENGTH_SHORT).show();
                }

            } else if (response.code() == 401) {
                viewModel.getErrorResult().setValue(401);
            } else {
                Result.Error<Void> r = ErrorParser.parseError(response);
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(@NotNull Call<Result.Success<Void>> call, @NotNull Throwable t) {
            showLoading(false);
            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



    public static class EditPinDialogFragment extends DialogFragment
            implements DialogInterface.OnShowListener, View.OnClickListener, Callback<Result.Success<Void>> {

        private DashboardActivityViewModel viewModel;

        private ProgressBar progressBar;

        private EditText oldPinEditText, newPinEditText;

        private Button okButton, badButton;

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog a = new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.change_pin)
                    .setView(R.layout.dialog_edit_pin)
                    .setPositiveButton(getString(R.string.done), null)
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
            oldPinEditText = ((Dialog) dialog).findViewById(R.id.old_password);
            newPinEditText = ((Dialog) dialog).findViewById(R.id.new_password);
            progressBar = ((Dialog) dialog).findViewById(R.id.loading);
        }

        @Override
        public void onClick(View v) {
            String oldPin = oldPinEditText.getText().toString();

            String newPin = newPinEditText.getText().toString();

            if (!Pattern.matches("\\d{4}", oldPin) || !Pattern.matches("\\d{4}", newPin) || oldPin.equals(newPin)) {
                Toast.makeText(requireContext(), getString(R.string.pin_invalid), Toast.LENGTH_SHORT).show();
            } else {
                sendUpdatePin(oldPin, newPin);
            }
        }

        private void showLoading(boolean isLoading) {
            okButton.setEnabled(!isLoading);
            badButton.setEnabled(!isLoading);
            oldPinEditText.setEnabled(!isLoading);
            newPinEditText.setEnabled(!isLoading);
            progressBar.setVisibility(isLoading? View.VISIBLE : View.GONE);
        }

        public void sendUpdatePin(String oldPin, String newPin) {

            if (getArguments() == null) return;

            if (((MyApplication)requireActivity().getApplication()).isOnline()) {
                showLoading(true);

                User user = new User();
                user.setPin(oldPin);
                user.setNewPin(newPin);
                UserApi service = Api.getConnection(getArguments().getString("token")).create(UserApi.class);

                Call<Result.Success<Void>> call = service.updatePin(user);

                call.enqueue(this);
            } else {
                Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(@NotNull Call<Result.Success<Void>> call, @NotNull Response<Result.Success<Void>> response) {
            showLoading(false);

            if (response.isSuccessful() && response.body() != null) {

                Result.Success<Void> r = response.body();
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();
                if (getDialog() != null) getDialog().dismiss();

            } else if (response.code() == 400) {

                Result.Success<Map<String, InputData>> r = ErrorParser.parseInputData(response);
                Map<String, InputData> data = r.getData();
                String message = "";

                if (data.containsKey("pin")) {
                    message = message.concat(getString(R.string.pin_invalid));
                }

                if (data.containsKey("new_pin")) {
                    message = message.concat(getString(R.string.new_pin_invalid));
                }

                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

            } else if (response.code() == 401) {
                viewModel.getErrorResult().setValue(401);
            } else {
                Result.Error<Void> r = ErrorParser.parseError(response);
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(@NotNull Call<Result.Success<Void>> call, @NotNull Throwable t) {
            showLoading(false);
            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public static class EditAddressDialogFragment extends DialogFragment
            implements DialogInterface.OnShowListener, View.OnClickListener, Callback<Result.Success<Void>> {

        private DashboardActivityViewModel viewModel;

        private ProgressBar progressBar;

        private EditText streetEditText;

        private Spinner stateSpinner, citySpinner;

        private Button okButton, badButton;

        private TextView streetError, cityError, stateError;

        private String state="", city, street;


        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog a = new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.change_address)
                    .setView(R.layout.dialog_edit_address)
                    .setPositiveButton(getString(R.string.done), null)
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
            streetEditText = ((Dialog) dialog).findViewById(R.id.street_edit_text);

            String[] states = getResources().getStringArray(R.array.states);

            stateSpinner = ((Dialog) dialog).findViewById(R.id.state_spinner);
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(requireContext(),
                    android.R.layout.simple_spinner_item, states) {

                @Override
                public boolean isEnabled(int position) {
                    return position != 0;
                }

                @Override
                public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        tv.setTextColor(Color.GRAY);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            stateSpinner.setAdapter(adapter);
            stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        state = (String)parent.getItemAtPosition(position);
                        city = null;
                        setUpCitySpinner();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            citySpinner = ((Dialog) dialog).findViewById(R.id.city_spinner);

            progressBar = ((Dialog) dialog).findViewById(R.id.loading);

            streetError = ((Dialog) dialog).findViewById(R.id.street_edit_text_error);
            cityError = ((Dialog) dialog).findViewById(R.id.city_spinner_error);
            stateError = ((Dialog) dialog).findViewById(R.id.state_spinner_error);

            if (viewModel.getUser().getValue() != null) {
                User u = viewModel.getUser().getValue();
                if (u.getAddressState() != null) {
                    state = u.getAddressState();
                    stateSpinner.setSelection(new ArrayList<>(Arrays.asList(states)).indexOf(state));
                }
                if (u.getAddressCity() != null) {
                    city = u.getAddressCity();
                }
                streetEditText.setText(u.getAddressStreet());
            }

            setUpCitySpinner();
        }

        private void setUpCitySpinner() {

            String[] cities;
            citySpinner.setEnabled(true);

            switch (state) {
                case "Rivers State" :
                    cities = getResources().getStringArray(R.array.cities_in_rivers_state);
                    break;
                case "Imo state" :
                    cities = getResources().getStringArray(R.array.cities_in_imo_state);
                    break;
                case "Abia state" :
                    cities = getResources().getStringArray(R.array.cities_in_abia_state);
                    break;
                default:
                    cities = getResources().getStringArray(R.array.cities_blank);
                    citySpinner.setEnabled(false);
            }

            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(requireContext(),
                    android.R.layout.simple_spinner_item, cities) {

                @Override
                public boolean isEnabled(int position) {
                    return position != 0;
                }

                @Override
                public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        tv.setTextColor(Color.GRAY);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            citySpinner.setAdapter(adapter);
            citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        city = (String)parent.getItemAtPosition(position);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if (city != null) {
                citySpinner.setSelection(new ArrayList<>(Arrays.asList(cities)).indexOf(city));
            } else {
                citySpinner.setSelection(0);
            }
        }

        @Override
        public void onClick(View v) {

            street = streetEditText.getText().toString();

            hideErrors();

            Map<String, InputData> errors = new HashMap<>();

            if (street.isEmpty()) {
                errors.put("address_street", null);
            }

            if (city == null || city.isEmpty()) {
                errors.put("address_city", null);
            }

            if (state == null || state.isEmpty()) {
                errors.put("address_state", null);
            }

            if (errors.isEmpty()) {
                sendUpdateAddress();
            } else {
                showErrors(errors);
            }
        }

        private void showErrors(Map<String, InputData> errors) {

            if (errors.containsKey("address_street")) {
                streetError.setVisibility(View.VISIBLE);
            }

            if (errors.containsKey("address_city")) {
                cityError.setVisibility(View.VISIBLE);
            }

            if (errors.containsKey("address_state")) {
                stateError.setVisibility(View.VISIBLE);
            }
        }

        private void hideErrors() {
            streetError.setVisibility(View.GONE);
            cityError.setVisibility(View.GONE);
            stateError.setVisibility(View.GONE);
        }

        private void showLoading(boolean isLoading) {
            okButton.setEnabled(!isLoading);
            badButton.setEnabled(!isLoading);
            streetEditText.setEnabled(!isLoading);
            stateSpinner.setEnabled(!isLoading);
            citySpinner.setEnabled(!isLoading);
            progressBar.setVisibility(isLoading? View.VISIBLE : View.GONE);
        }

        public void sendUpdateAddress() {

            if (getArguments() == null) return;

            if (((MyApplication)requireActivity().getApplication()).isOnline() && viewModel.getUser().getValue() != null) {
                showLoading(true);

                User user = new User();
                user.setAddressStreet(street);
                user.setAddressCity(city);
                user.setAddressState(state);

                UserApi service = Api.getConnection(getArguments().getString("token")).create(UserApi.class);

                Call<Result.Success<Void>> call;

                User u = viewModel.getUser().getValue();

                if (u.getType().equals(User.TYPE_MERCHANT)) {
                    call = service.updateMerchantAddress(user);
                } else {
                    call = service.updateCustomerAddress(user);
                }

                call.enqueue(this);

            } else {
                Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(@NotNull Call<Result.Success<Void>> call, @NotNull Response<Result.Success<Void>> response) {
            showLoading(false);

            if (response.isSuccessful() && response.body() != null) {

                Result.Success<Void> r = response.body();
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();

                if (viewModel.getUser().getValue() != null) {
                    User m = viewModel.getUser().getValue();
                    m.setAddressState(state);
                    m.setAddressCity(city);
                    m.setAddressStreet(street);
                    if (m.getType().equals(User.TYPE_MERCHANT))
                        ((Merchant)m).setStatus(DashboardActivityViewModel.merchantStatus(((Merchant)m)));

                    viewModel.putUser(m);
                }

                if (getDialog() != null) getDialog().dismiss();

            } else if (response.code() == 400) {

                Result.Success<Map<String, InputData>> r = ErrorParser.parseInputData(response);
                Map<String, InputData> data = r.getData();
                showErrors(data);

            } else if (response.code() == 401) {
                viewModel.getErrorResult().setValue(401);
            } else {

                Result.Error<Void> r = ErrorParser.parseError(response);
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(@NotNull Call<Result.Success<Void>> call, @NotNull Throwable t) {
            showLoading(false);
            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public static class EditSettlementAccountDialogFragment extends DialogFragment
            implements DialogInterface.OnShowListener, View.OnClickListener, Callback<Result.Success<SettlementAccount>> {

        private DashboardActivityViewModel viewModel;

        private ProgressBar progressBar;

        private EditText numberEditText;

        private Spinner bankNameSpinner, typeSpinner;

        private Button okButton, badButton;

        private TextView bankError, numberError, typeError;

        private String bank, number, type;


        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog a = new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.change_settlement_account)
                    .setView(R.layout.dialog_edit_settlement_account)
                    .setPositiveButton(getString(R.string.done), null)
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

            progressBar = ((Dialog) dialog).findViewById(R.id.loading);

            numberEditText = ((AlertDialog) dialog).findViewById(R.id.number_edit_text);
            bankNameSpinner = ((AlertDialog) dialog).findViewById(R.id.bank_name_spinner);
            typeSpinner = ((AlertDialog) dialog).findViewById(R.id.type_spinner);

            bankError = ((AlertDialog) dialog).findViewById(R.id.bank_name_spinner_error);
            numberError = ((AlertDialog) dialog).findViewById(R.id.number_edit_text_error);
            typeError = ((AlertDialog) dialog).findViewById(R.id.type_spinner_error);

            String[] banks = getResources().getStringArray(R.array.banks);

            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(requireContext(),
                    android.R.layout.simple_spinner_item, banks) {

                @Override
                public boolean isEnabled(int position) {
                    return position != 0;
                }

                @Override
                public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        tv.setTextColor(Color.GRAY);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bankNameSpinner.setAdapter(adapter);
            bankNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        bank = (String)parent.getItemAtPosition(position);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            String[] types = getResources().getStringArray(R.array.account_types);

            ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<CharSequence>(requireContext(),
                    android.R.layout.simple_spinner_item, types) {

                @Override
                public boolean isEnabled(int position) {
                    return position != 0;
                }

                @Override
                public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if (position == 0) {
                        tv.setTextColor(Color.GRAY);
                    } else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };

            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(adapter1);
            typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0) {
                        type = (String)parent.getItemAtPosition(position);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            if (viewModel.getUser().getValue() != null) {
                SettlementAccount sa = ((Merchant)viewModel.getUser().getValue()).getSettlementAccount(0);
                if (sa != null && sa.getBankName() != null) {
                    bank = sa.getBankName();
                    bankNameSpinner.setSelection(new ArrayList<>(Arrays.asList(banks)).indexOf(bank));
                }
                if (sa != null && sa.getType() != null) {
                    type = sa.getType();
                    typeSpinner.setSelection(new ArrayList<>(Arrays.asList(types)).indexOf(type));
                }
                if (sa != null && sa.getNumber() != null) {
                    numberEditText.setText(sa.getNumber());
                }
            }
        }

        @Override
        public void onClick(View v) {

            number = numberEditText.getText().toString();

            hideErrors();

            Map<String, InputData> errors = new HashMap<>();

            if (number.length() != 10) {
                errors.put("number", null);
            }

            if (bank == null || bank.isEmpty()) {
                errors.put("bank_name", null);
            }

            if (type == null || type.isEmpty()) {
                errors.put("type", null);
            }

            if (errors.isEmpty()) {
                sendUpdate();
            } else {
                showErrors(errors);
            }
        }

        private void showErrors(Map<String, InputData> errors) {

            if (errors.containsKey("bank_name")) {
                bankError.setVisibility(View.VISIBLE);
            }

            if (errors.containsKey("number")) {
                numberError.setVisibility(View.VISIBLE);
            }

            if (errors.containsKey("type")) {
                typeError.setVisibility(View.VISIBLE);
            }
        }

        private void hideErrors() {
            bankError.setVisibility(View.GONE);
            typeError.setVisibility(View.GONE);
            numberError.setVisibility(View.GONE);
        }

        private void showLoading(boolean isLoading) {
            okButton.setEnabled(!isLoading);
            badButton.setEnabled(!isLoading);
            numberEditText.setEnabled(!isLoading);
            bankNameSpinner.setEnabled(!isLoading);
            typeSpinner.setEnabled(!isLoading);
            progressBar.setVisibility(isLoading? View.VISIBLE : View.GONE);
        }

        public void sendUpdate() {

            if (getArguments() == null) return;

            if (((MyApplication)requireActivity().getApplication()).isOnline() && viewModel.getUser().getValue() != null) {
                showLoading(true);

                SettlementAccount sa = new SettlementAccount();
                sa.setBankName(bank);
                sa.setNumber(number);
                sa.setType(type);

                SettlementAccountApi service = Api.getConnection(getArguments().getString("token"))
                                                    .create(SettlementAccountApi.class);

                Call<Result.Success<SettlementAccount>> call = service.add(sa);

                call.enqueue(this);

            } else {
                Toast.makeText(requireContext(), getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(@NotNull Call<Result.Success<SettlementAccount>> call, @NotNull Response<Result.Success<SettlementAccount>> response) {
            showLoading(false);

            if (response.isSuccessful() && response.body() != null) {

                Result.Success<SettlementAccount> r = response.body();
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();

                if (viewModel.getUser().getValue() != null) {
                    SettlementAccount sa = new SettlementAccount();
                    List<SettlementAccount> list = new ArrayList<>();
                    list.add(sa);
                    Merchant m = (Merchant)viewModel.getUser().getValue();
                    sa.setBankName(bank);
                    sa.setNumber(number);
                    sa.setType(type);
                    sa.setId(r.getData().getId());
                    m.setSettlementAccounts(list);
                    viewModel.putUser(m);
                }

                if (getDialog() != null) getDialog().dismiss();

            } else if (response.code() == 400) {

                Result.Success<Map<String, InputData>> r = ErrorParser.parseInputData(response);
                Map<String, InputData> data = r.getData();
                showErrors(data);

            } else if (response.code() == 401) {
                viewModel.getErrorResult().setValue(401);
            } else {

                Result.Error<Void> r = ErrorParser.parseError(response);
                Toast.makeText(requireContext(), r.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(@NotNull Call<Result.Success<SettlementAccount>> call, @NotNull Throwable t) {
            showLoading(false);
            Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }



}




