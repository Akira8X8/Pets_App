package com.example.zed.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.zed.habittracker.data.HabitContract;
import com.example.zed.habittracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_TYPE,
                HabitContract.HabitEntry.COLUMN_FREQUENCY,
                HabitContract.HabitEntry.COLUMN_WHEN,
                HabitContract.HabitEntry.COLUMN_DURATION};

        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                projection,
                null, null, null, null, null);

        TextView displayView = (TextView) findViewById(R.id.habit_text);

        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // pets table in the database).

            displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitContract.HabitEntry._ID + " - " + HabitContract.HabitEntry.COLUMN_TYPE + " - " + HabitContract.HabitEntry.COLUMN_FREQUENCY + " - " + HabitContract.HabitEntry.COLUMN_WHEN + " - " + HabitContract.HabitEntry.COLUMN_DURATION + "\n");

            //Find index of each column
            int idColIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int typeColIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_TYPE);
            int freqColIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_FREQUENCY);
            int whenColIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_WHEN);
            int durColIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_DURATION);

            while(cursor.moveToNext()){

                int currentID = cursor.getInt(idColIndex);
                String currentType = cursor.getString(typeColIndex);
                String currentFreq = cursor.getString(freqColIndex);
                String currentWhen = cursor.getString(whenColIndex);
                String currentDur = cursor.getString(durColIndex);

                displayView.append(("\n" + currentID + " - " + currentType + " - " + currentFreq + " - " + currentWhen + " - " + currentDur));
            }

        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void insertHabit(){
        SQLiteDatabase db =mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_TYPE, "Sprint");
        values.put(HabitContract.HabitEntry.COLUMN_FREQUENCY, HabitContract.HabitEntry.FREQUENCY_NORMAL);
        values.put(HabitContract.HabitEntry.COLUMN_WHEN, HabitContract.HabitEntry.WHEN_AFTERNOON);
        values.put(HabitContract.HabitEntry.COLUMN_DURATION, 45);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        Log.v("CatalogActivity", "New row ID " + newRowId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all_entries) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
