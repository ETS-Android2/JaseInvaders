package com.callumdennien.jaseinvaders;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class LeaderBoardActivity extends AppCompatActivity {
    private SQLiteOpenHelper database;
    private ListView scoreList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        scoreList = findViewById(R.id.score_list);
        adapter = new ArrayAdapter<>(this, R.layout.score);
        scoreList.setAdapter(adapter);

        database = new SQLiteOpenHelper(this);
        HashMap<String, Integer> scores = database.queryScores();

        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            addScore(entry.getKey(), entry.getValue());
        }

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void addScore(String name, Integer score) {
        adapter.add(name + ": " + score + " Seconds");
    }
}