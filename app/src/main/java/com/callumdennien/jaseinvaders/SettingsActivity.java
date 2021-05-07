package com.callumdennien.jaseinvaders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences dataSource;
    private EditText nameText;
    private Button difficultyButton;
    private Button soundButton;
    private boolean soundToggle;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dataSource = getSharedPreferences("settings", Context.MODE_PRIVATE);
        nameText = findViewById(R.id.name_text);
        difficultyButton = findViewById(R.id.difficulty);
        soundButton = findViewById(R.id.sound);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        nameText.setText(dataSource.getString("name", "Anonymous"));
        soundToggle = dataSource.getBoolean("sound", true);
        difficultyButton.setText(dataSource.getString("difficulty", "Difficulty: Easy"));
        score = dataSource.getInt("score", 999);


        if (soundToggle) {
            soundButton.setText(R.string.sound_on);
        } else {
            soundButton.setText(R.string.sound_off);
        }
    }

    @Override
    public void onBackPressed() {
        Toast toast = Toast.makeText(this, "Cancelled Changes", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            dataSource.edit().clear().apply();
            dataSource.edit().putString("name", nameText.getText().toString()).apply();
            dataSource.edit().putString("difficulty", difficultyButton.getText().toString()).apply();
            dataSource.edit().putBoolean("sound", soundToggle).apply();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDifficultyClicked(View view) {
        String currentDifficulty = difficultyButton.getText().toString();

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
        Status status = twitter.updateStatus("I stopped an invasion in " + score + " Seconds. #JaseInvaders");
        Toast.makeText(this, "Shared Personal Score", Toast.LENGTH_SHORT).show();
    }
}