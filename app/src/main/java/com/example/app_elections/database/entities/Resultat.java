package com.example.app_elections.database.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "resultat",
        foreignKeys = {
                @ForeignKey(entity = Election.class,
                        parentColumns = "id",
                        childColumns = "idElection",
                        onDelete = androidx.room.ForeignKey.CASCADE),
                @ForeignKey(entity = Candidat.class,
                        parentColumns = "id",
                        childColumns = "idCandidat",
                        onDelete = androidx.room.ForeignKey.CASCADE)
        })
public class Resultat {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "idElection")
    public int idElection;

    @ColumnInfo(name = "idCandidat")
    public int idCandidat;

    @ColumnInfo(name = "idBureauVote")
    public int idBureauVote;

    @ColumnInfo(name = "nombreVoix")
    public int nombreVoix;

    @ColumnInfo(name = "pourcentage")
    public double pourcentage;

    @ColumnInfo(name = "valide")
    public boolean valide = false;
} 