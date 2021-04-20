package com.callumdennien.jaseinvaders;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private MathProblems mathProblems;
    private ProgressBar progressBar;
    private TextView questionView;
    private EditText answerText;
    private Integer problemAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mathProblems = new MathProblems();
        progressBar = findViewById(R.id.progressBar);
        questionView = findViewById(R.id.questionView);
        answerText = findViewById(R.id.answerText);
    }

    @Override
    protected void onStart() {
        super.onStart();

        createMathProblem();
    }

    private void createMathProblem() {
        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;

        switch (randomNumber) {
            case 1: updateViewAddition();
                break;
            case 2: updateViewSubtraction();
                break;
            case 3: updateViewMultiplication();
                break;
        }
    }

    private void updateViewMultiplication() {
        ArrayList<Integer> mathProblem = mathProblems.getMultiplication();
        String mathFormula = mathProblem.get(0) + " x " + mathProblem.get(1) + " =";
        questionView.setText(mathFormula);
        problemAnswer = mathProblem.get(2);
    }

    private void updateViewSubtraction() {
        ArrayList<Integer> mathProblem = mathProblems.getSubtraction();
        String mathFormula = mathProblem.get(0) + " - " + mathProblem.get(1) + " =";
        questionView.setText(mathFormula);
        problemAnswer = mathProblem.get(2);
    }

    private void updateViewAddition() {
        ArrayList<Integer> mathProblem = mathProblems.getAddition();
        String mathFormula = mathProblem.get(0) + " + " + mathProblem.get(1) + " =";
        questionView.setText(mathFormula);
        problemAnswer = mathProblem.get(2);
    }

    public void onFirePressed(View view) {
        String guess = answerText.getText().toString();

        if (guess.equals(String.valueOf(problemAnswer))) {
            createMathProblem();
            progressBar.setProgress(progressBar.getProgress() - 10);

        } else {
            Toast.makeText(this, String.valueOf(problemAnswer), Toast.LENGTH_SHORT).show();
        }
    }
}