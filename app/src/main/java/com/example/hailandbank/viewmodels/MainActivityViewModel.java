package com.example.hailandbank.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.hailandbank.models.User;


public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<User> user = new MutableLiveData<>();

    private final MutableLiveData<Boolean> nullUser = new MutableLiveData<>();

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<Boolean> getNullUser() {
        return nullUser;
    }

    public void putUser(User u) {
        user.setValue(u);
    }

    public void makeUserNull() {
        nullUser.setValue(true);
    }

}


