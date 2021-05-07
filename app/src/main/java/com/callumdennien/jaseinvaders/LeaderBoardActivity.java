package com.callumdennien.jaseinvaders;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.MenuItem;

public class LeaderBoardActivity extends AppCompatActivity {
    private SQLiteOpenHelper database;
    private ContentValues highScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        database = new SQLiteOpenHelper(this);
        database.getReadableDatabase();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Stuff
        return super.onOptionsItemSelected(item);
    }
}