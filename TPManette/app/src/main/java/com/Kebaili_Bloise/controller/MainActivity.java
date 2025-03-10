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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Game Controller");



        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                EditText IP = findViewById(R.id.textconnectionid);
                EditText pseudo = findViewById(R.id.pseudo);
                EditText color = findViewById(R.id.color);
                String couleur ="";
                if (color.getText().toString().equals("blue")) {
                    couleur = "0=0=255";
                } else if (color.getText().toString().equals("green")) {
                    couleur = "0=255=0";
                } else if (color.getText().toString().equals("red")) {
                    couleur = "255=0=0";
                }
                String finalCouleur = couleur;
                new Thread(){
                    public void run() {
                        GameMessageManager.connect(IP.getText().toString(),6969);

                        System.out.println(pseudo.getText().toString());

                        Intent intent = new Intent(MainActivity.this, Controller.class);
                        intent.putExtra("NAME", "NAME="+pseudo.getText().toString());
                        intent.putExtra("COL", "COL="+ finalCouleur);
                        startActivity(intent);
                    }}.start();

            }





        });




    }




}