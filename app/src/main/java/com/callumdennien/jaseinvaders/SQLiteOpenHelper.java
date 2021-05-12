package com.callumdennien.jaseinvaders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

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
    }

    public void insertScore(SQLiteDatabase db, String valueName, int valueScore) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("SCORE", valueName);
        contentValues.put("NAME", valueScore);
        db.insert("SCORES", null, contentValues);
        db.close();
    }

    public HashMap<String, Integer> queryScores() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT NAME, SCORE FROM SCORES ORDER BY SCORE", null);

        HashMap<String, Integer> scores = new HashMap<>();

        if (cursor.moveToFirst()) {
            do {
                scores.put(cursor.getString(1), Integer.parseInt(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return scores;
    }
}
