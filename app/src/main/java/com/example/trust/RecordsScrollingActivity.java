package com.example.trust;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class RecordsScrollingActivity extends AppCompatActivity {

    private final String TAG = "RecordsScr_Activity";
    public ListView mLvDatabase = null;
    SQLiteDatabase db = null;
    DBHelper helper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLvDatabase = findViewById(R.id.lv_database);
        //mLvDatabase.setOnItemLongClickListener(this);


        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from log", null);
        cursor.moveToFirst();
        cursor.moveToNext();
        Log.d("listview", cursor.getString(1).toString());
        CursorAdapter cursorAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_activated_2,
                cursor,
                new String[]{"title", "speed"},
                new int[]{android.R.id.text1, android.R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        cursorAdapter.notifyDataSetChanged();
        mLvDatabase.setAdapter(cursorAdapter);
        //insertDB("상도동", "123.4444", "987.7777", "1762");

    }

    public void insertDB(String title, String latitude, String longitude, String speed){
        db = helper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("title", title);
        value.put("latitude", latitude);
        value.put("longitude", longitude);
        value.put("speed", speed);

        db.insert("log", null, value);
    }


}
