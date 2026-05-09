package com.example.laba9;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etFullName, etAge;
    private SeekBar sbSalary;
    private TextView tvSelectedSalary, tvResult;
    private Button btnSubmitTest;

    private RadioButton rbtnQ1Correct, rbtnQ2Correct, rbtnQ3Correct, rbtnQ4Correct, rbtnQ5Correct;
    private CheckBox cbExperience, cbTesting, cbBusinessTrip;


    private final int MIN_COMPANY_SALARY = 1000;
    private final int MAX_COMPANY_SALARY = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ініціалізуємо віджети
        etFullName = findViewById(R.id.etFullName);
        etAge = findViewById(R.id.etAge);
        sbSalary = findViewById(R.id.sbSalary);
        tvSelectedSalary = findViewById(R.id.tvSelectedSalary);
        tvResult = findViewById(R.id.tvResult);
        btnSubmitTest = findViewById(R.id.btnSubmitTest);

        rbtnQ1Correct = findViewById(R.id.rbtnQ1Correct);
        rbtnQ2Correct = findViewById(R.id.rbtnQ2Correct);
        rbtnQ3Correct = findViewById(R.id.rbtnQ3Correct);
        rbtnQ4Correct = findViewById(R.id.rbtnQ4Correct);
        rbtnQ5Correct = findViewById(R.id.rbtnQ5Correct);

        cbExperience = findViewById(R.id.cbExperience);
        cbTesting = findViewById(R.id.cbTesting);
        cbBusinessTrip = findViewById(R.id.cbBusinessTrip);

        // Повзунок SeekBar для зарплати
        sbSalary.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSelectedSalary.setText("Обрана зарплата: " + progress + " USD");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        TextWatcher inputWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String name = etFullName.getText().toString().trim();
                String ageStr = etAge.getText().toString().trim();

                // Кнопка активна, лише якщо заповнені ПІБ та вік
                btnSubmitTest.setEnabled(!name.isEmpty() && !ageStr.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        etFullName.addTextChangedListener(inputWatcher);
        etAge.addTextChangedListener(inputWatcher);

        // Обробник натискання на кнопку "Здати тест"
        btnSubmitTest.setOnClickListener(v -> checkInterviewResult());
    }

    private void checkInterviewResult() {
        String fullName = etFullName.getText().toString().trim();
        int age = Integer.parseInt(etAge.getText().toString().trim());
        int salary = sbSalary.getProgress();

        StringBuilder resultMessage = new StringBuilder();
        boolean meetsRequirements = true;

        // 1. Валідація особистих даних кандидата за критеріями компанії
        if (age < 21 || age > 40) {
            meetsRequirements = false;
            resultMessage.append("Критерій відмови: невідповідний вік. Вимоги компанії: від 21 до 40 років (Ваш вік: ").append(age).append(").\n");
        }

        if (salary < MIN_COMPANY_SALARY || salary > MAX_COMPANY_SALARY) {
            meetsRequirements = false;
            resultMessage.append("Критерій відмови: очікувана зарплата поза дозволеними межами. Допустимо: від ")
                    .append(MIN_COMPANY_SALARY).append(" до ").append(MAX_COMPANY_SALARY)
                    .append(" USD (Ваш запит: ").append(salary).append(" USD).\n");
        }

        // 2. Підрахунок балів за тест та навички
        int totalScore = 0;

        // Тести (+2 бали за кожну правильну відповідь)
        if (rbtnQ1Correct.isChecked()) totalScore += 2;
        if (rbtnQ2Correct.isChecked()) totalScore += 2;
        if (rbtnQ3Correct.isChecked()) totalScore += 2;
        if (rbtnQ4Correct.isChecked()) totalScore += 2;
        if (rbtnQ5Correct.isChecked()) totalScore += 2;

        // Додаткові бали за досвід та навички
        if (cbExperience.isChecked()) totalScore += 2;      // Досвід від 2 років (+2 бали)
        if (cbTesting.isChecked()) totalScore += 1;         // Навички тестування (+1 бал)
        if (cbBusinessTrip.isChecked()) totalScore += 1;    // Готовність до відряджень (+1 бал)

        // 3. Формування фінального висновку
        if (!meetsRequirements) {
            // Кандидат не пройшов валідацію за віком або зарплатою
            resultMessage.append("\nВибачте, ви не проходите тест через невідповідність базовим вимогам компанії.");
            tvResult.setTextColor(getResources().getColor(R.color.error_red));
        } else {
            // Кандидат пройшов базову валідацію, перевіряємо бали
            resultMessage.append("Кандидат: ").append(fullName).append("\n");
            resultMessage.append("Набрано балів: ").append(totalScore).append(" з 14 можливих.\n\n");

            if (totalScore >= 10) {
                // Успішно складений тест (мінімум 10 балів)
                resultMessage.append("Вітаємо! Ви успішно склали попередній відбір.\n")
                        .append("Ось контакти нашого HR-відділу:\n")
                        .append("Телефон: +380 (50) 999-88-77\n")
                        .append("Email: jobs@futureit.ua");
                tvResult.setTextColor(getResources().getColor(R.color.success_green));
            } else {
                // Не набрав балів
                resultMessage.append("Вибачте, але Ви не набрали прохідний бал (необхідно мінімум 10). Спробуйте пізніше!");
                tvResult.setTextColor(getResources().getColor(R.color.error_red));
            }
        }

        // Відображаємо плашку з результатом
        tvResult.setText(resultMessage.toString());
        tvResult.setVisibility(View.VISIBLE);
    }
}