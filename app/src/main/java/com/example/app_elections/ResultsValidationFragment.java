package com.example.app_elections;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class ResultsValidationFragment extends Fragment {

    private LinearLayout resultsContainer;
    private Button validateButton;
    private List<Result> pendingResults = new ArrayList<>();

    public ResultsValidationFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results_validation, container, false);

        resultsContainer = view.findViewById(R.id.results_container);
        validateButton = view.findViewById(R.id.validate_button);

        // Charger des résultats factices
        loadSampleResults();
        displayPendingResults();

        validateButton.setOnClickListener(v -> {
            validateSelectedResults();
            navigateToDashboard();
        });

        return view;
    }

    private void loadSampleResults() {
        pendingResults.add(new Result("Bureau 1", "Jean Dupont", 138));
        pendingResults.add(new Result("Bureau 2", "Marie Curie", 95));
        pendingResults.add(new Result("Bureau 3", "Paul Martin", 112));
    }

    private void displayPendingResults() {
        resultsContainer.removeAllViews();

        for (Result result : pendingResults) {
            View resultView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_pending_result, resultsContainer, false);

            TextView stationText = resultView.findViewById(R.id.result_station);
            TextView candidateText = resultView.findViewById(R.id.result_candidate);
            TextView votesText = resultView.findViewById(R.id.result_votes);
            CheckBox validationCheckbox = resultView.findViewById(R.id.validation_checkbox);

            stationText.setText("Bureau: " + result.getStation());
            candidateText.setText("Candidat: " + result.getCandidate());
            votesText.setText("Voix: " + result.getVotes());

            resultsContainer.addView(resultView);
        }
    }

    private void validateSelectedResults() {
        // Implémentation réelle irait marquer les résultats comme validés en base
        showToast("Résultats validés avec succès");
    }

    private void navigateToDashboard() {
        ((MainActivity)requireActivity()).navigateToFragment(R.id.dashboardFragment);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private static class Result {
        private String station;
        private String candidate;
        private int votes;

        public Result(String station, String candidate, int votes) {
            this.station = station;
            this.candidate = candidate;
            this.votes = votes;
        }

        public String getStation() { return station; }
        public String getCandidate() { return candidate; }
        public int getVotes() { return votes; }
    }
}