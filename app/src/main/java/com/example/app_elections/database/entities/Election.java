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
    public String statut = "Planifiée";
} 