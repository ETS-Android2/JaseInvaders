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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
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
    private Random random;
    private TextView timerView;
    private ProgressBar progressBar;
    private TextView questionView;
    private TextView optionOneView;
    private TextView optionTwoView;
    private TextView optionThreeView;
    private TextView optionFourView;
    private ImageView ufoView;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // initialise member variables.
        gamePreferences = GamePreferences.getInstance();
        mathProblems = new MathProblems();
        audioManager = new AudioManager(this);
        random = new Random();
        progressBar = findViewById(R.id.progress_bar);
        questionView = findViewById(R.id.question_view);
        optionOneView = findViewById(R.id.option_one);
        optionTwoView = findViewById(R.id.option_two);
        optionThreeView = findViewById(R.id.option_three);
        optionFourView = findViewById(R.id.option_four);
        timerView = findViewById(R.id.timer_view);
        ufoView = findViewById(R.id.ufo_view);

        // initialise sensor manager, setup accelerometer sensor with listener.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        // setup game screen
        enableTimer();
        drawUFO();
        setupGame();

        // Add button to return to MainActivity.
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        // check preferences for music and sound, then play if toggled on.
        super.onStart();
        audioManager.toggle(gamePreferences.getSoundEffects());
        playMusic(gamePreferences.getMusic());

        createToast("Shake Screen to Change/Reset Question");
    }

    @Override
    protected void onPause() {
        // stop listening for sensor data, save current timer score, stop music.
        super.onPause();
        sensorManager.unregisterListener(this);
        gamePreferences.setCurrentScore(timer.getScore());

        if (gamePreferences.getMusic()) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onResume() {
        // start listening for sensor data, apply saved timer score to current timer.
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        timer.set(gamePreferences.getCurrentScore());
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // detect shake of device.
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gravityEarth = SensorManager.GRAVITY_EARTH;
        float accelerometer = (float) Math.sqrt(x * x + y * y + z * z);
        float movement = accelerometer - gravityEarth;
        float phoneShake = 10f * 0.9f + movement;

        // if the shake is large enough, generate new math problem.
        // change to "phoneShake > 10" if using emulator instead of physical device.
        if (phoneShake > 20) {
            gamePreferences.setAnsweredQuestion(false);
            createMathProblem();
            displayAnswers();
            changeButtonState(true);
            questionView.setText(gamePreferences.getCurrentQuestion());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(sensor.getName(), String.valueOf(accuracy));
    }

    private void setupGame() {
        // set math problem difficulty level based on game difficulty, create math problem.
        mathProblems.setDifficulty(gamePreferences.getDifficulty().difficultyValue);
        createMathProblem();

        // update question view and preferences.
        displayAnswers();
        gamePreferences.setAnsweredQuestion(false);
        questionView.setText(gamePreferences.getCurrentQuestion());
    }

    private void enableTimer() {
        // create new handler runnable to increase timer score.
        timer = new Timer();
        handler = new Handler();
        isRunning = true;
        gamePreferences.setCurrentScore(0);

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
        // use glide api to draw ufo to screen.
        Glide.with(this).load(R.drawable.ufo_boss).into(ufoView);
    }

    private void playMusic(boolean playing) {
        // while music is toggled on, play game background music.
        if (playing) {
            mediaPlayer = MediaPlayer.create(this, R.raw.game_background);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
    }

    private void createMathProblem() {
        // generate random math problem, update question view and preferences.
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

    private void displayAnswers() {
        // generate one correct answer and multiple incorrect answers.
        int optionCorrect = random.nextInt(4) + 1;
        ArrayList<String> optionsIncorrect = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            if (i % 2 == 0) {
                optionsIncorrect.add(String.valueOf(gamePreferences.getCurrentAnswer() + i));

            } else {
                optionsIncorrect.add(String.valueOf(gamePreferences.getCurrentAnswer() - i));

            }
        }

        // randomise the order of the incorrect answers, assign them to the options
        Collections.shuffle(optionsIncorrect);
        optionOneView.setText(optionsIncorrect.get(1));
        optionTwoView.setText(optionsIncorrect.get(2));
        optionThreeView.setText(optionsIncorrect.get(3));
        optionFourView.setText(optionsIncorrect.get(4));

        // assign correct answer to random option.
        switch (optionCorrect) {
            case 1:
                optionOneView.setText(String.valueOf(gamePreferences.getCurrentAnswer()));
                break;
            case 2:
                optionTwoView.setText(String.valueOf(gamePreferences.getCurrentAnswer()));
                break;
            case 3:
                optionThreeView.setText(String.valueOf(gamePreferences.getCurrentAnswer()));
                break;
            case 4:
                optionFourView.setText(String.valueOf(gamePreferences.getCurrentAnswer()));
                break;
        }
    }

    public void onOptionClicked(View view) {
        // guess option guess, check against answer.
        TextView guessView = (TextView) view;
        int guess = Integer.parseInt(guessView.getText().toString());

        if (guess == gamePreferences.getCurrentAnswer() && gamePreferences.getAnsweredQuestion()) {
            correctGuess();
        } else {
            incorrectGuess();
        }
    }

    private void correctGuess() {
        gamePreferences.setAnsweredQuestion(true);
        changeButtonState(false);

        // play game sounds, remove ufo health from progress bar.
        if (audioManager.isReady()) {
            if (progressBar.getProgress() > 10) {
                audioManager.play(Sound.laser);
                audioManager.play(Sound.grunt);
                progressBar.setProgress(progressBar.getProgress() - 10);

            } else {
                isRunning = false;
                audioManager.play(Sound.bomb);
                progressBar.setProgress(progressBar.getProgress() - 10);

                // on invader defeat, save score to database and update personal best.
                gamePreferences.setPersonalBest(Math.min(timer.getScore(), gamePreferences.getPersonalBest()));
                SQLiteOpenHelper database = new SQLiteOpenHelper(this);
                database.insertScore(database.getWritableDatabase(), gamePreferences.getPlayerName(), timer.getScore());
                database.close();

                // return to MainActivity.
                createToast("Beat Invasion In " + timer.toString());
                finish();
            }
        }
    }

    private void changeButtonState(boolean clickable) {
        // toggle text colour and button state.
        int textColour = getResources().getColor(R.color.white);

        if (!clickable) {
            textColour = getResources().getColor(R.color.light_grey);
        }

        optionOneView.setClickable(clickable);
        optionOneView.setTextColor(textColour);

        optionTwoView.setClickable(clickable);
        optionTwoView.setTextColor(textColour);

        optionThreeView.setClickable(clickable);
        optionThreeView.setTextColor(textColour);

        optionFourView.setClickable(clickable);
        optionFourView.setTextColor(textColour);
    }

    private void incorrectGuess() {
        // increase timer score if incorrect, play game sounds.
        timer.tick();
        timerView.setText(timer.toString());

        if (audioManager.isReady()) {
            audioManager.play(Sound.incorrect);
        }
    }

    private void createToast(String message) {
        // create toast using supplied message.
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}