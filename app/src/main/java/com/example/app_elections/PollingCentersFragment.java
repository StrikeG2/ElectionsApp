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

public class PollingCentersFragment extends Fragment {

    private LinearLayout centersContainer;
    private Button addCenterButton;
    private List<PollingCenter> centers = new ArrayList<>();

    public PollingCentersFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_polling_centers, container, false);

        centersContainer = view.findViewById(R.id.centers_container);
        addCenterButton = view.findViewById(R.id.add_center_button);

        // Charger des données factices
        loadSampleCenters();

        addCenterButton.setOnClickListener(v -> {
            ((com.example.app_elections.MainActivity)requireActivity()).loadFragment(new CreatePollingCenterFragment());
        });

        return view;
    }

    private void loadSampleCenters() {
        List<String> parisStations = new ArrayList<>();
        parisStations.add("École Primaire Voltaire");
        parisStations.add("Mairie du 1er");
        parisStations.add("Lycée Louis-le-Grand");

        List<String> lyonStations = new ArrayList<>();
        lyonStations.add("Gymnase Lumière");
        lyonStations.add("Espace Jean Couty");

        centers.add(new PollingCenter("Paris 1er", "123 Rue de la Paix, 75001 Paris", parisStations));
        centers.add(new PollingCenter("Lyon 5ème", "50 Avenue des Frères Lumière, 69005 Lyon", lyonStations));

        refreshCentersUI();
    }

    private void refreshCentersUI() {
        centersContainer.removeAllViews();

        for (PollingCenter center : centers) {
            View centerView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_polling_center, centersContainer, false);

            TextView titleText = centerView.findViewById(R.id.center_title);
            TextView addressText = centerView.findViewById(R.id.center_address);
            LinearLayout stationsContainer = centerView.findViewById(R.id.stations_container);

            titleText.setText(center.getName());
            addressText.setText(center.getAddress());

            // Ajouter les bureaux de vote
            for (String station : center.getPollingStations()) {
                TextView stationView = new TextView(getContext());
                stationView.setText("• " + station);
                stationView.setPadding(0, 4, 0, 4);
                stationsContainer.addView(stationView);
            }

            centersContainer.addView(centerView);
        }
    }

    private static class PollingCenter {
        private String name;
        private String address;
        private List<String> pollingStations;

        public PollingCenter(String name, String address, List<String> pollingStations) {
            this.name = name;
            this.address = address;
            this.pollingStations = pollingStations;
        }

        public String getName() { return name; }
        public String getAddress() { return address; }
        public List<String> getPollingStations() { return pollingStations; }
    }
}