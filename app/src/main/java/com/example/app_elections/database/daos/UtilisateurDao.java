package com.example.app_elections.database.daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.app_elections.database.entities.Utilisateur;

@Dao
public interface UtilisateurDao {
    @Query("SELECT * FROM utilisateur WHERE email = :email AND motDePasse = :password")
    Utilisateur authenticate(String email, String password);

    @Insert
    void insert(Utilisateur utilisateur);

    // Ajouter d'autres méthodes selon besoins
}
