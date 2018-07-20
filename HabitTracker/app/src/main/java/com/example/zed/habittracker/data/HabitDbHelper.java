package com.example.zed.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Zed on 2/12/2017.
 */

public class HabitDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Habits.db";
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE habits(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "type TEXT NOT NULL," +
            "frequency INTEGER NOT NULL," +
            "timing INTEGER NOT NULL," +
            "duration INTEGER NOT NULL DEFAULT 0)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + HabitContract.HabitEntry.TABLE_NAME;

    public HabitDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldV, int newV){
        onUpgrade(db, oldV, newV);
    }
}
