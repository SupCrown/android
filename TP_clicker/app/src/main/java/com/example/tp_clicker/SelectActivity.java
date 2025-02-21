package com.example.tp_clicker;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SelectActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectmode);


        Spinner spinner = findViewById(R.id.spinnerid);
        String[] options = {"Sélectionnez une page", "Mode Normal", "Mode Infernal", "Mode Speed"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {

                    Intent intent = new Intent(SelectActivity.this, MainActivity.class);
                    startActivity(intent);

                } else if (position == 2) {

                    Intent intent = new Intent(SelectActivity.this, ModeInfernalActivity.class);
                    startActivity(intent);

                } else if (position == 3) {

                    Intent intent = new Intent(SelectActivity.this, ModeSpeedActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
