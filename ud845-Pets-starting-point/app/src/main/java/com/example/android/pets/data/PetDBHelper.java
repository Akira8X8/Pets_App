package com.example.android.pets.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Zed on 1/31/2017.
 */

public class PetDBHelper extends SQLiteOpenHelper {
    //Database schema change requires complementing version change
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Pets.db";
    public static final String SQL_CREATE_ENTRIES = "CREATE TABLE pets(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "breed TEXT," +
            "gender INTEGER NOT NULL," +
            "weight INTEGER NOT NULL DEFAULT 0)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+ PetContract.PetEntry.TABLE_NAME;

    public PetDBHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
//cursor adapter -- 1. constructor 2.
    //bindview view & data

}
