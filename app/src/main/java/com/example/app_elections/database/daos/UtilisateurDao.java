package com.example.app_elections.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.app_elections.database.entities.Utilisateur;

@Dao
public interface UtilisateurDao {
    @Query("SELECT * FROM utilisateur WHERE email = :email LIMIT 1")
    Utilisateur findByEmail(String email);  // Modifié pour ne pas inclure le mot de passe

    @Insert
    void insert(Utilisateur utilisateur);

    @Query("SELECT COUNT(*) FROM utilisateur WHERE email = :email")
    int countByEmail(String email);

    @Query("SELECT motDePasse FROM utilisateur WHERE email = :email LIMIT 1")
    String getHashedPassword(String email);  // Ne retourne que le hash

    @Query("SELECT typeUtilisateur FROM utilisateur WHERE email = :email LIMIT 1")
    String getUserType(String email);

}