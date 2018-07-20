package com.example.android.reportcard;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Operator on 7/5/2016.
 */
public class ReportCard extends AppCompatActivity{
    private String mGrade;

    private String mSubject;

    private int mImageID = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int mAudioResId;


//    private MediaPlayer mediaPlayer;
//
//    private Button play, pause;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.grading);
//
////        play = findViewById(R.id.play);
////        pause = findViewById(R.id.pause);
//
//        mediaPlayer = MediaPlayer.create(this, R.raw.subsonik);
//
//        play.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                mediaPlayer.start();
//                Toast.makeText(getApplicationContext(), "Playing", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        pause.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Paused", Toast.LENGTH_SHORT).show();
//                mediaPlayer.pause();
//            }
//        });
//    }

    /**
     * Create a new ReportCard object.
     *
     */
    public ReportCard(String grade, String subject, int audioResId) {
        mGrade = grade;
        mSubject = subject;
        mAudioResId = audioResId;
    }

//new imageID is image associated with subject/grade
    public ReportCard(String grade, String subject, int imageID, int audioResId) {
        mGrade = grade;
        mSubject = subject;
        mImageID = imageID;
        mAudioResId = audioResId;
    }

    /**
     * Get grade
     */
    public String getGrade() {
        return mGrade;
    }

    /**
     * Get subject
     */
    public String getSubject() {
        return mSubject;
    }

    //check for img
    public boolean hasImg(){
        return mImageID != NO_IMAGE_PROVIDED;
    }

    //Get image
    public int getImageID(){return mImageID; }

    //Set subject
    public void setmSubject(String finSub){
        mSubject = finSub;
    }

    //Set grade
    public void setmGrade(String finGrade){
        mGrade = finGrade;
    }

    //return audio res ID
    public int getmAudioResId(){return mAudioResId;}


    @Override
    public String toString() {
        return "ReportCard{" +
                "mGrade='" + mGrade + '\'' +
                ", mSubject='" + mSubject + '\'' +
                ", mImageID=" + mImageID +
                '}';
    }
}
