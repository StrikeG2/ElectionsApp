package com.example.app_elections.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_elections.R;
import com.example.app_elections.adapters.FeaturesAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeaturesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_features, container, false);

        TextView title = view.findViewById(R.id.features_title);
        title.setText(R.string.features_title);

        ListView listView = view.findViewById(R.id.features_list);
        List<FeatureItem> features = new ArrayList<>();
        features.add(new FeatureItem(R.drawable.ic_stats, "Analyse des Données Électorales"));
        features.add(new FeatureItem(R.drawable.ic_notification, "Notifications en Temps Réel"));
        features.add(new FeatureItem(R.drawable.ic_security, "Sécurité et Confidentialité Renforcées"));

        FeaturesAdapter adapter = new FeaturesAdapter(getActivity(), features);
        listView.setAdapter(adapter);

        return view;
    }

    public static class FeatureItem {
        public int iconRes;
        public String title;

        public FeatureItem(int iconRes, String title) {
            this.iconRes = iconRes;
            this.title = title;
        }
    }
}