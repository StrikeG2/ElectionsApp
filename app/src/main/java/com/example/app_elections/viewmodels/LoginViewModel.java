package com.example.app_elections.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_elections.database.AppDatabase;
import com.example.app_elections.database.daos.UtilisateurDao;
import com.example.app_elections.repositories.UserRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginViewModel extends AndroidViewModel {
    private final UserRepository repository;
    private final MutableLiveData<String> currentUserType = new MutableLiveData<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        // Obtenez le DAO et créez un Executor
        UtilisateurDao utilisateurDao = AppDatabase.getDatabase(application).utilisateurDao();
        Executor executor = Executors.newSingleThreadExecutor();
        repository = new UserRepository(utilisateurDao, executor);
    }

    public LiveData<Boolean> authenticate(String email, String password) {
        return repository.authenticate(email, password);
    }

    public LiveData<String> getUserType(String email) {
        repository.getUserType(email).observeForever(currentUserType::postValue);
        return currentUserType;
    }
}