package com.example.tp_clicker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModeInfernalActivity extends AppCompatActivity{

    TextView score, hightscoreInfernal;
    int nbcookie = 0;
    int nbbonus = 0;
    int bestScoreInfernal = 0;
    int infercookie = 0;

    private Handler handler = new Handler();
    private boolean isRunning = true;
    private int interval = 5000;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.modeinfernal);
        startInfernalMode();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.infernalscoreid), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        final boolean[] bonuspepiteimage = {false};

        final boolean[] bonusbeurreimage = {false};

        hightscoreInfernal = findViewById(R.id.scoreid);
        score = findViewById(R.id.textid);

        ImageButton Cookie = findViewById(R.id.cookieid);

        ImageButton btnQuitter = findViewById(R.id.exitbuttonid);
        btnQuitter.setOnClickListener(v -> {
            isRunning = false;
            finishAffinity();
        });

        ImageButton pepite = findViewById(R.id.pepiteid);
        ImageButton beurre = findViewById(R.id.beurrebonusid);
        TextView bonustext = findViewById(R.id.bonusid);

        ImageView inter1 = findViewById(R.id.pointinterid1);
        ImageView inter2 = findViewById(R.id.pointinterid2);

        bestScoreInfernal = getBestScoreInfernal();
        hightscoreInfernal.setText(""+bestScoreInfernal);

        pepite.setEnabled(false);
        beurre.setEnabled(false);



        final boolean[] bonusPeppiteDejaChange = {false};
        final boolean[] bonusBeurreDejaChange = {false};

        Cookie.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                nbcookie = Math.max(0, nbcookie + (1 * (nbbonus + 1)));
                score.setText("Vous avez " + nbcookie + " cookies");

                if (nbcookie > bestScoreInfernal) {
                    bestScoreInfernal = nbcookie;
                    saveHighScoreInfernal(bestScoreInfernal);
                }

                if (nbcookie >= 100 && !bonusPeppiteDejaChange[0]) {
                    bonuspepiteimage[0] = true;
                    pepite.setEnabled(true);
                    pepite.setVisibility(pepite.VISIBLE);
                    inter1.setVisibility(inter1.INVISIBLE);
                    bonusPeppiteDejaChange[0] = true;
                } else if (nbcookie < 100 && bonusPeppiteDejaChange[0]) {
                    bonuspepiteimage[0] = false;
                    pepite.setVisibility(pepite.INVISIBLE);
                    inter1.setVisibility(inter1.VISIBLE);
                    pepite.setEnabled(false);
                    bonusPeppiteDejaChange[0] = false;
                }

                if (nbcookie >= 1000 && !bonusBeurreDejaChange[0]) {
                    bonusbeurreimage[0] = true;
                    beurre.setEnabled(true);
                    beurre.setVisibility(beurre.VISIBLE);
                    inter2.setVisibility(inter2.INVISIBLE);
                    bonusBeurreDejaChange[0] = true;
                } else if (nbcookie < 1000 && bonusBeurreDejaChange[0]) {
                    bonusbeurreimage[0] = false;
                    beurre.setVisibility(beurre.INVISIBLE);
                    inter2.setVisibility(inter2.VISIBLE);
                    beurre.setEnabled(false);
                    bonusBeurreDejaChange[0] = false;
                }

            }
        });

        pepite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bonuspepiteimage[0] && nbcookie >= 100) {
                    nbcookie -= 100;
                    nbbonus += 2;
                    bonustext.setText("Bonus Multiplicateur : " + nbbonus);
                    score.setText("Vous avez " + nbcookie + " cookies");
                }
            }
        });

        beurre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bonusbeurreimage[0] && nbcookie >= 1000) {
                    nbcookie -= 1000;
                    nbbonus += 8;
                    bonustext.setText("Bonus Multiplicateur : " + nbbonus);
                    score.setText("Vous avez " + nbcookie + " cookies");
                }
            }
        });

        ImageButton switchbutton = findViewById(R.id.switchmode);
        switchbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(ModeInfernalActivity.this, SelectActivity.class);
                startActivity(intent);
            }


        });

        Button viewScoresButton = findViewById(R.id.Voirscoreid);
        viewScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ModeInfernalActivity.this, BestscoreActivity.class);
                intent.putExtra("mode_name", "Infernal");
                intent.putExtra("shared_pref_name", "highscores");
                startActivity(intent);
            }
        });
    }

    private void startInfernalMode() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isRunning) return;

                if (nbcookie > 0) {
                    infercookie = Math.max(1, nbcookie / 10);
                    nbcookie -= infercookie;
                    score.setText("Vous avez " + nbcookie + " cookies");

                    interval = Math.max(500, 5000 - (nbcookie / 2));
                }

                handler.postDelayed(this, interval);
            }
        }, interval);
    }


    private int getBestScoreInfernal() {
        SharedPreferences spref = getSharedPreferences("highscores", MODE_PRIVATE);
        Set<String> scoresSet = spref.getStringSet("Infernal", new HashSet<>());

        if (scoresSet.isEmpty()) {
            return 0;
        }

        List<Integer> scoresList = new ArrayList<>();
        for (String s : scoresSet) {
            scoresList.add(Integer.parseInt(s));
        }

        Collections.sort(scoresList, Collections.reverseOrder());
        return scoresList.get(0);
    }

    private void saveHighScoreInfernal(int score) {
        SharedPreferences spref = getSharedPreferences("highscores", MODE_PRIVATE);
        SharedPreferences.Editor editor = spref.edit();

        Set<String> scoresSet = spref.getStringSet("Infernal", new HashSet<>());

        List<Integer> scoresList = new ArrayList<>();
        for (String s : scoresSet) {
            scoresList.add(Integer.parseInt(s));
        }

        scoresList.add(score);
        Collections.sort(scoresList, Collections.reverseOrder());

        if (scoresList.size() > 20) {
            scoresList = scoresList.subList(0, 20);
        }

        Set<String> newScoresSet = new HashSet<>();
        for (Integer s : scoresList) {
            newScoresSet.add(String.valueOf(s));
        }

        editor.putStringSet("Infernal", newScoresSet);
        editor.apply();
    }


}
