package com.example.app_elections;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class CreatePollingCenterFragment extends Fragment {

    private EditText idInput, locationInput;
    private Button createButton;

    public CreatePollingCenterFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_polling_center, container, false);

        idInput = view.findViewById(R.id.center_id_input);
        locationInput = view.findViewById(R.id.center_location_input);
        createButton = view.findViewById(R.id.create_center_button);

        createButton.setOnClickListener(v -> {
            if (validateInputs()) {
                createCenter();
                navigateBack();
            }
        });

        return view;
    }

    private boolean validateInputs() {
        if (idInput.getText().toString().trim().isEmpty()) {
            showError("Veuillez entrer un ID de centre");
            return false;
        }
        if (locationInput.getText().toString().trim().isEmpty()) {
            showError("Veuillez entrer une localisation");
            return false;
        }
        return true;
    }

    private void createCenter() {
        String id = idInput.getText().toString();
        String location = locationInput.getText().toString();

        Bundle result = new Bundle();
        result.putString("center_id", id);
        result.putString("center_location", location);

        getParentFragmentManager().setFragmentResult("new_polling_center", result);

        showSuccess("Centre de vote créé");
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