package com.callumdennien.jaseinvaders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences dataSource;
    private Button difficultyButton;
    private Button soundButton;
    private String currentDifficulty;
    private boolean soundToggle;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dataSource = getSharedPreferences("settings", Context.MODE_PRIVATE);
        difficultyButton = findViewById(R.id.difficulty);
        soundButton = findViewById(R.id.sound);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        soundToggle = dataSource.getBoolean("sound", true);
        currentDifficulty = dataSource.getString("difficulty", "Difficulty: Easy");
        score = dataSource.getInt("score", 999);

        difficultyButton.setText(currentDifficulty);

        if (soundToggle) {
            soundButton.setText(R.string.sound_on);
        } else {
            soundButton.setText(R.string.sound_off);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Cancelled Changes", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            dataSource.edit().clear().apply();
            dataSource.edit().putString("difficulty", currentDifficulty).apply();
            dataSource.edit().putBoolean("sound", soundToggle).apply();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDifficultyClicked(View view) {
        currentDifficulty = difficultyButton.getText().toString();

        switch (currentDifficulty) {
            case "Difficulty: Easy":
                currentDifficulty = "Difficulty: Normal";
                difficultyButton.setText(currentDifficulty);
                return;
            case "Difficulty: Normal":
                currentDifficulty = "Difficulty: Hard";
                difficultyButton.setText(currentDifficulty);
                return;
            case "Difficulty: Hard":
                currentDifficulty = "Difficulty: Easy";
                difficultyButton.setText(currentDifficulty);
        }
    }

    public void onSoundClicked(View view) {
        String soundMode = soundButton.getText().toString();

        switch (soundMode) {
            case "Sound: on":
                soundToggle = false;
                soundMode = "Sound: off";
                soundButton.setText(soundMode);
                return;
            case "Sound: off":
                soundToggle = true;
                soundMode = "Sound: on";
                soundButton.setText(soundMode);
        }
    }

    public void onShareClicked(View view) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.updateStatus("I just beat the game in " + score + " Seconds. #BeatThat");
    }
}