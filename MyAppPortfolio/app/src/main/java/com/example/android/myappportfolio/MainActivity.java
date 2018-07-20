package com.example.android.myappportfolio;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private MediaPlayer mediaPlayer;
    private Button play, pause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the View that shows movies
        TextView movies = (TextView) findViewById(R.id.movies);

        // Set click listener
        movies.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the movies category is clicked on.
            @Override
            public void onClick(View view) {
//                // Create a new intent to open the MovieActivity
//                Intent MovieIntent = new Intent(MainActivity.this, MovieActivity.class);
//
//                // Start the new activity
//                startActivity(MovieIntent);
                Toast.makeText(MainActivity.this, "This button shows you popular movies!", Toast.LENGTH_SHORT).show();
            }
        });

        // Find the View that shows stocks
        TextView stock = (TextView) findViewById(R.id.stock);

        stock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Intent MovieIntent = new Intent(MainActivity.this, MovieActivity.class);
//
//                // Start the new activity
//                startActivity(StockIntent);
                Toast.makeText(MainActivity.this, "This button shows you hot stocks!", Toast.LENGTH_SHORT).show();
            }
        });

        // Find the View that shows build
        TextView bigger = (TextView) findViewById(R.id.bigger);

        // Set click listener
        bigger.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the movies category is clicked on.
            @Override
            public void onClick(View view) {
//                Intent BuildIntent = new Intent(MainActivity.this, BuildActivity.class);
//
//                // Start the new activity
//                startActivity(BuildIntent);
                Toast.makeText(MainActivity.this, "This button builds!", Toast.LENGTH_SHORT).show();
            }
        });

        TextView material = (TextView) findViewById(R.id.material);

        // Set click listener
        material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent MaterialIntent = new Intent(MainActivity.this, MaterialActivity.class);
//
//                // Start the new activity
//                startActivity(MaterialIntent);
                Toast.makeText(MainActivity.this, "This button makes your app material!", Toast.LENGTH_SHORT).show();
            }
        });

        TextView ubi = (TextView) findViewById(R.id.ubi);

        // Set click listener
        ubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent UbiIntent = new Intent(MainActivity.this, UbiActivity.class);
//
//                startActivity(UbiIntent);
                Toast.makeText(MainActivity.this, "Go ubiquitous!", Toast.LENGTH_SHORT).show();
            }
        });

        TextView cap = (TextView) findViewById(R.id.cap);

        // Set click listener
        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent CapIntent = new Intent(MainActivity.this, CapActivity.class);
//
//                // Start the new activity
//                startActivity(CapIntent);
                Toast.makeText(MainActivity.this, "This button shows your capstone!", Toast.LENGTH_SHORT).show();
            }
        });


        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);

        mediaPlayer = MediaPlayer.create(this, R.raw.subsonik);

        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                Toast.makeText(getApplicationContext(), "Playing", Toast.LENGTH_SHORT).show();

            }
        });

        pause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
            }
        });
    }
}


