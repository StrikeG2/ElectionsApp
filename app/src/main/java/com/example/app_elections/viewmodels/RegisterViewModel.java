package com.example.app_elections.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.app_elections.repositories.UserRepository;
import com.example.app_elections.database.AppDatabase;

import java.util.concurrent.Executors;

public class RegisterViewModel extends AndroidViewModel {
    private static final String TAG = "RegisterViewModel";
    private final UserRepository userRepository;
    private final MutableLiveData<Boolean> inscriptionReussie = new MutableLiveData<>();
    private final MutableLiveData<String> erreurMessage = new MutableLiveData<>();

    public RegisterViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getDatabase(application);
        this.userRepository = new UserRepository(db.utilisateurDao(), Executors.newSingleThreadExecutor());
    }

    public void enregistrerUtilisateur(String email, String motDePasse, String typeUtilisateur) {
        Log.d(TAG, "Début de l'enregistrement pour: " + email + " (type: " + typeUtilisateur + ")");

        userRepository.enregistrerUtilisateur(email, motDePasse, typeUtilisateur, new UserRepository.RegistrationCallback() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "Enregistrement réussi pour: " + email);
                inscriptionReussie.postValue(true);
            }

            @Override
            public void onError(String errorMessage) {
                Log.e(TAG, "Erreur d'enregistrement pour " + email + ": " + errorMessage);
                erreurMessage.postValue(errorMessage);
            }
        });
    }

    public MutableLiveData<Boolean> getInscriptionReussie() {
        return inscriptionReussie;
    }

    public MutableLiveData<String> getErreurMessage() {
        return erreurMessage;
    }
}