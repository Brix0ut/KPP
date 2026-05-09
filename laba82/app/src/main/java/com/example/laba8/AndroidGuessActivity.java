package com.example.laba8;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AndroidGuessActivity extends AppCompatActivity {
    private int min = 1;
    private int max = 100;
    private int currentGuess;
    private TextView tvAndroidGuess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_guess);

        tvAndroidGuess = findViewById(R.id.tvAndroidGuess);
        Button btnLess = findViewById(R.id.btnLess);
        Button btnMore = findViewById(R.id.btnMore);
        Button btnCorrect = findViewById(R.id.btnCorrect);

        makeGuess();

        btnLess.setOnClickListener(v -> {
            max = currentGuess - 1;
            makeGuess();
        });

        btnMore.setOnClickListener(v -> {
            min = currentGuess + 1;
            makeGuess();
        });

        btnCorrect.setOnClickListener(v -> {
            tvAndroidGuess.setText("Ура! Я вгадав число " + currentGuess + "!");
            btnLess.setEnabled(false);
            btnMore.setEnabled(false);
            btnCorrect.setEnabled(false);
            Toast.makeText(this, "Скайнет переміг!", Toast.LENGTH_SHORT).show();
        });
    }

    private void makeGuess() {
        if (min > max) {
            tvAndroidGuess.setText("Ти десь змахлював! 🤔");
            return;
        }
        currentGuess = (min + max) / 2;
        tvAndroidGuess.setText("Твоє число " + currentGuess + "?");
    }
}