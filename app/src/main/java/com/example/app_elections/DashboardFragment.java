package com.example.app_elections;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {

    private LinearLayout historyContainer;
    private Button electionsBtn, candidatesBtn, districtsBtn, validateResultsBtn;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Initialiser les vues
        initializeViews(view);
        
        // Charger des données factices
        loadSampleHistory();

        // Configurer les listeners
        setupClickListeners();

        return view;
    }

    private void initializeViews(View view) {
        historyContainer = view.findViewById(R.id.history_container);
        electionsBtn = view.findViewById(R.id.elections_btn);
        candidatesBtn = view.findViewById(R.id.candidates_btn);
        districtsBtn = view.findViewById(R.id.districts_btn);
        validateResultsBtn = view.findViewById(R.id.validate_results_btn);
    }

    private void setupClickListeners() {
        if (electionsBtn != null) {
            electionsBtn.setOnClickListener(v ->
                    ((MainActivity) requireActivity()).loadFragment(new ElectionsFragment()));
        }

        if (candidatesBtn != null) {
            candidatesBtn.setOnClickListener(v ->
                    ((MainActivity) requireActivity()).loadFragment(new CandidatesFragment()));
        }

        if (districtsBtn != null) {
            districtsBtn.setOnClickListener(v ->
                    ((MainActivity) requireActivity()).loadFragment(new DistrictsFragment()));
        }

        if (validateResultsBtn != null) {
            validateResultsBtn.setOnClickListener(v ->
                    ((MainActivity) requireActivity()).loadFragment(new ResultsValidationFragment()));
        }
    }

    private void loadSampleHistory() {
        if (historyContainer == null) return;

        // Vider le conteneur d'abord
        historyContainer.removeAllViews();

        String[] historyItems = {
                "Élection Présidentielle 2024 - Terminée",
                "Élection Municipale 2023 - Terminée",
                "Référendum Constitutionnel 2022 - Terminée"
        };

        if (historyItems.length == 0) {
            // Afficher un message si aucun historique
            TextView noHistoryText = new TextView(requireContext());
            noHistoryText.setText("Aucune élection récente");
            noHistoryText.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_secondary));
            noHistoryText.setTextSize(14);
            noHistoryText.setPadding(0, 8, 0, 8);
            historyContainer.addView(noHistoryText);
            return;
        }

        for (String item : historyItems) {
            TextView historyItem = new TextView(requireContext());
            historyItem.setText("• " + item);
            historyItem.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_background));
            historyItem.setTextSize(14);
            historyItem.setPadding(0, 8, 0, 8);
            historyContainer.addView(historyItem);
        }
    }
}