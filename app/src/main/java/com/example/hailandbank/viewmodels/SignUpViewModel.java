package com.example.hailandbank.viewmodels;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.regex.Pattern;

public class SignUpViewModel extends ViewModel {

    private final Handler handler = new Handler(Looper.getMainLooper());

    private final MutableLiveData<FormData> formData = new MutableLiveData<FormData>();

    private final MutableLiveData<SignUpResult> signUpResult = new MutableLiveData<>();


    public MutableLiveData<FormData> getFormData() {
        return formData;
    }

    public LiveData<SignUpResult> getSignUpResult() {
        return signUpResult;
    }

    public void validateData(String firstName, String lastName, String phoneNumber, String pin) {

        FormData data = new FormData();

        data.setFirstName(firstName != null && !firstName.trim().isEmpty());

        data.setLastName(lastName != null && !lastName.trim().isEmpty());

        data.setPhoneNumber(phoneNumber != null && phoneNumber.length() == 11);

        data.setPin(pin != null && Pattern.matches("\\d{4}", pin));

        formData.setValue(data);
    }

    public void signUpUser(String type, String firstName, String lastName, String phoneNumber, String pin) {

        new Thread(() -> {

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            handler.post(() -> signUpResult.setValue(new SignUpResult()));

        }).start();

    }

    public static class FormData {

        private boolean firstName = false;

        private boolean lastName = false;

        private boolean phoneNumber = false;

        private boolean pin = false;

        public boolean isFirstName() {
            return firstName;
        }

        public void setFirstName(boolean firstName) {
            this.firstName = firstName;
        }

        public boolean isLastName() {
            return lastName;
        }

        public void setLastName(boolean lastName) {
            this.lastName = lastName;
        }

        public boolean isPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(boolean phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public boolean isPin() {
            return pin;
        }

        public void setPin(boolean pin) {
            this.pin = pin;
        }

        public boolean isValid() {
            return firstName && lastName && phoneNumber && pin;
        }

    }


    public static class SignUpResult {

        private boolean failed = false;

        private boolean success = false;

        public boolean isFailed() {
            return failed;
        }

        public void setFailed(boolean failed) {
            this.failed = failed;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }



}
