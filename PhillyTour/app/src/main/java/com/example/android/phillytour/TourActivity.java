package com.example.android.phillytour;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class TourActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);


        TextView eat = (TextView) findViewById(R.id.eat);
        eat.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the eat category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open
                Intent EatIntent = new Intent(TourActivity.this, EatActivity.class);
                // Start the new activity
                startActivity(EatIntent);
            }
        });

        TextView play = (TextView) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent PlayIntent = new Intent(TourActivity.this, PlayActivity.class);
                startActivity(PlayIntent);
            }
        });


        TextView colors = (TextView) findViewById(R.id.see);
        colors.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent ArtIntent = new Intent(TourActivity.this, SeeActivity.class);
                startActivity(ArtIntent);
            }
        });


        TextView phrases = (TextView) findViewById(R.id.stay);
        phrases.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent StayIntent = new Intent(TourActivity.this, StayActivity.class);
                startActivity(StayIntent);
            }
        });
    }
}
