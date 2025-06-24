package com.example.app_elections.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.app_elections.database.daos.UtilisateurDao;
import com.example.app_elections.database.entities.Utilisateur;

import org.mindrot.jbcrypt.BCrypt;

import java.util.concurrent.Executor;

public class UserRepository {
    private final UtilisateurDao utilisateurDao;
    private final Executor executor;

    public UserRepository(UtilisateurDao utilisateurDao, Executor executor) {
        this.utilisateurDao = utilisateurDao;
        this.executor = executor;
    }

    public void enregistrerUtilisateur(String email, String motDePasse, String typeUtilisateur, RegistrationCallback callback) {
        executor.execute(() -> {
            try {
                // Vérifier si l'email existe déjà
                if (utilisateurDao.countByEmail(email) > 0) {
                    callback.onError("Cet email est déjà utilisé");
                    return;
                }

                // Hacher le mot de passe avec BCrypt
                String motDePasseHash = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

                // Créer et insérer le nouvel utilisateur
                Utilisateur nouvelUtilisateur = new Utilisateur(email, motDePasseHash, typeUtilisateur);
                utilisateurDao.insert(nouvelUtilisateur);

                callback.onSuccess();
            } catch (Exception e) {
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
}