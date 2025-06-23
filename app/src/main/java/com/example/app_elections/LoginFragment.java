package com.example.app_elections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialisation des vues
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        buttonLogin = view.findViewById(R.id.login_button);

        // Initialisation de la base de données
        dbHelper = new DatabaseHelper(getActivity());

        // Gestion du clic sur le bouton de connexion
        buttonLogin.setOnClickListener(v -> attemptLogin());

        return view;
    }

    private void attemptLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        // Vérification des identifiants
        boolean isAuthenticated = dbHelper.checkUser(email, password);

        if (isAuthenticated) {
            // Connexion réussie
            Toast.makeText(getActivity(), "Connexion réussie", Toast.LENGTH_SHORT).show();

            // Récupérer le type d'utilisateur et rediriger
            String userType = dbHelper.getUserType(email);
            redirectUser(userType);
        } else {
            Toast.makeText(getActivity(), "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    private void redirectUser(String userType) {
        // Selon le diagramme de classes, vous avez Electeur, Admin, Superviseur, Opérateur
        switch (userType) {
            case "admin":
                // Rediriger vers l'interface Admin
                ((MainActivity) getActivity()).navigateToAdminDashboard();
                break;
            case "electeur":
                // Rediriger vers l'interface Electeur
                ((MainActivity) getActivity()).navigateToVoterDashboard();
                break;
            // Ajouter les autres cas selon vos besoins
            default:
                Toast.makeText(getActivity(), "Type d'utilisateur inconnu", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}