package com.example.app_elections.database.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "electeur",
        foreignKeys = @ForeignKey(entity = Utilisateur.class,
                parentColumns = "id",
                childColumns = "idUtilisateur",
                onDelete = CASCADE))
public class Electeur {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "idUtilisateur")
    public int idUtilisateur;

    @ColumnInfo(name = "aVote")
    public boolean aVote;
}
