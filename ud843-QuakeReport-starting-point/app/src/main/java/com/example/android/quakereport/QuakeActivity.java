package com.example.android.quakereport;

/**
 * Created by Operator on 8/2/2016.
 */
public class QuakeActivity {

    public final double magnitude;
    public final String location;
    public final long milliTime;
    public final String website;


    public QuakeActivity(double mag, String loc, long qTime, String site){
        magnitude = mag;
        location = loc;
        milliTime = qTime;
        website = site;
    }

    //Get info
    public double getMagnitude(){
        return magnitude;
    }

    public String getLocation(){
        return location;
    }

    public long getMilliTime(){
        return milliTime;
    }

    public String getSite() {return website;}
}
