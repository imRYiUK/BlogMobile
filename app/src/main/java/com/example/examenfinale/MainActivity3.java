package com.example.examenfinale;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.text.Html;

public class MainActivity3 extends AppCompatActivity {

    private TextView titreTextView, contenuTextView, auteurTextView, auteurDateTextView, dateTextView;
    private ImageButton retourButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        titreTextView = findViewById(R.id.titreTextView);
        contenuTextView = findViewById(R.id.contenuTextView);
        auteurTextView = findViewById(R.id.auteurTextView); // Nouveau TextView pour afficher l'auteur
        auteurDateTextView = findViewById(R.id.auteurDateTextView);
        dateTextView = findViewById(R.id.dateTextView); // Nouveau TextView pour afficher la date
        retourButton = findViewById(R.id.retourButton);

        // Récupération des données passées depuis MainActivity2
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titre = extras.getString("titre");
            String contenu = extras.getString("contenu");
            String auteur = extras.getString("auteur");
            String date = extras.getString("date");

            // Affichage du titre en gras
            String titreEnGras = "<b>" + titre + "</b>";
            titreTextView.setText(Html.fromHtml(titreEnGras));

            // Affichage du contenu
            contenuTextView.setText(contenu);

            // Affichage de l'auteur et de la date avec des tabulations
            String auteurDate = "Écrit par : " + "<b>" + auteur + "</b>" + "\t\t\t\t\t\t\t\t\t" + date;
            auteurDateTextView.setText(Html.fromHtml(auteurDate));
        }

        retourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Retourne à MainActivity2
            }
        });
    }
}
