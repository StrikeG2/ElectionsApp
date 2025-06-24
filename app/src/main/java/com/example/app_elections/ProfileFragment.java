package com.example.app_elections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProfileFragment extends Fragment {

    private SearchView searchView;
    private Spinner spinnerParty, spinnerZone;
    private RecyclerView recyclerViewCandidates;

    private CandidatesAdapter adapter;

    private List<Candidate> allCandidates = new ArrayList<>();
    private List<String> parties = new ArrayList<>();
    private List<String> zones = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        searchView = view.findViewById(R.id.search_view);
        spinnerParty = view.findViewById(R.id.spinner_party);
        spinnerZone = view.findViewById(R.id.spinner_zone);
        recyclerViewCandidates = view.findViewById(R.id.recycler_view_candidates);

        loadFakeData();

        // Configurer les spinners
        ArrayAdapter<String> partyAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, parties);
        partyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParty.setAdapter(partyAdapter);

        ArrayAdapter<String> zoneAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, zones);
        zoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZone.setAdapter(zoneAdapter);

        // RecyclerView
        recyclerViewCandidates.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CandidatesAdapter(allCandidates);
        recyclerViewCandidates.setAdapter(adapter);

        // Filtrer selon recherche ou filtres
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { return false; }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterCandidates(newText,
                        spinnerParty.getSelectedItem().toString(),
                        spinnerZone.getSelectedItem().toString());
                return true;
            }
        });

        spinnerParty.setOnItemSelectedListener(new SimpleItemSelectedListener(() ->
                filterCandidates(searchView.getQuery().toString(),
                        spinnerParty.getSelectedItem().toString(),
                        spinnerZone.getSelectedItem().toString())
        ));

        spinnerZone.setOnItemSelectedListener(new SimpleItemSelectedListener(() ->
                filterCandidates(searchView.getQuery().toString(),
                        spinnerParty.getSelectedItem().toString(),
                        spinnerZone.getSelectedItem().toString())
        ));

        return view;
    }

    private void filterCandidates(String query, String party, String zone) {
        List<Candidate> filtered = new ArrayList<>(allCandidates);

        if (query != null && !query.isEmpty()) {
            String lower = query.toLowerCase();
            List<Candidate> temp = new ArrayList<>();
            for (Candidate c : filtered) {
                if (c.name.toLowerCase().contains(lower)) {
                    temp.add(c);
                }
            }
            filtered = temp;
        }

        if (!party.equals("Tous")) {
            List<Candidate> temp = new ArrayList<>();
            for (Candidate c : filtered) {
                if (c.party.equals(party)) {
                    temp.add(c);
                }
            }
            filtered = temp;
        }

        if (!zone.equals("Toutes")) {
            List<Candidate> temp = new ArrayList<>();
            for (Candidate c : filtered) {
                if (c.zone.equals(zone)) {
                    temp.add(c);
                }
            }
            filtered = temp;
        }

        adapter.updateData(filtered);
    }


    private void loadFakeData() {
        // Parties
        parties.add("Tous");
        parties.add("Parti Vert");
        parties.add("Parti Bleu");
        parties.add("Parti Jaune");

        // Zones
        zones.add("Toutes");
        zones.add("Libreville");
        zones.add("Port-Gentil");
        zones.add("Franceville");

        // Candidats fictifs
        allCandidates.add(new Candidate("Alice M", "Parti Vert", "Libreville", 58, 5));
        allCandidates.add(new Candidate("Bob K", "Parti Bleu", "Port-Gentil", 40, 2));
        allCandidates.add(new Candidate("Clara J", "Parti Jaune", "Franceville", 72, 8));
    }

    // Classe Candidate
    public static class Candidate {
        String name;
        String party;
        String zone;
        int score;
        int zonesWon;

        public Candidate(String name, String party, String zone, int score, int zonesWon) {
            this.name = name;
            this.party = party;
            this.zone = zone;
            this.score = score;
            this.zonesWon = zonesWon;
        }
    }

    // Adapter RecyclerView candidats
    public class CandidatesAdapter extends RecyclerView.Adapter<CandidatesAdapter.ViewHolder> {

        private List<Candidate> data;

        public CandidatesAdapter(List<Candidate> data) {
            this.data = data;
        }

        public void updateData(List<Candidate> newData) {
            data = newData;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public CandidatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_candidat, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CandidatesAdapter.ViewHolder holder, int position) {
            Candidate c = data.get(position);
            holder.name.setText(c.name);
            holder.party.setText(c.party);
            holder.zone.setText(c.zone);
            holder.score.setText("Score: " + c.score);
            holder.zonesWon.setText("Zones remportées: " + c.zonesWon);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView name, party, zone, score, zonesWon;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.text_name);
                party = itemView.findViewById(R.id.text_party);
                zone = itemView.findViewById(R.id.text_zone);
                score = itemView.findViewById(R.id.text_score);
                zonesWon = itemView.findViewById(R.id.text_zones_won);
            }
        }
    }

    // Listener simplifié pour Spinner (pour éviter boilerplate)
    public static class SimpleItemSelectedListener implements android.widget.AdapterView.OnItemSelectedListener {
        private final Runnable onSelected;

        public SimpleItemSelectedListener(Runnable onSelected) {
            this.onSelected = onSelected;
        }

        @Override
        public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
            onSelected.run();
        }

        @Override
        public void onNothingSelected(android.widget.AdapterView<?> parent) {}
    }
}
