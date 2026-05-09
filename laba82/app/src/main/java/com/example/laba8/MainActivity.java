package com.example.laba8;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnUserGuesses = findViewById(R.id.btnUserGuesses);
        Button btnAndroidGuesses = findViewById(R.id.btnAndroidGuesses);

        btnUserGuesses.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, UserGuessActivity.class));
        });

        btnAndroidGuesses.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AndroidGuessActivity.class));
        });
    }
}