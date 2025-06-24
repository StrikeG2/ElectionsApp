package com.example.app_elections.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "candidat",
        foreignKeys = @ForeignKey(entity = Election.class,
                parentColumns = "id",
                childColumns = "idElection",
                onDelete = androidx.room.ForeignKey.CASCADE))
public class Candidat {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "idElection")
    public int idElection;

    @ColumnInfo(name = "nom")
    public String nom;

    @ColumnInfo(name = "parti")
    public String parti;

    @ColumnInfo(name = "nbVoix")
    public int nbVoix = 0;
} 