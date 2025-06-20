package com.example.app_elections.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_elections.R;

public class WelcomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        TextView title1 = view.findViewById(R.id.welcome_title1);
        TextView title2 = view.findViewById(R.id.welcome_title2);
        TextView title3 = view.findViewById(R.id.welcome_title3);
        TextView description = view.findViewById(R.id.welcome_description);

        title1.setText(R.string.welcome_title1);
        title2.setText(R.string.welcome_title2);
        title3.setText(R.string.welcome_title3);
        description.setText(R.string.welcome_description);

        return view;
    }
}