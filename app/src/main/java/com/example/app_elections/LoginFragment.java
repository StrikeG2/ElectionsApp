package com.example.app_elections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.app_elections.database.AppDatabase;
import com.example.app_elections.databinding.FragmentLoginBinding;
import com.example.app_elections.repositories.UserRepository;

import java.util.concurrent.Executors;

// LoginFragment.java
// LoginFragment.java
public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private UserRepository userRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        // Initialisation avec Room
        AppDatabase db = AppDatabase.getDatabase(requireActivity());
        userRepository = new UserRepository(
                db.utilisateurDao(),
                Executors.newSingleThreadExecutor()
        );

        setupLoginButton();
        setupForgotPassword();

        return binding.getRoot();
    }

    private void setupLoginButton() {
        binding.loginButton.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString().trim();
            String password = binding.editTextPassword.getText().toString().trim();

            if (validateInputs(email, password)) {
                attemptLogin(email, password);
            }
        });
    }

    private void setupForgotPassword() {
        // Ajoutez ici la logique pour "Mot de passe oublié"
        binding.editTextPassword.setOnClickListener(v -> {
            // Navigation vers le fragment de récupération de mot de passe
        });
    }

    private boolean validateInputs(String email, String password) {
        if (email.isEmpty()) {
            binding.editTextEmail.setError("Email requis");
            return false;
        }
        if (password.isEmpty()) {
            binding.editTextPassword.setError("Mot de passe requis");
            return false;
        }
        return true;
    }

    private void attemptLogin(String email, String password) {
        userRepository.authenticate(email, password).observe(getViewLifecycleOwner(), success -> {
            if (success) {
                onLoginSuccess(email);
            } else {
                onLoginFailure();
            }
        });
    }

    private void onLoginSuccess(String email) {
        Toast.makeText(requireContext(), "Connexion réussie", Toast.LENGTH_SHORT).show();
        userRepository.getUserType(email).observe(getViewLifecycleOwner(), this::redirectUser);
    }

    private void onLoginFailure() {
        Toast.makeText(requireContext(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
    }

    private void redirectUser(String userType) {
        switch (userType.toLowerCase()) {
            case "admin":
                navigateTo(R.id.action_login_to_main);
                break;
            case "electeur":
                navigateTo(R.id.action_login_to_main);
                break;
            case "superviseur":
                navigateTo(R.id.action_login_to_main);
                break;
            case "operateur":
                navigateTo(R.id.action_login_to_main);
                break;
            default:
                Toast.makeText(requireContext(), "Type d'utilisateur inconnu", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateTo(int destinationId) {
        Navigation.findNavController(binding.getRoot()).navigate(destinationId);
    }
}