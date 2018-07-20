package com.example.android.reportcard;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        TextView numbers = (TextView) findViewById(R.id.Math);
        numbers.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open
                Intent MathIntent = new Intent(ReportActivity.this, MathActivity.class);
                // Start the new activity
                startActivity(MathIntent);
            }
        });


        TextView family = (TextView) findViewById(R.id.English);
        family.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent EnglishIntent = new Intent(ReportActivity.this, EnglishActivity.class);
                startActivity(EnglishIntent);
            }
        });


        TextView colors = (TextView) findViewById(R.id.Art);
        colors.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent ArtIntent = new Intent(ReportActivity.this, ArtActivity.class);
                startActivity(ArtIntent);
            }
        });


        TextView phrases = (TextView) findViewById(R.id.Science);
        phrases.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent ScienceIntent = new Intent(ReportActivity.this, ScienceActivity.class);
                startActivity(ScienceIntent);
            }
        });
    }
}
