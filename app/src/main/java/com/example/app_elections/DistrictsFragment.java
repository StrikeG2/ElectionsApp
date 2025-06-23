package com.example.app_elections;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class DistrictsFragment extends Fragment {

    private LinearLayout districtsContainer;
    private Button addDistrictButton;
    private List<District> districts = new ArrayList<>();

    public DistrictsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_districts, container, false);

        districtsContainer = view.findViewById(R.id.districts_container);
        addDistrictButton = view.findViewById(R.id.add_district_button);

        // Charger des données factices
        loadSampleDistricts();

        addDistrictButton.setOnClickListener(v -> {
            ((com.example.app_elections.MainActivity)requireActivity()).loadFragment(new CreateDistrictFragment());
        });

        return view;
    }

    private void loadSampleDistricts() {
        districts.add(new District("A", "Circonscription Nord", 42));
        districts.add(new District("B", "Circonscription Sud", 35));
        districts.add(new District("C", "Circonscription Est", 28));

        refreshDistrictsUI();
    }

    private void refreshDistrictsUI() {
        districtsContainer.removeAllViews();

        for (District district : districts) {
            View districtView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_district, districtsContainer, false);

            TextView codeText = districtView.findViewById(R.id.district_code);
            TextView nameText = districtView.findViewById(R.id.district_name);
            TextView officesText = districtView.findViewById(R.id.district_offices);

            codeText.setText(district.getCode());
            nameText.setText(district.getName());
            officesText.setText("Bureaux: " + district.getPollingStations());

            districtsContainer.addView(districtView);
        }
    }

    private static class District {
        private String code;
        private String name;
        private int pollingStations;

        public District(String code, String name, int pollingStations) {
            this.code = code;
            this.name = name;
            this.pollingStations = pollingStations;
        }

        public String getCode() { return code; }
        public String getName() { return name; }
        public int getPollingStations() { return pollingStations; }
    }
}