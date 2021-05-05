package com.callumdennien.jaseinvaders;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private Button difficultyButton;
    private Button soundButton;
    private Button shareButton;
    private String currentDifficulty;
    private boolean soundToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        difficultyButton = findViewById(R.id.difficulty);
        soundButton = findViewById(R.id.sound);
        shareButton = findViewById(R.id.share);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Cancelled Changes", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            // Save to DataStream
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

    public void onShareClicked(View view) {
    }
}