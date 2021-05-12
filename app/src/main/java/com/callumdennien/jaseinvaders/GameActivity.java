package com.callumdennien.jaseinvaders;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private GamePreferences gamePreferences;
    private MathProblems mathProblems;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private Handler handler;
    private ProgressBar progressBar;
    private TextView questionView;
    private EditText answerText;
    private TextView timerView;
    private ImageView ufoView;
    private Integer problemAnswer;
    private boolean isRunning;
    private final int speed = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gamePreferences = GamePreferences.getInstance();
        mathProblems = new MathProblems();
        audioManager = new AudioManager(this);
        progressBar = findViewById(R.id.progressBar);
        questionView = findViewById(R.id.questionView);
        timerView = findViewById(R.id.timerView);
        answerText = findViewById(R.id.answerText);
        ufoView = findViewById(R.id.ufoView);

        isRunning = false;
        enableTimer();
        Glide.with(this).load(R.drawable.ufo_boss).into(ufoView);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void playMusic() {
        if (gamePreferences.getMusic()) {
            mediaPlayer = MediaPlayer.create(this, R.raw.game_background);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    private void enableTimer() {
        timer = new Timer();
        handler = new Handler();
        isRunning = true;

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
        audioManager.toggle(gamePreferences.getSoundEffects());
        playMusic();

        switch (gamePreferences.getDifficulty()) {
            case "EASY MODE":
                mathProblems.setDifficulty(10);
                break;
            case "MEDIUM MODE":
                mathProblems.setDifficulty(20);
                break;
            case "HARD MODE":
                mathProblems.setDifficulty(30);
                break;
        }

        createMathProblem();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
        // Save Settings/Time
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
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
                // TODO: DRAW IN INVADER
                audioManager.play(Sound.laser);

                progressBar.setProgress(progressBar.getProgress() - 10);
                answerText.setText("");
                createMathProblem();

            } else {
                audioManager.play(Sound.bomb);

                isRunning = false;
                progressBar.setProgress(progressBar.getProgress() - 10);
                answerText.setText("");

                if (timer.getScore() > gamePreferences.getPersonalBest()) {
                    gamePreferences.setPersonalBest(timer.getScore());
                }

//                TODO: Add score to DataBase.

                Toast toast = Toast.makeText(this, "Beat Invasion In " + timer.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                finish();
            }

        } else {
            System.out.println(problemAnswer);
            timer.tick();
            timerView.setText(timer.toString());
            audioManager.play(Sound.incorrect);
        }
    }
}