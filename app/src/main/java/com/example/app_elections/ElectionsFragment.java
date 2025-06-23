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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ElectionsFragment extends Fragment {

    private List<Election> elections = new ArrayList<>();
    private Button createElectionBtn;
    private Spinner electionTypeSpinner;

    public ElectionsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_elections, container, false);

        // Initialisation des vues
        electionTypeSpinner = view.findViewById(R.id.election_type_spinner);
        createElectionBtn = view.findViewById(R.id.create_election_btn);

        // Configurer le Spinner
        setupElectionTypeSpinner();

        // Charger les données factices
        loadSampleElections(view);

        // Gestion du clic sur le bouton
        createElectionBtn.setOnClickListener(v -> {
            ((com.example.app_elections.MainActivity)requireActivity()).loadFragment(new CreateElectionFragment());
        });

        return view;
    }

    private void setupElectionTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.election_types,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        electionTypeSpinner.setAdapter(adapter);
    }

    private void loadSampleElections(View view) {
        elections.add(new Election("Présidentielle", "En cours", "25 Avril 2024"));
        elections.add(new Election("Municipale", "Planifiée", "10 Mai 2024"));
        elections.add(new Election("Régionale", "Terminée", "15 Juin 2024"));

        // Afficher les élections
        displayElections(view);
    }

    private void displayElections(View view) {
        LinearLayout container = view.findViewById(R.id.elections_container);
        container.removeAllViews();

        for (Election election : elections) {
            View electionView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_election, container, false);

            TextView title = electionView.findViewById(R.id.election_title);
            TextView date = electionView.findViewById(R.id.election_date);
            TextView status = electionView.findViewById(R.id.election_status);

            title.setText(election.getTitle());
            date.setText("Date: " + election.getDate());
            status.setText("Statut: " + election.getStatus());

            container.addView(electionView);
        }
    }

    private static class Election {
        private String title;
        private String status;
        private String date;

        public Election(String title, String status, String date) {
            this.title = title;
            this.status = status;
            this.date = date;
        }

        public String getTitle() { return title; }
        public String getStatus() { return status; }
        public String getDate() { return date; }
    }
}