package com.example.app_elections.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.app_elections.R;
import com.example.app_elections.fragments.FeaturesFragment.FeatureItem;

import java.util.List;

public class FeaturesAdapter extends ArrayAdapter<FeatureItem> {

    public FeaturesAdapter(Context context, List<FeatureItem> features) {
        super(context, 0, features);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        FeatureItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.feature_item, parent, false);
        }

        ImageView icon = convertView.findViewById(R.id.feature_icon);
        TextView title = convertView.findViewById(R.id.feature_title);

        icon.setImageResource(item.iconRes);
        title.setText(item.title);

        return convertView;
    }
}