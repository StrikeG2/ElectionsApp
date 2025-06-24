package com.example.app_elections.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.app_elections.database.daos.UtilisateurDao;
import com.example.app_elections.database.daos.ElectionDao;
import com.example.app_elections.database.entities.Candidat;
import com.example.app_elections.database.entities.Electeur;
import com.example.app_elections.database.entities.Election;
import com.example.app_elections.database.entities.Resultat;
import com.example.app_elections.database.entities.Utilisateur;

@Database(entities = {
        Utilisateur.class,
        Electeur.class,
        Election.class,
        Candidat.class,
        Resultat.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UtilisateurDao utilisateurDao();
    public abstract ElectionDao electionDao();
    // Ajouter d'autres DAOs si besoin

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "elections.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
