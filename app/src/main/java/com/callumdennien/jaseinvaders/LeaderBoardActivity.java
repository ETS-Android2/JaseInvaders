package com.callumdennien.jaseinvaders;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class LeaderBoardActivity extends AppCompatActivity {
    private SQLiteOpenHelper database;
    private ListView scoreList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        scoreList = findViewById(R.id.score_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        scoreList.setAdapter(adapter);

        adapter.add("Callum: 1000 - Easy");
        adapter.add("Bob: 1000 - Easy");


//        database = new SQLiteOpenHelper(this);
//        database.insertScore(database.getWritableDatabase(), "Callum", 24);
//        HashMap<String, String> scores = database.queryScores();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}