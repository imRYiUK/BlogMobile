package com.example.examenfinale;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.text.Html;

import com.example.examenfinale.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> articles;
    private ArticleAdapter adapter;
    private DatabaseHelper databaseHelper;
    private Button returnButton; // Ajout de la variable pour le bouton de retour

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHelper = new DatabaseHelper(this);
        articles = loadArticles();

        adapter = new ArticleAdapter(articles);
        recyclerView.setAdapter(adapter);

        returnButton = findViewById(R.id.returnButton); // Initialisation du bouton de retour

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Action à effectuer lorsque le bouton de retour est cliqué
                onBackPressed(); // Utilisation de la méthode onBackPressed pour revenir à l'activité précédente
            }
        });
    }

    // Méthode pour charger les articles depuis la base de données
    private List<String> loadArticles() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseContract.ArticleEntry.TABLE_NAME, null, null, null, null, null, null);
        List<String> articles = new ArrayList<>();

        while (cursor.moveToNext()) {
            int titreIndex = cursor.getColumnIndex(DatabaseContract.ArticleEntry.COLUMN_TITRE);
            int contenuIndex = cursor.getColumnIndex(DatabaseContract.ArticleEntry.COLUMN_CONTENU);
            int auteurIndex = cursor.getColumnIndex(DatabaseContract.ArticleEntry.COLUMN_AUTEUR);
            int dateIndex = cursor.getColumnIndex(DatabaseContract.ArticleEntry.COLUMN_DATE);

            String titre = cursor.getString(titreIndex);
            String contenu = cursor.getString(contenuIndex);
            String auteur = cursor.getString(auteurIndex);
            String date = cursor.getString(dateIndex);

            String titreEnGras = "<b>" + titre + "</b>";
            String article = titreEnGras + "<br>" + contenu + "<br>" + auteur + "<br>" + date;
            articles.add(article);
        }

        cursor.close();
        db.close();

        return articles;
    }

    // Classe ArticleAdapter
    private class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

        private List<String> articles;

        public ArticleAdapter(List<String> articles) {
            this.articles = articles;
        }

        @Override
        public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
            return new ArticleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ArticleViewHolder holder, int position) {
            String article = articles.get(position);
            holder.bind(article);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Séparer la chaîne de l'article en titre, contenu, auteur et date
                    String[] parts = article.split("<br>", 4);
                    String titre = parts[0];
                    String contenu = parts[1];
                    String auteur = parts[2];
                    String date = parts[3];

                    Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
                    intent.putExtra("titre", titre);
                    intent.putExtra("contenu", contenu);
                    intent.putExtra("auteur", auteur);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return articles.size();
        }
    }

    // Classe ArticleViewHolder
    private class ArticleViewHolder extends RecyclerView.ViewHolder {

        private TextView articleTextView;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            articleTextView = itemView.findViewById(R.id.articleTextView);
        }

        public void bind(String article) {
            int maxLength = 100; // Limite de longueur du texte (nombre de caractères)
            if (article.length() > maxLength) {
                String truncatedText = article.substring(0, maxLength) + "...";
                articleTextView.setText(Html.fromHtml(truncatedText));
            } else {
                articleTextView.setText(Html.fromHtml(article));
            }
        }
    }
}
