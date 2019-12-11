package com.example.gamermanagement.db;

import android.provider.BaseColumns;

//【1】创建数据库元数据
public final class GameMetaData {
    private GameMetaData(){};
    public static abstract class GamePlayer implements BaseColumns{
        public static final String TABLE_NAME = "player_table";
        public static final String PLAYER = "player";
        public static final String SCORE = "score";
        public static final String LEVEL = "level";

    }
}
