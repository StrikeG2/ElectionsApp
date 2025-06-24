package com.example.app_elections.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.app_elections.database.entities.Election;
import com.example.app_elections.repositories.ElectionRepository;

import java.util.List;

public class ElectionViewModel extends AndroidViewModel {
    private ElectionRepository repository;
    private LiveData<List<Election>> allElections;

    public ElectionViewModel(@NonNull Application application) {
        super(application);
        repository = new ElectionRepository(application);
        allElections = repository.getAllElections();
    }

    public long createElection(String libelle, String type, int tour, String dateScrutin, String statut) {
        return repository.createElection(libelle, type, tour, dateScrutin, statut);
    }

    public LiveData<List<Election>> getAllElections() {
        return allElections;
    }
}
