package com.example.gamermanagement.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gamermanagement.entity.GamePlayer;

import java.util.ArrayList;

//【4】编写数据库操作工具类  增删改查
public class DatabaseAdapter {

    private DatabaseHelper dbHelper;

    public DatabaseAdapter(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }


    //使用原始SQL语句进行查询
    public void rawadd(GamePlayer gamePlayer){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into player_table(player,score,level) values(?,?,?)";
        Object[] args = {gamePlayer.getPlayer(),gamePlayer.getScore(),gamePlayer.getLevel()};
        db.execSQL(sql,args);
        db.close();
    }

    //添加操作
    public void add(GamePlayer gamePlayer) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GameMetaData.GamePlayer.PLAYER, gamePlayer.getPlayer());
        values.put(GameMetaData.GamePlayer.SCORE, gamePlayer.getScore());
        values.put(GameMetaData.GamePlayer.LEVEL, gamePlayer.getLevel());
        db.insert(GameMetaData.GamePlayer.TABLE_NAME, null, values);
        db.close();
    }

    //使用原始SQL语句进行 删除
    public void rawdelete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from player_table where _id =?";
        Object[] args ={id};
        db.execSQL(sql,args);
        db.close();
    }

    //删除操作
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereclause = GameMetaData.GamePlayer._ID + "=?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete(GameMetaData.GamePlayer.TABLE_NAME, whereclause, whereArgs);
        db.close();
    }

    //使用SQL原始语句 进行更新
    public void rawupdate(GamePlayer gamePlayer){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "update player_table set player =?,score =?,level =? where _id =?";
        Object[] args = {gamePlayer.getId()};
        db.execSQL(sql,args);
        db.close();
    }

    //更新操作
    public void update(GamePlayer gamePlayer) {
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(GameMetaData.GamePlayer.PLAYER,gamePlayer.getPlayer());
        values.put(GameMetaData.GamePlayer.SCORE,gamePlayer.getScore());
        values.put(GameMetaData.GamePlayer.LEVEL,gamePlayer.getLevel());
        String whereClause=GameMetaData.GamePlayer._ID+"=?";
        String[] whereArgs={String.valueOf(gamePlayer.getId())};
        //表名，ContentValues,条件，条件的值
        db.update(GameMetaData.GamePlayer.TABLE_NAME,values,whereClause,whereArgs);
        db.close();
    }


    //条件查询
    public GamePlayer findById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql="select _id,player,score,level from player_table where _id =?";
        //使用SQLite内部查询
        //是否去除重复记录，表名，要查询的列，查询条件，查询条件的值，分组条件，分组条件的值，排序，分页条件
        //Cursor c= db.query(true,GameMetaData.GamePlayer.TABLE_NAME,null,GameMetaData.GamePlayer._ID+"=?",new String[]{String.valueOf(id)},null,null,null,null);
        //原生SQL 语句
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        GamePlayer gamePlayer = null;
        if (cursor.moveToNext()) {
            gamePlayer=new GamePlayer();
            gamePlayer.setId(cursor.getInt(cursor.getColumnIndexOrThrow(GameMetaData.GamePlayer._ID)));
            gamePlayer.setPlayer(cursor.getString(cursor.getColumnIndexOrThrow(GameMetaData.GamePlayer.PLAYER)));
            gamePlayer.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(GameMetaData.GamePlayer.SCORE)));
            gamePlayer.setLevel(cursor.getInt(cursor.getColumnIndexOrThrow(GameMetaData.GamePlayer.LEVEL)));
        }
        cursor.close();
        db.close();
        return gamePlayer;
    }


    //全部查询
    public ArrayList<GamePlayer> findAll(){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="select _id,player,score,level from player_table order by score desc";
        Cursor cursor=db.rawQuery(sql,null);
        ArrayList<GamePlayer> gamePlayers=new ArrayList<>();
        GamePlayer gamePlayer=null;
        while (cursor.moveToNext()){
            gamePlayer=new GamePlayer();
            gamePlayer.setId(cursor.getInt(cursor.getColumnIndexOrThrow(GameMetaData.GamePlayer._ID)));
            gamePlayer.setPlayer(cursor.getString(cursor.getColumnIndexOrThrow(GameMetaData.GamePlayer.PLAYER)));
            gamePlayer.setScore(cursor.getInt(cursor.getColumnIndexOrThrow(GameMetaData.GamePlayer.SCORE)));
            gamePlayer.setLevel(cursor.getInt(cursor.getColumnIndexOrThrow(GameMetaData.GamePlayer.LEVEL)));
            gamePlayers.add(gamePlayer);
        }
        cursor.close();
        db.close();
        return gamePlayers;
    }

    //获取玩家数目
    public int getCount(){
        int count = 0;
        String sql = "select count(_id) from player_table";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count;
    }

}
