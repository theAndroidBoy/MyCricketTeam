package com.easyapps.CricketTeam.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }

        ContentValues contentValues = new ContentValues();

        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_NAME, "Chris Lyn");
        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_AVERAGE, 33);
        db.insert(PlayersContract.Team.TABLE_NAME, null,
                contentValues);

        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_NAME, "Maxwell");
        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_AVERAGE, 41);
        db.insert(PlayersContract.Team.TABLE_NAME, null,
                contentValues);

        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_NAME, "Dean Elgar");
        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_AVERAGE, 36);
        db.insert(PlayersContract.Team.TABLE_NAME, null,
                contentValues);

        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_NAME, "Markram");
        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_AVERAGE, 23);
        db.insert(PlayersContract.Team.TABLE_NAME, null,
                contentValues);

        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_NAME, "Dumminy");
        contentValues.put(PlayersContract.Team.COLUMN_PLAYER_AVERAGE, 45);
        db.insert(PlayersContract.Team.TABLE_NAME, null,
                contentValues);

    }
}