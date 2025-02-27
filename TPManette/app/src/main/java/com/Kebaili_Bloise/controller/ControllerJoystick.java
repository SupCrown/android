package com.Kebaili_Bloise.controller;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class ControllerJoystick  extends  AppCompatActivity  {

    public JoystickView.ValueChangedHandler changedHandler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_joystick_controller);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FrameLayout Joystick = findViewById(R.id.joystickid);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button right = findViewById(R.id.button2id);
        right.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Vérifier la connexion dans le nouveau thread
                        if (GameMessageManager.isConnected()) {
                            // Envoyer le message dans le nouveau thread
                            GameMessageManager.sendMessage("MotR=1");
                        } else {
                            // Gérer le cas où la connexion n'est pas établie
                            // Par exemple, afficher un message d'erreur ou réessayer plus tard
                            System.err.println("Connexion non établie !");
                        }}
                }).start(); // Démarrer le thread


            }

        });
        Joystick.addView(new JoystickView(this, changedHandler));
}
}



