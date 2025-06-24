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

public class PollingStationsFragment extends Fragment {

    private EditText stationIdInput, capacityInput;
    private Button createStationButton;

    public PollingStationsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_polling_stations, container, false);

        stationIdInput = view.findViewById(R.id.station_id_input);
        capacityInput = view.findViewById(R.id.capacity_input);
        createStationButton = view.findViewById(R.id.create_station_button);

        createStationButton.setOnClickListener(v -> {
            if (validateInputs()) {
                createStation();
                navigateBack();
            }
        });

        return view;
    }

    private boolean validateInputs() {
        if (stationIdInput.getText().toString().trim().isEmpty()) {
            showError("Veuillez entrer un ID de bureau");
            return false;
        }
        if (capacityInput.getText().toString().trim().isEmpty()) {
            showError("Veuillez entrer une capacité");
            return false;
        }
        return true;
    }

    private void createStation() {
        String stationId = stationIdInput.getText().toString();
        int capacity = Integer.parseInt(capacityInput.getText().toString());

        Bundle result = new Bundle();
        result.putString("station_id", stationId);
        result.putInt("station_capacity", capacity);

        getParentFragmentManager().setFragmentResult("new_polling_station", result);

        showSuccess("Bureau de vote créé");
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