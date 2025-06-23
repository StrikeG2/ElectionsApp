package com.example.app_elections.repositories;

import android.app.Application;

import com.example.app_elections.database.AppDatabase;
import com.example.app_elections.database.daos.UtilisateurDao;
import com.example.app_elections.database.entities.Utilisateur;

public class UserRepository {
    private final UtilisateurDao utilisateurDao;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        utilisateurDao = db.utilisateurDao();
    }

    public boolean authenticate(String email, String password) {
        // Note: Dans une vraie app, faire ça en background
        Utilisateur user = utilisateurDao.authenticate(email, password);
        return user != null;
    }
}
