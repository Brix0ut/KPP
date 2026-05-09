package com.example.laba8;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class UserGuessActivity extends AppCompatActivity {
    private int targetNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guess);

        TextView tvPrompt = findViewById(R.id.tvPrompt);
        EditText etUserInput = findViewById(R.id.etUserInput);
        Button btnCheck = findViewById(R.id.btnCheck);

        targetNumber = new Random().nextInt(100) + 1;

        btnCheck.setOnClickListener(v -> {
            String input = etUserInput.getText().toString();
            if (input.isEmpty()) return;

            int userGuess = Integer.parseInt(input);

            if (userGuess > targetNumber) {
                tvPrompt.setText("Моє число МЕНШЕ ніж " + userGuess);
            } else if (userGuess < targetNumber) {
                tvPrompt.setText("Моє число БІЛЬШЕ ніж " + userGuess);
            } else {
                tvPrompt.setText("🎉 Правильно! Це число " + targetNumber + "!");
                btnCheck.setEnabled(false);
                Toast.makeText(this, "Ти переміг!", Toast.LENGTH_SHORT).show();
            }
            etUserInput.setText("");
        });
    }
}