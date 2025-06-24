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
                ((MainActivity)requireActivity()).navigateToFragment(R.id.electionFragment));

        candidatesBtn.setOnClickListener(v ->
                ((MainActivity)requireActivity()).navigateToFragment(R.id.candidatesFragment));

        districtsBtn.setOnClickListener(v ->
                ((MainActivity)requireActivity()).navigateToFragment(R.id.districtsFragment));

        return view;
    }

    @SuppressLint("SetTextI18n")
    private void loadSampleHistory() {
        String[] historyItems = {
                "Élection Présidentielle 2024 - Terminée",
                "Élection Municipale 2023 - Terminée",
                "Référendum Constitutionnel 2022 - Terminée"
        };

        // Ajoute un séparateur entre les éléments
        for (String item : historyItems) {
            TextView historyItem = new TextView(getContext());
            historyItem.setText("• " + item);
            historyItem.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_background));
            historyItem.setPadding(0, 8, 0, 8);
            historyContainer.addView(historyItem);

            // Ajoute un séparateur
            if (!item.equals(historyItems[historyItems.length-1])) {
                View separator = new View(getContext());
                separator.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 1));
                separator.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.divider));
                historyContainer.addView(separator);
            }
        }
    }
}