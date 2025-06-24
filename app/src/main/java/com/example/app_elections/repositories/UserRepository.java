package com.example.app_elections.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_elections.database.daos.UtilisateurDao;
import com.example.app_elections.database.entities.Utilisateur;

import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.Executor;

public class UserRepository {
    private static final String TAG = "UserRepository";
    private final UtilisateurDao utilisateurDao;
    private final Executor executor;

    public UserRepository(UtilisateurDao utilisateurDao, Executor executor) {
        this.utilisateurDao = utilisateurDao;
        this.executor = executor;
    }

    public void enregistrerUtilisateur(String email, String motDePasse, String typeUtilisateur, RegistrationCallback callback) {
        executor.execute(() -> {
            try {
                Log.d(TAG, "Vérification de l'email: " + email);
                int count = utilisateurDao.countByEmail(email);

                if (count > 0) {
                    Log.w(TAG, "Email déjà existant: " + email);
                    callback.onError("Cet email est déjà utilisé");
                    return;
                }

                Log.d(TAG, "Hachage du mot de passe pour: " + email);
                String motDePasseHash = BCrypt.hashpw(motDePasse, BCrypt.gensalt());
                Log.v(TAG, "Mot de passe hashé généré");

                Log.d(TAG, "Création de l'entité Utilisateur");
                Utilisateur nouvelUtilisateur = new Utilisateur(email, motDePasseHash, typeUtilisateur);

                Log.d(TAG, "Insertion en base de données");
                utilisateurDao.insert(nouvelUtilisateur);
                Log.i(TAG, "Utilisateur enregistré avec succès: " + email);

                callback.onSuccess();
            } catch (Exception e) {
                Log.e(TAG, "Exception lors de l'enregistrement", e);
                callback.onError("Erreur lors de l'inscription: " + e.getMessage());
            }
        });
    }

    public interface RegistrationCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    public LiveData<Boolean> authenticate(String email, String password) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        executor.execute(() -> {
            String hashedPassword = utilisateurDao.getHashedPassword(email);
            if (hashedPassword != null && BCrypt.checkpw(password, hashedPassword)) {
                result.postValue(true);
            } else {
                result.postValue(false);
            }
        });
        return result;
    }

    public LiveData<String> getUserType(String email) {
        MutableLiveData<String> result = new MutableLiveData<>();
        executor.execute(() -> {
            String type = utilisateurDao.getUserType(email);
            result.postValue(type);
        });
        return result;
    }

    // UserRepository.java
    public LiveData<Boolean> userExists(String email) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        executor.execute(() -> {
            boolean exists = utilisateurDao.userExists(email);
            result.postValue(exists);
        });
        return result;
    }
}