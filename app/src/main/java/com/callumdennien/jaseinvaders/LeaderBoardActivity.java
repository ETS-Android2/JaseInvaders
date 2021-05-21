package com.callumdennien.jaseinvaders;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class LeaderBoardActivity extends AppCompatActivity {
    private SQLiteOpenHelper database;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        // initialise database and array adapter.
        database = new SQLiteOpenHelper(this);
        adapter = new ArrayAdapter<>(this, R.layout.score);

        // initialise the activity list view, add database scores to adapter.
        ListView scoreList = findViewById(R.id.score_list);
        scoreList.setAdapter(adapter);
        getLeaderBoard();

        // Add button to return to MainActivity.
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getLeaderBoard() {
        // query database for scores, append scores to list adapter.
        HashMap<String, Integer> scores = database.queryScores();

        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            addScore(entry.getKey(), entry.getValue());
        }
    }

    public void addScore(String name, Integer score) {
        adapter.add(name + ": " + score + " Seconds");
    }
}