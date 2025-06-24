package com.example.app_elections.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.app_elections.CandidatesFragment;
import com.example.app_elections.ElectionsFragment;
import com.example.app_elections.ResultsEntryFragment;
import com.example.app_elections.database.daos.ElectionDao;
import com.example.app_elections.database.daos.UtilisateurDao;
import com.example.app_elections.database.entities.Electeur;
import com.example.app_elections.database.entities.Election;
import com.example.app_elections.database.entities.Utilisateur;

@Database(entities = {Utilisateur.class, Electeur.class,
        Election.class},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UtilisateurDao utilisateurDao();
    public abstract ElectionDao electionDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "elections.db")
                            .fallbackToDestructiveMigration() // Pour la simplicité
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
