package com.example.app_elections.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.app_elections.database.entities.Election;

import java.util.List;

@Dao
public interface ElectionDao {
    @Insert
    long insert(Election election); // Retourne l'ID de l'élection créée

    @Query("SELECT * FROM elections ORDER BY date_scrutin DESC")
    LiveData<List<Election>> getAllElections();

    @Query("SELECT * FROM elections WHERE id = :electionId")
    LiveData<Election> getElectionById(int electionId);
}
