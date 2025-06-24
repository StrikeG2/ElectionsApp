package com.example.app_elections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultsFragment extends Fragment {

    private Spinner spinnerElections;
    private TextView textResultsSummary;
    private RecyclerView recyclerViewZoneResults;
    private Button btnShareResults;

    private ZoneResultsAdapter zoneResultsAdapter;

    // Exemple de données fictives
    private ArrayList<String> electionsList = new ArrayList<>();
    private ArrayList<ZoneResult> zoneResults = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        spinnerElections = view.findViewById(R.id.spinner_elections);
        textResultsSummary = view.findViewById(R.id.text_results_summary);
        recyclerViewZoneResults = view.findViewById(R.id.recycler_view_zone_results);
        btnShareResults = view.findViewById(R.id.btn_share_results);

        // Charger les données fictives
        loadFakeData();

        // Configurer spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, electionsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerElections.setAdapter(adapter);

        // Configurer RecyclerView
        recyclerViewZoneResults.setLayoutManager(new LinearLayoutManager(getContext()));
        zoneResultsAdapter = new ZoneResultsAdapter(zoneResults);
        recyclerViewZoneResults.setAdapter(zoneResultsAdapter);

        // TODO : ajouter logique pour changer affichage selon élection sélectionnée

        btnShareResults.setOnClickListener(v -> {
            // TODO : implémenter partage des résultats
        });

        return view;
    }

    private void loadFakeData() {
        // Élections fictives
        electionsList.add("Présidentielle 2025");
        electionsList.add("Législatives 2025");
        electionsList.add("Locales 2024");

        // Résultats par zone fictifs
        zoneResults.add(new ZoneResult("Libreville", "45%"));
        zoneResults.add(new ZoneResult("Port-Gentil", "38%"));
        zoneResults.add(new ZoneResult("Franceville", "29%"));
    }

    // Classe simple pour données zone + résultat
    public static class ZoneResult {
        public String zoneName;
        public String participationRate;

        public ZoneResult(String zoneName, String participationRate) {
            this.zoneName = zoneName;
            this.participationRate = participationRate;
        }
    }

    // Adapter RecyclerView pour zone results
    public class ZoneResultsAdapter extends RecyclerView.Adapter<ZoneResultsAdapter.ViewHolder> {

        private final ArrayList<ZoneResult> dataList;

        public ZoneResultsAdapter(ArrayList<ZoneResult> dataList) {
            this.dataList = dataList;
        }

        @NonNull
        @Override
        public ZoneResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ZoneResultsAdapter.ViewHolder holder, int position) {
            ZoneResult item = dataList.get(position);
            holder.text1.setText(item.zoneName);
            holder.text2.setText("Participation: " + item.participationRate);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView text1, text2;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                text1 = itemView.findViewById(android.R.id.text1);
                text2 = itemView.findViewById(android.R.id.text2);
            }
        }
    }
}
