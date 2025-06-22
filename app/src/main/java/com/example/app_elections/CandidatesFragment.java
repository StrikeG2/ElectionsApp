package com.example.app_elections;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class CandidatesFragment extends Fragment {

    private LinearLayout candidatesContainer;
    private Button addCandidateButton;
    private List<Candidate> candidates = new ArrayList<>();

    public CandidatesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candidates, container, false);

        candidatesContainer = view.findViewById(R.id.candidates_container);
        addCandidateButton = view.findViewById(R.id.add_candidate_button);

        // Données factices pour démo
        populateSampleCandidates();

        addCandidateButton.setOnClickListener(v -> {
            ((com.example.app_elections.MainActivity)requireActivity()).loadFragment(new AddCandidateFragment());
        });

        return view;
    }

    private void populateSampleCandidates() {
        candidates.add(new Candidate("Jean Dupont", "Parti de la Liberté"));
        candidates.add(new Candidate("Marie Curie", "Parti de l'Avenir"));
        candidates.add(new Candidate("Paul Martin", "Parti de la Justice"));

        refreshCandidatesUI();
    }

    // Classe modèle interne
    private static class Candidate {
        private String name;
        private String party;

        public Candidate(String name, String party) {
            this.name = name;
            this.party = party;
        }

        public String getName() { return name; }
        public String getParty() { return party; }
    }
    private void refreshCandidatesUI() {
        candidatesContainer.removeAllViews();

        for (int i = 0; i < candidates.size(); i++) {
            Candidate candidate = candidates.get(i);
               View candidateView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_candidate, candidatesContainer, false);

                TextView nameText = candidateView.findViewById(R.id.candidate_name);
                TextView partyText = candidateView.findViewById(R.id.candidate_party);
                Button deleteButton = candidateView.findViewById(R.id.delete_button);
                Button editButton = candidateView.findViewById(R.id.edit_button);

                nameText.setText(candidate.getName());
                partyText.setText(candidate.getParty());

                // Gestion suppression
            int finalI = i;
            deleteButton.setOnClickListener(v -> {
                    candidates.remove(finalI);
                    refreshCandidatesUI();
                    showToast("Candidat supprimé");
                });

                // Gestion édition
                editButton.setOnClickListener(v -> {
                    openEditDialog(candidate, finalI);
                });

                candidatesContainer.addView(candidateView);
            }
        }

        private void openEditDialog(Candidate candidate, int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = requireActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_edit_candidate, null);

            EditText nameInput = dialogView.findViewById(R.id.edit_name);
            EditText partyInput = dialogView.findViewById(R.id.edit_party);

            nameInput.setText(candidate.getName());
            partyInput.setText(candidate.getParty());

            builder.setView(dialogView)
                    .setTitle("Modifier candidat")
                    .setPositiveButton("Enregistrer", (dialog, id) -> {
                        String newName = nameInput.getText().toString();
                        String newParty = partyInput.getText().toString();

                    if (!newName.isEmpty() && !newParty.isEmpty()) {
                        candidates.set(position, new Candidate(newName, newParty));
                        refreshCandidatesUI();
                        showToast("Modifications enregistrées");
                    }
            })
            .setNegativeButton("Annuler", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}