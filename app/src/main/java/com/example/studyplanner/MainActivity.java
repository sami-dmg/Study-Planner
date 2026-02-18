package com.example.studyplanner;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText subjectCountInput;
    Button generateTargets, proceedToActual, showProgress;
    LinearLayout targetLayout, actualLayout;
    TextView resultText;

    ArrayList<EditText> subjectNames = new ArrayList<>();
    ArrayList<EditText> targetHours = new ArrayList<>();
    ArrayList<EditText> actualHours = new ArrayList<>();

    int subjectCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subjectCountInput = findViewById(R.id.subjectCountInput);
        generateTargets = findViewById(R.id.generateTargets);
        proceedToActual = findViewById(R.id.proceedToActual);
        showProgress = findViewById(R.id.showProgress);
        targetLayout = findViewById(R.id.targetLayout);
        actualLayout = findViewById(R.id.actualLayout);
        resultText = findViewById(R.id.resultText);

        generateTargets.setOnClickListener(v -> {
            String input = subjectCountInput.getText().toString();
            if (TextUtils.isEmpty(input)) {
                Toast.makeText(this, "Enter subject count", Toast.LENGTH_SHORT).show();
                return;
            }

            subjectCount = Integer.parseInt(input);
            subjectNames.clear();
            targetHours.clear();
            targetLayout.removeAllViews();

            for (int i = 0; i < subjectCount; i++) {
                EditText sub = new EditText(this);
                sub.setHint("Subject " + (i + 1) + " Name");
                sub.setPadding(10, 10, 10, 10);
                sub.setBackgroundResource(android.R.drawable.editbox_background);
                sub.setTextColor(0xFF000000);

                EditText hour = new EditText(this);
                hour.setHint("Target Hour for Subject " + (i + 1));
                hour.setInputType(2); // number
                hour.setPadding(10, 10, 10, 10);
                hour.setBackgroundResource(android.R.drawable.editbox_background);
                hour.setTextColor(0xFF000000);

                targetLayout.addView(sub);
                targetLayout.addView(hour);

                subjectNames.add(sub);
                targetHours.add(hour);
            }

            proceedToActual.setVisibility(View.VISIBLE);
        });

        proceedToActual.setOnClickListener(v -> {
            actualHours.clear();
            actualLayout.removeAllViews();
            actualLayout.setVisibility(View.VISIBLE);

            for (int i = 0; i < subjectCount; i++) {
                EditText actual = new EditText(this);
                actual.setHint("Actual Hour for " + subjectNames.get(i).getText().toString());
                actual.setInputType(2);
                actual.setPadding(10, 10, 10, 10);
                actual.setBackgroundResource(android.R.drawable.editbox_background);
                actual.setTextColor(0xFF000000);

                actualLayout.addView(actual);
                actualHours.add(actual);
            }

            showProgress.setVisibility(View.VISIBLE);
        });

        showProgress.setOnClickListener(v -> {
            StringBuilder result = new StringBuilder("📊 Progress Report:\n\n");

            for (int i = 0; i < subjectCount; i++) {
                String name = subjectNames.get(i).getText().toString();
                float target = Float.parseFloat(targetHours.get(i).getText().toString());
                float actual = Float.parseFloat(actualHours.get(i).getText().toString());

                int percent = Math.round((actual / target) * 100);
                if (percent > 100) percent = 100;

                result.append("✅ ").append(name).append(": ").append(percent).append("%\n");
            }

            resultText.setText(result.toString());
        });
    }
}