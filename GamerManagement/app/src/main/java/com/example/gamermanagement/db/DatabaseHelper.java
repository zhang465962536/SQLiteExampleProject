package com.example.gamermanagement.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
//【2】创建 SQLiteOpenHelper 子类 创建表
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "game.db";
    private static final int VERSION = 1;

    private static final String CREATE_TABLE_PLAYER = "CREATE TABLE IF NOT EXISTS player_table("+
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "player TEXT,score INTEGER,level INTEGER)";

    private static final String DROP_TABLE_PLAYER = "DROP TABLE IF EXISTS player_table";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PLAYER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_PLAYER);
        db.execSQL(CREATE_TABLE_PLAYER);
    }
}
