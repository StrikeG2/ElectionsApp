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
import androidx.fragment.app.Fragment;

public class DashboardFragment extends Fragment {

    private LinearLayout historyContainer;
    private Button electionsBtn, candidatesBtn, districtsBtn;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        historyContainer = view.findViewById(R.id.history_container);
        electionsBtn = view.findViewById(R.id.elections_btn);
        candidatesBtn = view.findViewById(R.id.candidates_btn);
        districtsBtn = view.findViewById(R.id.districts_btn);

        // Charger des données factices
        loadSampleHistory();

        // Navigation
        electionsBtn.setOnClickListener(v ->
                ((com.example.app_elections.MainActivity)requireActivity()).loadFragment(new ElectionsFragment()));

        candidatesBtn.setOnClickListener(v ->
                ((com.example.app_elections.MainActivity)requireActivity()).loadFragment(new CandidatesFragment()));

        districtsBtn.setOnClickListener(v ->
                ((com.example.app_elections.MainActivity)requireActivity()).loadFragment(new DistrictsFragment()));

        return view;
    }

    private void loadSampleHistory() {
        String[] historyItems = {
                "Élection Présidentielle 2024 - Terminée",
                "Élection Municipale 2023 - Terminée",
                "Référendum Constitutionnel 2022 - Terminée"
        };

        for (String item : historyItems) {
            TextView historyItem = new TextView(getContext());
            historyItem.setText("• " + item);
            historyItem.setPadding(0, 8, 0, 8);
            historyContainer.addView(historyItem);
        }
    }
}