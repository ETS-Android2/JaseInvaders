package com.callumdennien.jaseinvaders;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements SensorEventListener {
    private GamePreferences gamePreferences;
    private SensorManager sensorManager;
    private Sensor sensor;
    private MathProblems mathProblems;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private Timer timer;
    private Handler handler;
    private TextView timerView;
    private ProgressBar progressBar;
    private TextView questionView;
    private EditText answerText;
    private ImageView ufoView;
    private boolean isRunning;

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

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        isRunning = false;
        enableTimer();
        drawUFO();

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
        createToast("Shake Screen to Change/Reset Question");
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        gamePreferences.setCurrentScore(timer.getScore());

        if (gamePreferences.getMusic()) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        timer.add(gamePreferences.getCurrentScore());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gravityEarth = SensorManager.GRAVITY_EARTH;
        float accelerometer = (float) Math.sqrt(x * x + y * y + z * z);
        float movement = accelerometer - gravityEarth;
        float phoneShake = 10f * 0.9f + movement;

        if (phoneShake > 10) {
            gamePreferences.setAnsweredQuestion(false);
            answerText.setText("");
            createMathProblem();
            questionView.setText(gamePreferences.getCurrentQuestion());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(sensor.getName(), String.valueOf(accuracy));
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
            if (!gamePreferences.getAnsweredQuestion()) {
                damageUFO();
            }

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

    private void drawUFO() {
        Glide.with(this).load(R.drawable.ufo_boss).into(ufoView);
    }

    private void playMusic(boolean playing) {
        if (playing) {
            mediaPlayer = MediaPlayer.create(this, R.raw.game_background);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    private void damageUFO() {
        gamePreferences.setAnsweredQuestion(true);

        if (audioManager.isReady()) {
            if (progressBar.getProgress() > 10) {
                audioManager.play(Sound.laser);
                audioManager.play(Sound.grunt);
                progressBar.setProgress(progressBar.getProgress() - 10);

            } else {
                isRunning = false;
                audioManager.play(Sound.bomb);
                progressBar.setProgress(progressBar.getProgress() - 10);

                gamePreferences.setPersonalBest(Math.max(timer.getScore(), gamePreferences.getPersonalBest()));
                SQLiteOpenHelper database = new SQLiteOpenHelper(this);
                database.insertScore(database.getWritableDatabase(), gamePreferences.getPlayerName(), timer.getScore());
                database.close();

                createToast("Beat Invasion In " + timer.toString());
                finish();
            }
        }
    }

    private void createToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}