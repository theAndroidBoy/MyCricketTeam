package com.easyapps.CricketTeam.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.easyapps.CricketTeam.data.PlayersContract.*;

public class PlayersDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "waitlist.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public PlayersDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + Team.TABLE_NAME + " (" +
                Team._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Team.COLUMN_PLAYER_NAME + " TEXT NOT NULL, " +
                Team.COLUMN_PLAYER_AVERAGE + " INTEGER NOT NULL, " +
                Team.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Team.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}