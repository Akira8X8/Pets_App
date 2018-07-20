package com.example.zed.inventory.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.zed.inventory.data.ItemContract.ItemEntry;

/**
 * Created by Zed on 2/27/2017.
 */

public class ItemDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ItemDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "stock.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 3;

    /**
     * Constructs a new instance of {@link ItemDbHelper}.
     *
     * @param context of the app
     */
    public ItemDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ITEMS_TABLE =  "CREATE TABLE " + ItemEntry.TABLE_NAME + " ("
                + ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_ITEM_PRICE + " INTEGER NOT NULL DEFAULT 0, "
                + ItemEntry.COLUMN_ITEM_TYPE + " INTEGER NOT NULL, "
                + ItemEntry.COLUMN_ITEM_WEIGHT + " INTEGER NOT NULL DEFAULT 1, "
                + ItemEntry.COLUMN_ITEM_QUANTITY + " INTEGER NOT NULL DEFAULT 1,"
                + ItemEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL, "
                + ItemEntry.COLUMN_SUPPLIER_PHONE + " INTEGER, "
                + ItemEntry.COLUMN_SUPPLIER_EMAIL + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ITEMS_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemEntry.TABLE_NAME);
        onCreate(db);


    }

    public Cursor findItems() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                ItemEntry._ID,
                ItemEntry.COLUMN_ITEM_NAME,
                ItemEntry.COLUMN_ITEM_PRICE,
                ItemEntry.COLUMN_ITEM_QUANTITY,
                ItemEntry.COLUMN_SUPPLIER_NAME,
                ItemEntry.COLUMN_SUPPLIER_PHONE,
                ItemEntry.COLUMN_SUPPLIER_EMAIL
        };
        Cursor cursor = db.query(
                ItemEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        return cursor;
    }

    public void sellItem(long itemId, int quantity) {
        SQLiteDatabase db = getWritableDatabase();
        int newQuantity = 0;
        if (quantity > 0) {
            newQuantity = quantity -1;
        }
        ContentValues values = new ContentValues();
        values.put(ItemEntry.COLUMN_ITEM_QUANTITY, newQuantity);
        String selection = ItemEntry._ID + "=?";
        String[] selectionArgs = new String[] { String.valueOf(itemId) };
        db.update(ItemEntry.TABLE_NAME,
                values, selection, selectionArgs);
    }
}
