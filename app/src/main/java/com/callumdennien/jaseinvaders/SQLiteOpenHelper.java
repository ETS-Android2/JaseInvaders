package com.callumdennien.jaseinvaders;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    private static final String DB_NAME = "jaseScores";
    private static final int DB_VERSION = 1;

    public SQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE SCORES (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME TEXT, " + "SCORE INTEGER);");
//        ContentValues highScores = new ContentValues();
//        highScores.put("NAME", name);
//        highScores.put("SCORE", score);
//        db.insert("SCORES", null, highScores);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
