package com.example.app_elections;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.app_elections.databinding.FragmentRegisterBinding;
import com.example.app_elections.viewmodels.RegisterViewModel;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private RegisterViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Configuration du dropdown pour les types d'utilisateurs
        String[] typesUtilisateur = {"Électeur", "Superviseur", "Opérateur", "Administrateur"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                typesUtilisateur
        );
        binding.autoCompleteUserType.setAdapter(adapter);

        // Gestion de l'inscription
        binding.registerButton.setOnClickListener(v -> {
            if (validerFormulaire()) {
                String email = binding.editTextEmail.getText().toString().trim();
                String motDePasse = binding.editTextPassword.getText().toString();
                String typeUtilisateur = binding.autoCompleteUserType.getText().toString();

                viewModel.enregistrerUtilisateur(email, motDePasse, typeUtilisateur);
            }
        });

        // Observation des résultats
        viewModel.getInscriptionReussie().observe(getViewLifecycleOwner(), reussie -> {
            if (reussie) {
                Navigation.findNavController(binding.getRoot());
                        //.navigate(R.id.action_registerFragment_to_electionsFragment);
            }
        });

        viewModel.getErreurMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
        });

        binding.textViewLoginLink.setOnClickListener(v -> {
            try {
                Navigation.findNavController(v).navigate(R.id.action_register_to_login);
            } catch (IllegalArgumentException e) {
                // Action de navigation non trouvée
                Toast.makeText(requireContext(),
                        "Navigation non configurée",
                        Toast.LENGTH_SHORT).show();
                Log.e("RegisterFragment", "Action de navigation non trouvée", e);
            }
        });
    }

    private boolean validerFormulaire() {
        boolean valide = true;

        // Validation email
        if (binding.editTextEmail.getText().toString().isEmpty()) {
            binding.textInputEmail.setError("Email requis");
            valide = false;
        } else {
            binding.textInputEmail.setError(null);
        }

        // Validation mot de passe
        if (binding.editTextPassword.getText().toString().isEmpty()) {
            binding.textInputPassword.setError("Mot de passe requis");
            valide = false;
        } else if (binding.editTextPassword.getText().length() < 6) {
            binding.textInputPassword.setError("Minimum 6 caractères");
            valide = false;
        } else {
            binding.textInputPassword.setError(null);
        }

        // Validation confirmation mot de passe
        if (!binding.editTextConfirmPassword.getText().toString().equals(binding.editTextPassword.getText().toString())) {
            binding.textInputConfirmPassword.setError("Les mots de passe ne correspondent pas");
            valide = false;
        } else {
            binding.textInputConfirmPassword.setError(null);
        }

        // Validation type utilisateur
        if (binding.autoCompleteUserType.getText().toString().isEmpty()) {
            binding.textInputUserType.setError("Type d'utilisateur requis");
            valide = false;
        } else {
            binding.textInputUserType.setError(null);
        }

        return valide;

    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}