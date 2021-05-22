package com.callumdennien.jaseinvaders;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class SettingsActivity extends AppCompatActivity {
    private GamePreferences gamePreferences;
    private EditText nameText;
    private Button difficultyButton;
    private Button soundButton;
    private Button musicButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // initialise customisable fields and game preferences.
        gamePreferences = GamePreferences.getInstance();
        nameText = findViewById(R.id.name_text);
        difficultyButton = findViewById(R.id.difficulty);
        soundButton = findViewById(R.id.sound);
        musicButton = findViewById(R.id.music);

        // create a listener to update player name.
        nameText.addTextChangedListener(textWatcher);

        // Add button to return to MainActivity.
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        // initialise all current settings stored in gamePreferences.
        super.onStart();
        difficultyButton.setText(gamePreferences.getDifficulty().difficultyText);

        if (!gamePreferences.getPlayerName().equals("Anonymous")) {
            nameText.setText(gamePreferences.getPlayerName());
        }

        if (gamePreferences.getSoundEffects()) {
            soundButton.setText(R.string.sound_on);
        } else if (!(gamePreferences.getSoundEffects())) {
            soundButton.setText(R.string.sound_off);
        }

        if (gamePreferences.getMusic()) {
            musicButton.setText(R.string.music_on);
        } else if (!(gamePreferences.getMusic())) {
            musicButton.setText(R.string.music_off);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // return to MainActivity.
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDifficultyClicked(View view) {
        // toggle game difficulty based on Difficulty enum, set text.
        switch (difficultyButton.getText().toString()) {
            case "EASY MODE":
                gamePreferences.setDifficulty(Difficulty.MEDIUM);
                difficultyButton.setText(gamePreferences.getDifficulty().difficultyText);
                break;
            case "MEDIUM MODE":
                gamePreferences.setDifficulty(Difficulty.HARD);
                difficultyButton.setText(gamePreferences.getDifficulty().difficultyText);
                break;
            case "HARD MODE":
                gamePreferences.setDifficulty(Difficulty.EASY);
                difficultyButton.setText(gamePreferences.getDifficulty().difficultyText);
                break;
        }
    }

    public void onSoundClicked(View view) {
        // toggle sound button on/off, set text.
        switch (soundButton.getText().toString()) {
            case "Sound: on":
                gamePreferences.setSoundEffects(false);
                soundButton.setText(R.string.sound_off);
                break;
            case "Sound: off":
                gamePreferences.setSoundEffects(true);
                soundButton.setText(R.string.sound_on);
                break;
        }
    }

    public void onMusicClicked(View view) {
        // toggle music button on/off, set text.
        switch (musicButton.getText().toString()) {
            case "Music: on":
                gamePreferences.setMusic(false);
                musicButton.setText(R.string.music_off);
                break;
            case "Music: off":
                gamePreferences.setMusic(true);
                musicButton.setText(R.string.music_on);
                break;
        }
    }

    public void onShareClicked(View view) throws TwitterException {
        // check current tweet status isn't a duplicate, then tweet current score.
        Twitter twitter = TwitterFactory.getSingleton();
        Status currentStatus = twitter.getHomeTimeline().get(0);
        String latestUpdate = "I stopped an invasion in " + gamePreferences.getPersonalBest() + " Seconds. #JaseInvaders";

        if (!currentStatus.getText().equals(latestUpdate)) {
            if (gamePreferences.getPersonalBest() == 999) {
                createToast("No Score Available");

            } else {
                twitter.updateStatus(latestUpdate);
                createToast("Sharing Personal Best");
            }

        } else {
            createToast("Duplicate Tweet Detected");
        }
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // detect before text changes.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // detect during text changes.
        }

        @Override
        public void afterTextChanged(Editable s) {
            // after player finishes typing, update player name.
            gamePreferences.setPlayerName(nameText.getText().toString());
        }
    };

    private void createToast(String message) {
        // create toast using supplied message.
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}