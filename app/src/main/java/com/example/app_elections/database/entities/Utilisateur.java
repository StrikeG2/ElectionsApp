package com.example.app_elections.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "utilisateur")
public class Utilisateur {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "motDePasse")
    public String motDePasse;

    @ColumnInfo(name = "typeUtilisateur") // Nouveau champ pour distinguer les types
    public String typeUtilisateur; // "electeur", "admin", "superviseur", "operateur"

    // Constructeur
    public Utilisateur(String email, String motDePasse, String typeUtilisateur) {
        this.email = email;
        this.motDePasse = motDePasse;
        this.typeUtilisateur = typeUtilisateur;
    }
}