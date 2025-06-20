package com.example.app_elections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.app_elections.fragments.FeaturesFragment;
import com.example.app_elections.fragments.FooterFragment;
import com.example.app_elections.fragments.HeaderFragment;
import com.example.app_elections.fragments.WelcomeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Charger les fragments
        loadFragment(new HeaderFragment(), R.id.header_container);
        loadFragment(new WelcomeFragment(), R.id.welcome_container);
        loadFragment(new FeaturesFragment(), R.id.features_container);
        loadFragment(new FooterFragment(), R.id.footer_container);
    }

    private void loadFragment(Fragment fragment, int containerId) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(containerId, fragment);
        transaction.commit();
    }
}