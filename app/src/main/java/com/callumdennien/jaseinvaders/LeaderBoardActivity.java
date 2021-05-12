package com.callumdennien.jaseinvaders;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class LeaderBoardActivity extends AppCompatActivity {
    private SQLiteOpenHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        database = new SQLiteOpenHelper(this);
//        database.insertScore(database.getWritableDatabase(), "Callum", 24);

        HashMap<String, String> scores = database.queryScores();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Stuff
        return super.onOptionsItemSelected(item);
    }
}