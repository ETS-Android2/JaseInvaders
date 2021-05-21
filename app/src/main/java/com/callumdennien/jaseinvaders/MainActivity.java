package com.callumdennien.jaseinvaders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate main menu to display items.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void onStartPressed(View view) {
        // create an intent for GameActivity, start the activity.
        Intent gameIntent = new Intent(this, GameActivity.class);
        startActivity(gameIntent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // check for menu item selected, start activity depending on choice.
        switch (item.getItemId()) {
            case R.id.settings_item:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.leader_board_item:
                Intent leaderBoardIntent = new Intent(this, LeaderBoardActivity.class);
                startActivity(leaderBoardIntent);
                break;
            case R.id.instructions_item:
                Intent instructionsIntent = new Intent(this, InstructionsActivity.class);
                startActivity(instructionsIntent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}