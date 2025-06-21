package com.example.app_elections;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class ResultsEntryFragment extends Fragment {

    private Spinner candidateSpinner, stationSpinner;
    private EditText votesInput;
    private Button submitButton;

    public ResultsEntryFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results_entry, container, false);

        candidateSpinner = view.findViewById(R.id.candidate_spinner);
        stationSpinner = view.findViewById(R.id.station_spinner);
        votesInput = view.findViewById(R.id.votes_input);
        submitButton = view.findViewById(R.id.submit_results_button);

        setupSpinners();

        submitButton.setOnClickListener(v -> {
            if (validateInputs()) {
                submitResults();
                navigateBack();
            }
        });

        return view;
    }

    private void setupSpinners() {
        // Données factices - à remplacer par vos données réelles
        List<String> candidates = new ArrayList<>();
        candidates.add("Jean Dupont - Parti de la Liberté");
        candidates.add("Marie Curie - Parti de l'Avenir");
        candidates.add("Paul Martin - Parti de la Justice");

        List<String> stations = new ArrayList<>();
        stations.add("Bureau 1 - École Primaire Voltaire");
        stations.add("Bureau 2 - Mairie du 1er");
        stations.add("Bureau 3 - Lycée Louis-le-Grand");

        ArrayAdapter<String> candidateAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                candidates
        );
        candidateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        candidateSpinner.setAdapter(candidateAdapter);

        ArrayAdapter<String> stationAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                stations
        );
        stationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stationSpinner.setAdapter(stationAdapter);
    }

    private boolean validateInputs() {
        if (votesInput.getText().toString().trim().isEmpty()) {
            showError("Veuillez entrer un nombre de voix");
            return false;
        }
        return true;
    }

    private void submitResults() {
        String candidate = candidateSpinner.getSelectedItem().toString();
        String station = stationSpinner.getSelectedItem().toString();
        int votes = Integer.parseInt(votesInput.getText().toString());

        Bundle result = new Bundle();
        result.putString("candidate", candidate);
        result.putString("station", station);
        result.putInt("votes", votes);

        getParentFragmentManager().setFragmentResult("new_results", result);

        showSuccess("Résultats enregistrés");
    }

    private void navigateBack() {
        getParentFragmentManager().popBackStack();
    }

    private void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void showSuccess(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}