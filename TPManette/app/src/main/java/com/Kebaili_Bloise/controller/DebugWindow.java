package com.Kebaili_Bloise.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DebugWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menudebug);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String pseudo = intent.getStringExtra("NAME");
        String color = intent.getStringExtra("COL");
        System.out.println(pseudo);

        settings(pseudo, color);


        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Game Controller");

        Button button = findViewById(R.id.button2);
        EditText message = findViewById(R.id.messageid);
        EditText result = findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new Thread() {
                    public void run() {
                        System.out.println(message.getText().toString());

                        if (GameMessageManager.isConnected()) {
                            String joueur = String.valueOf(GameMessageManager.sendMessage(message.getText().toString()));

                            runOnUiThread(() -> result.setText(joueur));

                            GameMessageManager.getNextMessage();
                        }
                    }
                }.start();
            }
        });

    }
    private void settings(String pseudo, String color) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (GameMessageManager.isConnected()) {
                    GameMessageManager.sendMessage(pseudo);
                    System.out.println(color);
                    GameMessageManager.sendMessage(color);
                }
            }
        }).start();
    }
}
