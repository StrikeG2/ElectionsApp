package com.example.app_elections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.app_elections.databinding.FragmentRegisterAdminBinding;
import com.example.app_elections.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class RegisterAdminFragment extends Fragment {

    private FragmentRegisterAdminBinding binding;
    private UserRepository userRepository;
    private static final String ADMIN_SECRET_CODE = "CODE_SECRET_123"; // À changer en production

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterAdminBinding.inflate(inflater, container, false);
        userRepository = new UserRepository(requireActivity().getApplication());

        binding.registerButton.setOnClickListener(v -> registerAdmin());

        // Lien vers le login
        binding.loginRedirect.setOnClickListener(v ->
                ((MainActivity)requireActivity()).loadFragment(new LoginFragment()));

        return binding.getRoot();
    }

    private void registerAdmin() {
        String adminCode = binding.editTextAdminCode.getText().toString().trim();
        String email = binding.editTextEmail.getText().toString().trim();
        String password = binding.editTextPassword.getText().toString().trim();

        if (!validateInputs(adminCode, email, password)) {
            return;
        }

        if (!adminCode.equals(ADMIN_SECRET_CODE)) {
            Toast.makeText(getContext(), "Code admin invalide", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hacher le mot de passe
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Enregistrer l'admin dans la base de données
        if (userRepository.registerAdmin(email, hashedPassword)) {
            Toast.makeText(getContext(), "Compte admin créé avec succès", Toast.LENGTH_SHORT).show();
            ((MainActivity)requireActivity()).loadFragment(new LoginFragment());
        } else {
            Toast.makeText(getContext(), "Erreur lors de la création", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateInputs(String adminCode, String email, String password) {
        if (adminCode.isEmpty()) {
            Toast.makeText(getContext(), "Code admin requis", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), "Email invalide", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty() || password.length() < 6) {
            Toast.makeText(getContext(), "Mot de passe trop court (min 6 caractères)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}