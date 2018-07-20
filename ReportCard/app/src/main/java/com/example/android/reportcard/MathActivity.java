package com.example.android.reportcard;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Operator on 7/5/2016.
 */
public class MathActivity extends Activity {

    private MediaPlayer audioGen;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer audioGen){
            Toast.makeText(MathActivity.this, "Done playing!", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create a list of reportCard
        final ArrayList<ReportCard> reportCard = new ArrayList<ReportCard>();
        reportCard.add(new ReportCard("B-", "Geometry",R.drawable.family_daughter, R.raw.family_grandfather));
        reportCard.add(new ReportCard("A+", "Arithmetic", R.raw.color_black));
        reportCard.add(new ReportCard("A-", "Statistics", R.raw.color_brown));
        reportCard.add(new ReportCard("A", "Calculus", R.raw.family_grandmother));
        reportCard.add(new ReportCard("D", "Probability", R.raw.color_gray));
        reportCard.add(new ReportCard("A", "Discrete Math", R.raw.color_mustard_yellow));
        reportCard.add(new ReportCard("B", "Applied Math", R.raw.color_red));
        reportCard.add(new ReportCard("A-", "Analytics", R.raw.family_daughter));


        // Create an {@link WordAdapter}, whose data source is a list of {@link ReportCard}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, reportCard, R.color.math);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);


        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link ReportCard} in the list.
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ReportCard reportCard1 = reportCard.get(position);
                releaseMediaPlayer();
                audioGen = MediaPlayer.create(MathActivity.this, reportCard1.getmAudioResId());
                audioGen.start();
                audioGen.setOnCompletionListener(mCompletionListener);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */

    private void releaseMediaPlayer(){
        // If the media player is not null, then it may be currently playing a sound.
        if (audioGen != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            audioGen.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            audioGen = null;
        }
    }
}
