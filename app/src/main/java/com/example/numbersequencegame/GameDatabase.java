package com.example.numbersequencegame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class GameDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "Game History";
    public static final int DB_VERSION =  2;

    // Game Table
    public static final String TBL_GAME = "Game";
    public static final String CLN_GAME_ID = "id";
    public static final String CLN_GAME_NAME = "name";
    public static final String CLN_GAME_SCORE = "score";
    public static final String CLN_GAME_DATE = "date";


    public GameDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table "+TBL_GAME+" ( "+CLN_GAME_ID+" integer primary key autoincrement, "+
                CLN_GAME_NAME+" text not null, "+
                CLN_GAME_SCORE+" integer, "+
                CLN_GAME_DATE+" text not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists "+TBL_GAME);

    }

    public void insertGameHistory(GameVeribel gameVeribel){

        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CLN_GAME_NAME,gameVeribel.getName());
        values.put(CLN_GAME_SCORE,gameVeribel.getScore());
        values.put(CLN_GAME_DATE,gameVeribel.getDate());

        long result = database.insert(TBL_GAME,null,values);
    }

    public ArrayList<GameVeribel> getAllScore(){

        SQLiteDatabase database = getReadableDatabase();
        ArrayList<GameVeribel> gameVeribel = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from "+TBL_GAME, null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(CLN_GAME_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CLN_GAME_NAME));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(CLN_GAME_SCORE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(CLN_GAME_DATE));
                gameVeribel.add(new GameVeribel(id,name,score,date));
            }
            while (cursor.moveToNext());
            cursor.close();
        }
        return gameVeribel;
    }

    public  void  delete_tabl(){

        SQLiteDatabase database = getWritableDatabase();

        String query1 = "delete from " + TBL_GAME;
        String query2 = "delete from sqlite_sequence where name='" + TBL_GAME + "'";
        database.execSQL(query1);
        database.execSQL(query2);
        database.close();






    }

    public ArrayList<GameVeribel> getLastGameHistory(){

        SQLiteDatabase database = getReadableDatabase();
        ArrayList<GameVeribel> gameVeribel = new ArrayList<>();

        Cursor cursor = database.rawQuery("select * from "+TBL_GAME, null);

        if (cursor.moveToFirst()){
            do {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(CLN_GAME_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(CLN_GAME_NAME));
                int score = cursor.getInt(cursor.getColumnIndexOrThrow(CLN_GAME_SCORE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(CLN_GAME_DATE));

                gameVeribel.add(new GameVeribel(id,name,score,date));
            }
            while (cursor.moveToNext());
            cursor.close();


        }
        return gameVeribel;
    }
}