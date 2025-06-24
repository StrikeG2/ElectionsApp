package com.example.app_elections;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.app_elections.database.entities.Election;
import com.example.app_elections.viewmodels.ElectionViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ElectionsFragment extends Fragment {
    private ElectionViewModel viewModel;
    private LinearLayout electionsContainer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_elections, container, false);

        // Initialisation du ViewModel
        viewModel = new ViewModelProvider(this).get(ElectionViewModel.class);

        // Initialisation des vues
        electionsContainer = view.findViewById(R.id.elections_container);
        Button createBtn = view.findViewById(R.id.create_election_btn);

        // Observer les changements dans la liste des élections
        viewModel.getAllElections().observe(getViewLifecycleOwner(), elections -> {
            displayElections(elections);
        });

        // Gestion du clic sur le bouton
        createBtn.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).navigateToFragment(R.id.createElectionFragment);
        });

        return view;
    }

    private void displayElections(List<Election> elections) {
        electionsContainer.removeAllViews();

        if (elections == null || elections.isEmpty()) {
            TextView emptyView = new TextView(getContext());
            emptyView.setText("Aucune élection disponible");
            electionsContainer.addView(emptyView);
            return;
        }

        for (Election election : elections) {
            View electionView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_election, electionsContainer, false);

            TextView title = electionView.findViewById(R.id.election_title);
            TextView date = electionView.findViewById(R.id.election_date);
            TextView status = electionView.findViewById(R.id.election_status);

            title.setText(election.libelle);
            date.setText("Date: " + election.dateScrutin);
            status.setText("Statut: " + election.statut);

            electionsContainer.addView(electionView);
        }
    }
}