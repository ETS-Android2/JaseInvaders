package com.callumdennien.jaseinvaders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private SharedPreferences dataSource;
    private MathProblems mathProblems;
    private AudioManager audioManager;
    private Timer timer;
    private Handler handler;
    private boolean isRunning;
    private ProgressBar progressBar;
    private TextView questionView;
    private EditText answerText;
    private TextView timerView;
    private Integer problemAnswer;
    private final int speed = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        dataSource = getSharedPreferences("settings", Context.MODE_PRIVATE);
        mathProblems = new MathProblems();
        audioManager = new AudioManager(this);
        progressBar = findViewById(R.id.progressBar);
        questionView = findViewById(R.id.questionView);
        timerView = findViewById(R.id.timerView);
        answerText = findViewById(R.id.answerText);

        isRunning = false;
        enableTimer();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void enableTimer() {
        timer = new Timer();
        isRunning = true;
        handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    timer.tick();
                    timerView.setText(timer.toString());
                    handler.postDelayed(this, speed);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean sound = dataSource.getBoolean("sound", true);
        String difficulty = dataSource.getString("difficulty", "Difficulty: Easy");

        audioManager.toggle(sound);

        switch (difficulty) {
            case "Difficulty: Easy":
                mathProblems.setDifficulty(10);
                return;
            case "Difficulty: Normal":
                mathProblems.setDifficulty(20);
                return;
            case "Difficulty: Hard":
                mathProblems.setDifficulty(30);
        }

        createMathProblem();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Save Settings
    }

    private void createMathProblem() {
        Random random = new Random();
        int randomNumber = random.nextInt(4) + 1;

        switch (randomNumber) {
            case 1:
                updateViewAddition();
                break;
            case 2:
                updateViewSubtraction();
                break;
            case 3:
                updateViewMultiplication();
                break;
            case 4:
                updateViewDivision();
                break;
        }
    }

    private void updateViewDivision() {
        ArrayList<Integer> mathProblem = mathProblems.getDivision();
        String mathFormula = mathProblem.get(0) + " / " + mathProblem.get(1) + " =";
        questionView.setText(mathFormula);
        problemAnswer = mathProblem.get(2);
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
            if (!(progressBar.getProgress() == 10)) {
                // Shoot Turret
                // Play Damage Sound
                // Play Health Sound
                audioManager.play(Sound.laser);

                progressBar.setProgress(progressBar.getProgress() - 10);
                answerText.setText("");
                createMathProblem();

            } else {
                audioManager.play(Sound.bomb);

                isRunning = false;
                progressBar.setProgress(progressBar.getProgress() - 10);
                answerText.setText("");

                dataSource.edit().putString("score", timer.toString()).apply();
                // reset game
            }

        } else {
            System.out.println(problemAnswer);
            timer.tick();
            timerView.setText(timer.toString());
            audioManager.play(Sound.incorrect);
        }
    }
}