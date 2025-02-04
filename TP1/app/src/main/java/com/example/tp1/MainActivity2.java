package com.example.tp1;

import android.content.Intent;
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

import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ImageView img = findViewById(R.id.imageView2);
        double pok = Math.random();
        int pokemonCur = 0;
        if (pok<=0.50){
            pokemonCur = R.drawable.roucoul;
        }else {
            pokemonCur = R.drawable.dracolosse;
        }
        img.setImageResource(pokemonCur);



        ImageButton b = findViewById(R.id.imageButton);
        int finalPokemonCur = pokemonCur;
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView img = findViewById(R.id.imageView2);
                  img.setImageResource(R.drawable.capture);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {

                        double capt = Math.random();
                        if (capt<=0.50){
                            img.setImageResource(finalPokemonCur);

                        }else {
                            finish();
                        }                    }
                },4000);

            }


        });
    }
}