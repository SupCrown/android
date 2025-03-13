package com.Kebaili_Bloise.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ControllerJoystick extends AppCompatActivity {

    private JoystickView joystickView; // Vue personnalisée du joystick

    private Handler joystickHandler = new Handler();
    private Runnable joystickRunnable;
    private static final long JOYSTICK_UPDATE_INTERVAL = 100; // Intervalle en millisecondes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick_controller);

        // Initialisation du joystick et des boutons
        FrameLayout joystickContainer = findViewById(R.id.frameJoy);
        JoystickView joystickView = new JoystickView(this, new JoystickValueChangedHandler());
        joystickView.setJoystickRadius(100);
        joystickContainer.addView(joystickView);

        Intent intent = getIntent();
        String pseudo = intent.getStringExtra("NAME");
        String color = intent.getStringExtra("COL");
        System.out.println(pseudo);

        settings(pseudo, color);

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

        ImageButton shootButton = findViewById(R.id.shutbuttonid);
        shootButton.setOnClickListener(v -> sendCommand("GunTrig=1"));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Arrêter le Handler lorsque l'activité est détruite
        joystickHandler.removeCallbacks(joystickRunnable);
    }

    /**
     * Méthode de calcul de la puissance moteur en fonction des mouvements du joystick.
     */
    private float calculateMotorPower(int angle, int strength, boolean isLeftMotor) {
        float power = strength / 100.0f;

        // Si le joystick pointe en arrière, inverser la direction
        if (angle > 90 && angle < 270) {
            power = -power;
        }

        // Simple logique pour différencier gauche et droite (inverser un angle si nécessaire)
        if (isLeftMotor && angle > 180) {
            power = -power;
        }

        return (power + 1) / 2; // Normaliser dans la plage [0.0 - 1.0]
    }

    /**
     * Méthode pour envoyer une commande au serveur.
     */
    private void sendCommand(String command) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (!GameMessageManager.isConnected()) {
                    System.err.println("Connexion non établie !");
                    return;
                } else {
                    System.out.println(command);
                    GameMessageManager.sendMessage(command);
                }
            }
        }).start(); // Start the thread
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