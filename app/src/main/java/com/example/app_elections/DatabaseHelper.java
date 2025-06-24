package com.example.app_elections;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Informations de la base de données
    private static final String DATABASE_NAME = "elections.db";
    private static final int DATABASE_VERSION = 1;

    // Tables et colonnes
    public static final String TABLE_UTILISATEUR = "utilisateur";
    public static final String TABLE_ELECTEUR = "electeur";
    public static final String TABLE_SUPERVISEUR = "superviseur";
    public static final String TABLE_ADMIN = "admin";
    public static final String TABLE_OPERATEUR = "operateur";
    public static final String TABLE_ELECTION = "election";
    public static final String TABLE_CANDIDAT = "candidat";
    public static final String TABLE_CIRCONSCRIPTION = "circonscription";
    public static final String TABLE_BUREAU_VOTE = "bureau_vote";
    public static final String TABLE_CENTRE_VOTE = "centre_vote";
    public static final String TABLE_RESULTAT = "resultat";

    // Requêtes de création des tables
    private static final String CREATE_TABLE_UTILISATEUR =
            "CREATE TABLE " + TABLE_UTILISATEUR + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "email TEXT NOT NULL, " +
                    "motDePasse TEXT NOT NULL);";

    private static final String CREATE_TABLE_ELECTEUR =
            "CREATE TABLE " + TABLE_ELECTEUR + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idUtilisateur INTEGER NOT NULL, " +
                    "aVote INTEGER DEFAULT 0, " +
                    "FOREIGN KEY(idUtilisateur) REFERENCES " + TABLE_UTILISATEUR + "(id));";

    private static final String CREATE_TABLE_ELECTION =
            "CREATE TABLE " + TABLE_ELECTION + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "libelle TEXT NOT NULL, " +
                    "type TEXT NOT NULL, " +
                    "tour INTEGER NOT NULL, " +
                    "dateScrutin TEXT NOT NULL);";

    private static final String CREATE_TABLE_CANDIDAT =
            "CREATE TABLE " + TABLE_CANDIDAT + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idElection INTEGER NOT NULL, " +
                    "nom TEXT NOT NULL, " +
                    "parti TEXT NOT NULL, " +
                    "nbVoix INTEGER DEFAULT 0, " +
                    "FOREIGN KEY(idElection) REFERENCES " + TABLE_ELECTION + "(id));";

    private static final String CREATE_TABLE_RESULTAT =
            "CREATE TABLE " + TABLE_RESULTAT + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idElection INTEGER NOT NULL, " +
                    "idCandidat INTEGER NOT NULL, " +
                    "idBureauVote INTEGER NOT NULL, " +
                    "nombreVoix INTEGER NOT NULL, " +
                    "pourcentage REAL NOT NULL, " +
                    "FOREIGN KEY(idElection) REFERENCES " + TABLE_ELECTION + "(id), " +
                    "FOREIGN KEY(idCandidat) REFERENCES " + TABLE_CANDIDAT + "(id), " +
                    "FOREIGN KEY(idBureauVote) REFERENCES " + TABLE_BUREAU_VOTE + "(id));";

    // Constructeur
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création des tables
        db.execSQL(CREATE_TABLE_UTILISATEUR);
        db.execSQL(CREATE_TABLE_ELECTEUR);
        db.execSQL(CREATE_TABLE_ELECTION);
        db.execSQL(CREATE_TABLE_CANDIDAT);
        db.execSQL(CREATE_TABLE_RESULTAT);
        // Ajoutez les autres tables selon le même modèle
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Suppression des tables existantes
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ELECTEUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        // Recréation de la base
        onCreate(db);
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Requête pour vérifier l'utilisateur
        String query = "SELECT * FROM " + TABLE_UTILISATEUR +
                " WHERE email = ? AND motDePasse = ?";

        // Dans une application réelle, vous devriez hasher le mot de passe
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public String getUserType(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String userType = "unknown";

        // Vérifier d'abord dans la table electeur
        String query = "SELECT 1 FROM " + TABLE_ELECTEUR + " e " +
                "JOIN " + TABLE_UTILISATEUR + " u ON e.idUtilisateur = u.id " +
                "WHERE u.email = ?";

        Cursor cursor = db.rawQuery(query, new String[]{email});
        if (cursor.getCount() > 0) {
            userType = "electeur";
        }
        cursor.close();

        // Ajouter des vérifications similaires pour admin, superviseur, etc.
        // ...

        return userType;
    }
}