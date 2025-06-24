package com.example.app_elections.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "elections")
public class Election {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "libelle")
    public String libelle;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "tour")
    public int tour;

    @ColumnInfo(name = "date_scrutin")
    public String dateScrutin;

    @ColumnInfo(name = "statut")
    public String statut; // "planifiée", "en cours", "terminée"

    // Constructeur
    public Election(String libelle, String type, int tour, String dateScrutin, String statut) {
        this.libelle = libelle;
        this.type = type;
        this.tour = tour;
        this.dateScrutin = dateScrutin;
        this.statut = statut;
    }

    // Getters...
}
