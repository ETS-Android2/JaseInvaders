package com.callumdennien.jaseinvaders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.TreeMap;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    private static final String DB_NAME = "jaseScores";
    private static final int DB_VERSION = 1;

    public SQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SCORES (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME TEXT, " + "SCORE INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS SCORES");
        onCreate(db);
    }

    public void insertScore(SQLiteDatabase db, String valueName, int valueScore) {
        // Insert method value and score into scores database.
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", valueName);
        contentValues.put("SCORE", valueScore);
        db.insert("SCORES", null, contentValues);
        db.close();
    }

    public TreeMap<String, Integer> queryScores() {
        // Get readable database and grab all scores through a cursor.
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM SCORES ORDER BY SCORE", null);

        TreeMap<String, Integer> scores = new TreeMap<>();

        if (cursor.moveToFirst()) {
            int count = 0;

            do {
                count++;
                scores.put(count + ") " + cursor.getString(1), cursor.getInt(2));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return scores;
    }
}
