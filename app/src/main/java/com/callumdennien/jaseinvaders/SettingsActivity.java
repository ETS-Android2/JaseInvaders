package com.callumdennien.jaseinvaders;

import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        gamePreferences = GamePreferences.getInstance();
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

        nameText.setText(gamePreferences.getPlayerName());
        difficultyButton.setText(gamePreferences.getDifficulty());

        if (gamePreferences.getSoundEffects()) {
            soundButton.setText(R.string.sound_on);
        } else if (!(gamePreferences.getSoundEffects())) {
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
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onDifficultyClicked(View view) {
        switch (difficultyButton.getText().toString()) {
            case "Difficulty: Easy":
                gamePreferences.setDifficulty(Diffuclty.MEDIUM);
                difficultyButton.setText(gamePreferences.getDifficulty());
                break;
            case "Difficulty: Normal":
                gamePreferences.setDifficulty(Diffuclty.HARD);
                difficultyButton.setText(gamePreferences.getDifficulty());
                break;
            case "Difficulty: Hard":
                gamePreferences.setDifficulty(Diffuclty.EASY);
                difficultyButton.setText(gamePreferences.getDifficulty());
                break;
        }
    }

    public void onSoundClicked(View view) {
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

    public void onShareClicked(View view) throws TwitterException {
        Twitter twitter = TwitterFactory.getSingleton();
        Status status = twitter.updateStatus("I stopped an invasion in " + gamePreferences.getPersonalBest() + " Seconds. #JaseInvaders");
        Toast.makeText(this, "Shared Personal Score", Toast.LENGTH_SHORT).show();
    }
}