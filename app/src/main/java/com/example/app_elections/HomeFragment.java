// HomeFragment.java
package com.example.app_elections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView welcomeText = view.findViewById(R.id.text_welcome);
        Button seeDetailsBtn = view.findViewById(R.id.btn_detailed_results);

        welcomeText.setText("Bienvenue, suivez les résultats en direct");

        seeDetailsBtn.setOnClickListener(v -> {
            // TODO: Open detailed results screen
        });
    }
}
