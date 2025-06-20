package com.example.app_elections.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_elections.R;

public class HeaderFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);

        ImageView icon = view.findViewById(R.id.header_icon);
        TextView title = view.findViewById(R.id.header_title);

        icon.setImageResource(R.drawable.ic_check);
        title.setText(R.string.app_name);

        return view;
    }
}