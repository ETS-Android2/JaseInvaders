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

        gamePreferences = GamePreferences.getInstance();
        nameText = findViewById(R.id.name_text);
        difficultyButton = findViewById(R.id.difficulty);
        soundButton = findViewById(R.id.sound);
        musicButton = findViewById(R.id.music);

        nameText.addTextChangedListener(textWatcher);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        difficultyButton.setText(gamePreferences.getDifficulty());

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
            case "EASY MODE":
                gamePreferences.setDifficulty(Diffuclty.MEDIUM);
                difficultyButton.setText(gamePreferences.getDifficulty());
                break;
            case "MEDIUM MODE":
                gamePreferences.setDifficulty(Diffuclty.HARD);
                difficultyButton.setText(gamePreferences.getDifficulty());
                break;
            case "HARD MODE":
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

    public void onMusicClicked(View view) {
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
        Twitter twitter = TwitterFactory.getSingleton();
        Status status = twitter.updateStatus("I stopped an invasion in " + gamePreferences.getPersonalBest() + " Seconds. #JaseInvaders");
        Toast.makeText(this, "Shared Personal Score", Toast.LENGTH_SHORT).show();
    }

    private final TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // before text changes
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // during text changes
        }

        @Override
        public void afterTextChanged(Editable s) {
            gamePreferences.setPlayerName(nameText.getText().toString());
        }
    };
}