package com.Kebaili_Bloise.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Controller extends AppCompatActivity {

    private boolean isUpPressed = false;
    private boolean isLeftPressed = false;
    private boolean isRightPressed = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_controller);
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

        ImageButton right = findViewById(R.id.right);
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isRightPressed = true;
                        sendMovementMessage();
                        return true;
                    case MotionEvent.ACTION_UP:
                        isRightPressed = false;
                        sendMovementMessage();
                        return true;
                }
                return false;
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText message = findViewById(R.id.message);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GameMessageManager.sendMessage("MSG="+message.getText().toString());
                    }
                }).start();
            }
        });

        ImageButton left = findViewById(R.id.left);
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isLeftPressed = true;
                        sendMovementMessage();
                        return true;
                    case MotionEvent.ACTION_UP:
                        isLeftPressed = false;
                        sendMovementMessage();
                        return true;
                }
                return false;
            }
        });

        ImageButton up = findViewById(R.id.up);
        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isUpPressed = true;
                        sendMovementMessage();
                        return true;
                    case MotionEvent.ACTION_UP:
                        isUpPressed = false;
                        sendMovementMessage();
                        return true;
                }
                return false;
            }
        });

        ImageButton shoot = findViewById(R.id.shoot);
        shoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("shoot");
                                GameMessageManager.sendMessage("GunTrig=1");
                            }
                        }).start();
                        return true;
                }
                return false;
            }
        });
    }



    private void sendMovementMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!GameMessageManager.isConnected()) {
                    System.err.println("Connexion non établie !");
                    return;
                }

                float motR = 0.5f;
                float motL = 0.5f;

                if (isUpPressed) {
                    if (isLeftPressed) {
                        // Diagonale gauche
                        motR = 1f;
                        motL = 0.5f;
                    } else if (isRightPressed) {
                        // Diagonale droite
                        motR = 0.5f;
                        motL = 1f;
                    } else {
                        // Tout droit
                        motR = 1f;
                        motL = 1f;
                    }
                } else {
                    if (isLeftPressed) {
                        // Gauche
                        motR = 1f;
                        motL = 0.5f;
                    } else if (isRightPressed) {
                        // Droite
                        motR = 0.5f;
                        motL = 1f;
                    }
                }

                GameMessageManager.sendMessage("MotR=" + motR + "#MotL=" + motL);
            }
        }).start();

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
