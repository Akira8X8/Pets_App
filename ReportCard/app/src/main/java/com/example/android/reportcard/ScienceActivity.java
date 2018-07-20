package com.example.android.reportcard;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
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
public class ScienceActivity extends Activity {

    private MediaPlayer audioGen;

    private AudioManager maudioManage;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){
        @Override
        public void onCompletion(MediaPlayer audioGen){
            Toast.makeText(ScienceActivity.this, "Done playing!", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //creat & setup {@link AudioManager} to request audio focus
        maudioManage = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Create a list of reportCard
        final ArrayList<ReportCard> reportCard = new ArrayList<ReportCard>();
        reportCard.add(new ReportCard("C-", "Ecology", R.raw.color_black));
        reportCard.add(new ReportCard("A", "Astronomy", R.raw.color_white));
        reportCard.add(new ReportCard("B-", "Physics", R.raw.color_green));
        reportCard.add(new ReportCard("A+", "Biology", R.raw.color_mustard_yellow));
        reportCard.add(new ReportCard("A", "Chemistry", R.raw.color_dusty_yellow));
        reportCard.add(new ReportCard("A", "Marine Biology", R.raw.color_red));
        reportCard.add(new ReportCard("A+", "Life Sciences", R.raw.color_brown));
        reportCard.add(new ReportCard("A+", "Nutrition", R.raw.color_white));


        // Create an {@link WordAdapter}, whose data source is a list of {@link ReportCard}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, reportCard, R.color.science);

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

                //request audio focus for playback
//                int result = maudioManage.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
//                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//                    maudioManage.registerMediaButtonEventReceiver(RemoteControlReceiver);

                    audioGen = MediaPlayer.create(ScienceActivity.this, reportCard1.getmAudioResId());
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
