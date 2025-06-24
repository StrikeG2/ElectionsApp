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

public class CreateDistrictFragment extends Fragment {

    private EditText codeInput, nameInput, officesInput;
    private Button createButton;

    public CreateDistrictFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_district, container, false);

        codeInput = view.findViewById(R.id.district_code_input);
        nameInput = view.findViewById(R.id.district_name_input);
        officesInput = view.findViewById(R.id.district_offices_input);
        createButton = view.findViewById(R.id.create_district_button);

        createButton.setOnClickListener(v -> {
            if (validateInputs()) {
                createDistrict();
                navigateBack();
            }
        });

        return view;
    }

    private boolean validateInputs() {
        if (codeInput.getText().toString().trim().isEmpty()) {
            showError("Veuillez entrer un code");
            return false;
        }
        if (nameInput.getText().toString().trim().isEmpty()) {
            showError("Veuillez entrer un nom");
            return false;
        }
        if (officesInput.getText().toString().trim().isEmpty()) {
            showError("Veuillez entrer le nombre de bureaux");
            return false;
        }
        return true;
    }

    private void createDistrict() {
        String code = codeInput.getText().toString();
        String name = nameInput.getText().toString();
        int offices = Integer.parseInt(officesInput.getText().toString());

        Bundle result = new Bundle();
        result.putString("district_code", code);
        result.putString("district_name", name);
        result.putInt("district_offices", offices);

        getParentFragmentManager().setFragmentResult("new_district", result);

        showSuccess("Circonscription créée");
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