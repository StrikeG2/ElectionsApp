package com.example.app_elections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class AddCandidateFragment extends Fragment {

    private EditText nameInput, partyInput, votesInput;

    public AddCandidateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_candidate, container, false);

        nameInput = view.findViewById(R.id.name_input);
        partyInput = view.findViewById(R.id.party_input);
        votesInput = view.findViewById(R.id.votes_input);
        Button addButton = view.findViewById(R.id.add_button);

        addButton.setOnClickListener(v -> {
            if (validateInputs()) {
                addNewCandidate();
                navigateBackToCandidates();
            }
        });

        return view;
    }

    private boolean validateInputs() {
        if (nameInput.getText().toString().trim().isEmpty()) {
            showError("Veuillez entrer un nom");
            return false;
        }
        if (partyInput.getText().toString().trim().isEmpty()) {
            showError("Veuillez entrer un parti politique");
            return false;
        }
        return true;
    }

    private void addNewCandidate() {
        // En production, vous stockeriez cela en base de données
        String name = nameInput.getText().toString();
        String party = partyInput.getText().toString();
        String votes = votesInput.getText().toString();

        // Transmettre les données au fragment précédent
        Bundle result = new Bundle();
        result.putString("name", name);
        result.putString("party", party);
        result.putString("votes", votes);

        getParentFragmentManager().setFragmentResult("new_candidate", result);
    }

    private void navigateBackToCandidates() {
        getParentFragmentManager().popBackStack();
    }

    private void showError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}