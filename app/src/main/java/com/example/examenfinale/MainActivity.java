package com.example.examenfinale;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private EditText titreArticleEditText;
    private EditText contenuEditText;

    private EditText auteurEditText;
    private Button ajouterButton;
    private Button dernierBouton; // Ajout de la déclaration du bouton dernierBouton
    private DatabaseHelper databaseHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titreArticleEditText = findViewById(R.id.titrearticle);
        contenuEditText = findViewById(R.id.contenu);
        auteurEditText = findViewById(R.id.auteur);
        ajouterButton = findViewById(R.id.buttonajouter);
        dernierBouton = findViewById(R.id.dernierBouton); // Initialisation du bouton dernierBouton

        databaseHelper = new DatabaseHelper(this);

        ajouterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterArticle();
            }
        });

        dernierBouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Appeler l'autre activité ici
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    private void ajouterArticle() {
        String titre = titreArticleEditText.getText().toString().trim();
        String contenu = contenuEditText.getText().toString().trim();
        String auteur = auteurEditText.getText().toString().trim();

        // Obtenir la date actuelle
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(calendar.getTime());

        if (!titre.isEmpty() && !contenu.isEmpty()) {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(DatabaseContract.ArticleEntry.COLUMN_TITRE, titre);
            values.put(DatabaseContract.ArticleEntry.COLUMN_CONTENU, contenu);
            values.put(DatabaseContract.ArticleEntry.COLUMN_AUTEUR, auteur);
            values.put(DatabaseContract.ArticleEntry.COLUMN_DATE, date);

            long newRowId = db.insert(DatabaseContract.ArticleEntry.TABLE_NAME, null, values);

            if (newRowId != -1) {
                Toast.makeText(this, "Article ajouté avec succès", Toast.LENGTH_SHORT).show();
                titreArticleEditText.setText("");
                contenuEditText.setText("");
                auteurEditText.setText(""); // Réinitialiser le champ auteurEditText
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout de l'article", Toast.LENGTH_SHORT).show();
            }

            db.close();
        } else {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        }
    }

}