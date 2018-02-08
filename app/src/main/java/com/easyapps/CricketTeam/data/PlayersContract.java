package com.easyapps.CricketTeam.data;

import android.provider.BaseColumns;

public class PlayersContract {

    public static final class Team implements BaseColumns {
        public static final String TABLE_NAME = "team";
        public static final String COLUMN_PLAYER_NAME = "playerName";
        public static final String COLUMN_PLAYER_AVERAGE = "playerAverage";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
