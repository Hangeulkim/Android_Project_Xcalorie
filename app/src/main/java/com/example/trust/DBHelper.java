package com.example.trust;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 8;

    public DBHelper(Context context){
        super(context, "logdb", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String logSQL = "create table log (" +
                "_id integer primary key autoincrement, " +
                "title, " +
                "latitude, " +
                "longitude, " +
                "speed)";

        db.execSQL(logSQL);
        db.execSQL("insert into log (title, speed) values ('test0','87883')");
        db.execSQL("insert into log (title, latitude, longitude, speed) values ('test1','123.444444','456.777777','1213')");
        db.execSQL("insert into log (title, latitude, longitude, speed) values ('test2','433.445544','296.777555','1227')");
        db.execSQL("insert into log (title, latitude, longitude, speed) values (?, ?, ?, ?)", new String[]{"test3","284.445544","983.777555","2223"});

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION) {
            db.execSQL("drop table log");
            onCreate(db);
        }
    }
/*
    private final String TAG = "DBHelper";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table log (_id integer primary key autoincrement, name text, latitude text, longitude text, speed text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="drop table if exists log";
        db.execSQL(sql);
        onCreate(db);
    }

 */
}
