package com.callumdennien.jaseinvaders;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView timerView;
    private Handler handler;
    private boolean isRunning;
    private ProgressBar progressBar;
    private TextView questionView;
    private EditText answerText;
//    private int currentAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
//
        gamePreferences = GamePreferences.getInstance();
        mathProblems = new MathProblems();
        audioManager = new AudioManager(this);
        progressBar = findViewById(R.id.progressBar);
        questionView = findViewById(R.id.questionView);
        timerView = findViewById(R.id.timerView);
        answerText = findViewById(R.id.answerText);
//        ImageView ufoView = findViewById(R.id.ufoView);
//
        isRunning = false;
        enableTimer();
//        Glide.with(this).load(R.drawable.ufo_boss).into(ufoView);
//
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        audioManager.toggle(gamePreferences.getSoundEffects());
        playMusic(gamePreferences.getMusic());

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

        if (gamePreferences.getCurrentQuestion().equals("1 / 1 =")) {
            createMathProblem();
        }

        questionView.setText(gamePreferences.getCurrentQuestion());
    }

    @Override
    protected void onPause() {
        super.onPause();
        gamePreferences.setCurrentScore(timer.getScore());

        if (gamePreferences.getMusic()) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        timer.add(gamePreferences.getCurrentScore());
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

    private void updateViewAddition() {
        ArrayList<Integer> currentProblem = mathProblems.getAddition();
        gamePreferences.setCurrentQuestion(currentProblem.get(0) + " + " + currentProblem.get(1) + " =");
        gamePreferences.setCurrentAnswer(currentProblem.get(2));
    }

    private void updateViewSubtraction() {
        ArrayList<Integer> currentProblem = mathProblems.getSubtraction();
        gamePreferences.setCurrentQuestion(currentProblem.get(0) + " - " + currentProblem.get(1) + " =");
        gamePreferences.setCurrentAnswer(currentProblem.get(2));
    }

    private void updateViewMultiplication() {
        ArrayList<Integer> currentProblem = mathProblems.getMultiplication();
        gamePreferences.setCurrentQuestion(currentProblem.get(0) + " x " + currentProblem.get(1) + " =");
        gamePreferences.setCurrentAnswer(currentProblem.get(2));
    }

    private void updateViewDivision() {
        ArrayList<Integer> currentProblem = mathProblems.getDivision();
        gamePreferences.setCurrentQuestion(currentProblem.get(0) + " / " + currentProblem.get(1) + " =");
        gamePreferences.setCurrentAnswer(currentProblem.get(2));
    }

    public void onFirePressed(View view) {
        String guess = answerText.getText().toString();

        if (!guess.equals("") && Integer.parseInt(guess) == gamePreferences.getCurrentAnswer()) {
            damageUFO();
        } else {
            timer.tick();
            timerView.setText(timer.toString());

            if (audioManager.isReady()) {
                audioManager.play(Sound.incorrect);
            }
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
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void playMusic(boolean playing) {
        if (playing) {
            mediaPlayer = MediaPlayer.create(this, R.raw.game_background);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    private void damageUFO() {
        if (audioManager.isReady()) {
            if (progressBar.getProgress() > 10) {
                audioManager.play(Sound.laser);
                audioManager.play(Sound.grunt);
                progressBar.setProgress(progressBar.getProgress() - 10);
                answerText.setText("");

            } else {
                isRunning = false;
                audioManager.play(Sound.bomb);
                progressBar.setProgress(progressBar.getProgress() - 10);

                gamePreferences.setPersonalBest(Math.max(timer.getScore(), gamePreferences.getPersonalBest()));
                // TODO: Create Database input

                Toast toast = Toast.makeText(this, "Beat Invasion In " + timer.toString(), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                finish();
            }
        }
    }
}