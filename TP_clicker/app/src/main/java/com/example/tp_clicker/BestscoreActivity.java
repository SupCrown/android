package com.example.tp_clicker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Set;
import java.util.HashSet;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Collections;
import java.util.ArrayList;


public class BestscoreActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bestscore);

        String modeName = getIntent().getStringExtra("mode_name");

        ArrayList<String> scoresList = getScoresList(modeName);

        TextView titleText = findViewById(R.id.titreListid);
        titleText.setText("Mode: " + modeName);

        String bestScoreS = scoresList.isEmpty() || scoresList.get(0).equals("Aucun score disponible") ? "Aucun score disponible" : scoresList.get(0);

        ListView listView = findViewById(R.id.scoreListId);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoresList);
        listView.setAdapter(adapter);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button send = findViewById(R.id.sendscoreid);
        send.setOnClickListener(v -> {
            shareBestScore(modeName, bestScoreS);
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedScore = scoresList.get(position);
            shareBestScore(modeName, selectedScore);
        });

        Button backButton = findViewById(R.id.Retourbuttonid);
        backButton.setOnClickListener(v -> {
            finish();
        });

        ImageButton btnQuitter = findViewById(R.id.exitbuttonid);
        btnQuitter.setOnClickListener(v -> finishAffinity());
    }
    private ArrayList<String> getScoresList(String mode) {
        SharedPreferences spref = getSharedPreferences("highscores", MODE_PRIVATE);
        Set<String> scoresSet = spref.getStringSet(mode, new HashSet<>());

        ArrayList<String> scoresList = new ArrayList<>(scoresSet);

        Collections.sort(scoresList, (a, b) -> Integer.compare(Integer.parseInt(b), Integer.parseInt(a)));

        if (scoresList.isEmpty()) {
            scoresList.add("Aucun score disponible");
        }

        return scoresList;
    }

    private void shareBestScore(String mode, String score) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Mon Meilleur Score !");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "J'ai fait un score de " + score + " en mode " + mode + " ! ");

        startActivity(Intent.createChooser(shareIntent, "Partager avec :"));
    }
}
