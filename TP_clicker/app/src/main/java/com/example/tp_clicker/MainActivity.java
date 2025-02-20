package com.example.tp_clicker;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    TextView score, hightscore ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Mainscoreid), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });

        hightscore = findViewById(R.id.Textscoreid);
        score = findViewById(R.id.ScoreID);
        ImageButton Cookie = findViewById(R.id.cookieId);

        final int[] nbcookie = {0};
        // final RelativeLayout layout = findViewById(R.id.Mainscoreid);

        Cookie.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {


                gethightscore();
                nbcookie[0] = nbcookie[0] + 1;
                TextView text = findViewById(R.id.textId);
                text.setText("Vous avez "+ nbcookie[0] +" cookies");
                savehightscore();

            }

        });



    }
    private void gethightscore(){
        SharedPreferences Spref = getSharedPreferences("hightscore",MODE_PRIVATE);
        int main_score = Spref.getInt("hightscore", 0);
        score.setText(String.valueOf(main_score));
    };
    private void savehightscore(){
        SharedPreferences Spref = getSharedPreferences("hightscore",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = Spref.edit();
        myEdit.putInt("hightscore", Integer.parseInt(score.getText().toString()));
        myEdit.apply();
    };
}