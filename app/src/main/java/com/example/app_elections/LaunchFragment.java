package com.example.app_elections;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class LaunchFragment extends Fragment {

    private Button startButton;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_launch, container, false);

        startButton = view.findViewById(R.id.start_button);
        startButton.setOnClickListener(v -> {
            // Naviguer vers le tableau de bord
            ((MainActivity)requireActivity()).navigateToFragment(R.id.dashboardFragment);
        });

        return view;
    }
}