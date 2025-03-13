package com.Kebaili_Bloise.controller;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


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
        setContentView(R.layout.activity_main_v2);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Toolbar toolbar = findViewById(R.id.my_toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Game Controller");

        EditText IP = findViewById(R.id.messageid);
        EditText pseudo = findViewById(R.id.result);
        EditText color = findViewById(R.id.color);
        Spinner spinner = findViewById(R.id.spinner);
        Button button = findViewById(R.id.button);


        String[] options = {"Sélectionner", "Boutons", "Joystick", "Debug"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String couleur;
                String colorText = color.getText().toString().toLowerCase();
                switch (colorText) {
                    case "blue":
                        couleur = "0=0=255";
                        break;
                    case "green":
                        couleur = "0=255=0";
                        break;
                    case "red":
                        couleur = "255=0=0";
                        break;
                    default:
                        couleur = "255=255=255";
                        break;
                }


                int position = spinner.getSelectedItemPosition();
                if (position == 1 || position == 2 || position == 3) {
                    String finalCouleur = couleur;
                    new Thread(() -> {
                        GameMessageManager.connect(IP.getText().toString(), 6969);

                        Intent intent;
                        if (position == 1) {
                            intent = new Intent(MainActivity.this, Controller.class);
                        } else if (position == 2){
                            intent = new Intent(MainActivity.this, ControllerJoystick.class);
                        } else {
                            intent = new Intent(MainActivity.this, DebugWindow.class);
                        }

                        intent.putExtra("NAME", "NAME=" + pseudo.getText().toString());
                        intent.putExtra("COL", "COL=" + finalCouleur);


                        runOnUiThread(() -> startActivity(intent));
                    }).start();
                }
            }
        });
    }
}