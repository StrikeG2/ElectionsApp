package com.example.app_elections.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.app_elections.repositories.UserRepository;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository repository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
    }

    public boolean login(String email, String password) {
        return repository.authenticate(email, password);
    }
}
