package com.example.zed.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zed.habittracker.data.HabitContract;
import com.example.zed.habittracker.data.HabitDbHelper;


/**
 * Created by Zed on 2/14/2017.
 */

public class EditorActivity extends AppCompatActivity {

    private EditText mTypeEditText;
    private Spinner mFreqSpinner;
    private Spinner mWhenSpinner;
    private EditText mDurationEditText;

    private int mFreq = 0;
    private int mWhen = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editor_main);

        mTypeEditText = (EditText) findViewById(R.id.edit_type);
        mFreqSpinner = (Spinner) findViewById(R.id.spinner_frequency);
        mWhenSpinner = (Spinner) findViewById(R.id.spinner_when);
        mDurationEditText = (EditText) findViewById(R.id.edit_time);

        setFreqSpinner();
        setWhenSpinner();
    }

    private void setFreqSpinner(){

        ArrayAdapter freqSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_freq_options, android.R.layout.simple_spinner_item);

        freqSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mFreqSpinner.setAdapter(freqSpinnerAdapter);

        mFreqSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)){
                    if (selection.equals(getString(R.string.freq_normal))){
                        mFreq = HabitContract.HabitEntry.FREQUENCY_NORMAL;
                    } else if (selection.equals(getString(R.string.freq_often))) {
                        mFreq = HabitContract.HabitEntry.FREQUENCY_OFTEN;
                    } else {
                        mFreq = HabitContract.HabitEntry.FREQUENCY_RARE;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { mFreq = 0; //normal
            }
        });
    }

    private void setWhenSpinner(){

        ArrayAdapter whenSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_when_options, android.R.layout.simple_spinner_item);

        whenSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mWhenSpinner.setAdapter(whenSpinnerAdapter);

        mWhenSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)){
                    if (selection.equals(getString(R.string.when_morning))){
                        mWhen = HabitContract.HabitEntry.WHEN_MORNING;
                    } else if (selection.equals(getString(R.string.when_afternoon))) {
                        mWhen = HabitContract.HabitEntry.WHEN_AFTERNOON;
                    } else {
                        mWhen = HabitContract.HabitEntry.WHEN_NIGHT;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { mFreq = 0; //normal
            }
        });
    }

    private void insertHabit(){
        String typeString = mTypeEditText.getText().toString().trim();
        String durationString = mDurationEditText.getText().toString().trim();

        int duration = Integer.parseInt(durationString);

        HabitDbHelper mDbHelper = new HabitDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_TYPE, typeString);
        values.put(HabitContract.HabitEntry.COLUMN_FREQUENCY, mFreq);
        values.put(HabitContract.HabitEntry.COLUMN_WHEN, mWhen);
        values.put(HabitContract.HabitEntry.COLUMN_DURATION, duration);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);

        if(newRowId == -1){
            Toast.makeText(this, "Error saving habit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertHabit();
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
