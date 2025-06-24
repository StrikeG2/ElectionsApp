package com.example.app_elections.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "election")
public class Election {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "libelle")
    public String libelle;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "tour")
    public int tour;

    @ColumnInfo(name = "dateScrutin")
    public String dateScrutin;

    @ColumnInfo(name = "statut")
    public String statut; // "planifiée", "en cours", "terminée"

    // Constructeur vide requis par Room
    public Election() {}

    // Constructeur complet
    public Election(String libelle, String type, int tour, String dateScrutin, String statut) {
        this.libelle = libelle;
        this.type = type;
        this.tour = tour;
        this.dateScrutin = dateScrutin;
        this.statut = statut;
    }

    // Getters et setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getTour() { return tour; }
    public void setTour(int tour) { this.tour = tour; }

    public String getDateScrutin() { return dateScrutin; }
    public void setDateScrutin(String dateScrutin) { this.dateScrutin = dateScrutin; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
} 