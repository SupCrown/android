package com.Kebaili_Bloise.controller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ControllerJoystick extends AppCompatActivity {

    private JoystickView joystickView; // Vue personnalisée du joystick

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joystick_controller);

        // Trouver le frame dans lequel le joystick sera placé
        FrameLayout joystickContainer = findViewById(R.id.joystickid);

        // Crée le joystick et l'ajoute au FrameLayout
        joystickView = new JoystickView(this);
        joystickContainer.addView(joystickView);

        // Initialiser le bouton pour tirer (shoot)
        ImageButton shootButton = findViewById(R.id.shutbuttonid);

        // Listener pour le bouton de tir
        shootButton.setOnClickListener(v -> {
            Toast.makeText(this, "Fire command sent!", Toast.LENGTH_SHORT).show();
            sendCommand("GunTrig=1"); // Envoyer une commande pour tirer
        });

        // Listener pour arrêter le tir (par joystick reset ou autre)
        Button joystickResetButton = findViewById(R.id.joystick);
        joystickResetButton.setOnClickListener(v -> {
            joystickView.resetStick(); // Remets le joystick au centre
            Toast.makeText(this, "Joystick reset!", Toast.LENGTH_SHORT).show();
        });

        // Écouter les mouvements du joystick
        joystickView.setOnJoystickMoveListener((angle, strength) -> {
            // Convertir les mouvements en puissance moteur et envoyer des commandes
            float motorPowerLeft = calculateMotorPower(angle, strength, true); // Moteur gauche
            float motorPowerRight = calculateMotorPower(angle, strength, false); // Moteur droit

            sendCommand("MotL=" + motorPowerLeft);
            sendCommand("MotR=" + motorPowerRight);
        });
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
        // Coupe uniquement pour montrer les commandes. Remplacez par votre logique serveur
        Toast.makeText(this, "Command sent: " + command, Toast.LENGTH_SHORT).show();
    }
}