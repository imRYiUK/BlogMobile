package com.example.examenfinale;

import android.provider.BaseColumns;

public class DatabaseContract {

    private DatabaseContract() {
    }

    public static class ArticleEntry implements BaseColumns {
        public static final String TABLE_NAME = "articles";
        public static final String COLUMN_TITRE = "titre";
        public static final String COLUMN_CONTENU = "contenu";
        public static final String COLUMN_AUTEUR = "auteur"; // Ajout de la colonne auteur
        public static final String COLUMN_DATE = "date"; // Ajout de la colonne date
    }
}
