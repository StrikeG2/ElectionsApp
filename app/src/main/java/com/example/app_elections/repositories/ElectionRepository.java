package com.example.app_elections.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.app_elections.database.AppDatabase;
import com.example.app_elections.database.daos.ElectionDao;
import com.example.app_elections.database.entities.Election;

import java.util.List;

public class ElectionRepository {
    private final ElectionDao electionDao;

    public ElectionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        electionDao = db.electionDao();
    }

    public long createElection(String libelle, String type, int tour, String dateScrutin, String statut) {
        Election newElection = new Election(libelle, type, tour, dateScrutin, statut);
        return electionDao.insert(newElection);
    }

    public LiveData<List<Election>> getAllElections() {
        return electionDao.getAllElections();
    }
}