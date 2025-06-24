package com.example.app_elections;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.Calendar;

public class CreateElectionFragment extends Fragment {

    private EditText electionNameInput, electionDateInput;
    private Spinner electionTypeSpinner, roundSpinner;
    private Button createButton;

    public CreateElectionFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_election, container, false);

        // Initialisation des vues
        electionNameInput = view.findViewById(R.id.election_name_input);
        electionTypeSpinner = view.findViewById(R.id.election_type_spinner);
        roundSpinner = view.findViewById(R.id.round_spinner);
        electionDateInput = view.findViewById(R.id.election_date_input);
        createButton = view.findViewById(R.id.create_election_button);

        // Configurer les Spinners
        setupSpinners();

        // Gestion du champ date
        setupDatePicker();

        // Gestion du clic sur le bouton
        createButton.setOnClickListener(v -> {
            if (validateForm()) {
                createElection();
                navigateBack();
            }
        });

        return view;
    }

    private void setupSpinners() {
        // Spinner Type d'élection
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.election_types,
                android.R.layout.simple_spinner_item
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        electionTypeSpinner.setAdapter(typeAdapter);

        // Spinner Tour
        ArrayAdapter<CharSequence> roundAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.election_rounds,
                android.R.layout.simple_spinner_item
        );
        roundAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roundSpinner.setAdapter(roundAdapter);
    }

    private void setupDatePicker() {
        electionDateInput.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(
                    requireContext(),
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String formattedDate = String.format("%04d-%02d-%02d",
                                selectedYear, selectedMonth + 1, selectedDay);
                        electionDateInput.setText(formattedDate);
                    },
                    year, month, day
            );
            datePicker.show();
        });
    }

    private boolean validateForm() {
        if (electionNameInput.getText().toString().trim().isEmpty()) {
            showError("Veuillez entrer un nom d'élection");
            return false;
        }
        if (electionDateInput.getText().toString().isEmpty()) {
            showError("Veuillez sélectionner une date");
            return false;
        }
        return true;
    }

    private void createElection() {
        String name = electionNameInput.getText().toString();
        String type = electionTypeSpinner.getSelectedItem().toString();
        String round = roundSpinner.getSelectedItem().toString();
        String date = electionDateInput.getText().toString();

        // Envoyer les données au fragment précédent
        Bundle result = new Bundle();
        result.putString("election_name", name);
        result.putString("election_type", type);
        result.putString("election_round", round);
        result.putString("election_date", date);

        getParentFragmentManager().setFragmentResult("new_election", result);

        showSuccess("Élection créée avec succès");
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
