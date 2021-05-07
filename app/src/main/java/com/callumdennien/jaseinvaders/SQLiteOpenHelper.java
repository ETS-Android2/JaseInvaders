package com.callumdennien.jaseinvaders;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
