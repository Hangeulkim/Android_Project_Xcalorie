package com.example.trust;

/*
public class DBHandler {
    private final String TAG = "DBHandler";

    SQLiteOpenHelper mHelper = null;
    SQLiteDatabase mDB = null;

    public DBHandler(Context context, String name){
        mHelper = new DBHelper(context);
    }

    public static DBHandler open(Context context, String name) {
        return new DBHandler(context, name);
    }

    public Cursor select(){
        mDB = mHelper.getReadableDatabase();
        Cursor c = mDB.query("log", null, null, null, null, null, null);
        return c;
    }

    public void insert(String name, String latitude, String longitude, String speed){
        Log.d(TAG, "insert");

        mDB = mHelper.getWritableDatabase();

        ContentValues value = new ContentValues();
        value.put("name", name);
        value.put("latitude", latitude);
        value.put("longitude", longitude);
        value.put("speed", speed);

        mDB.insert("log", null, value);
    }

    public void delete(String name){
        Log.d(TAG, "delete");
        mDB = mHelper.getWritableDatabase();
        mDB.delete("log", "name=?", new String[]{name});
    }

    public void close(){
        mHelper.close();
    }
}


 */