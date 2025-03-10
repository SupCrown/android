package com.Kebaili_Bloise.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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

        Button button = findViewById(R.id.joystick);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Controller.this, ControllerJoystick.class);
                startActivity(intent);
            }});

        Intent intent = getIntent();
        String pseudo = intent.getStringExtra("NAME");
        System.out.println(pseudo);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        GameMessageManager.sendMessage(pseudo);
                 }});


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
}
