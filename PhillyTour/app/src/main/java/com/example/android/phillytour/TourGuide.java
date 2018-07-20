package com.example.android.phillytour;

/**
 * Created by Operator on 8/1/2016.
 */
public class TourGuide{

    private int Texhibit;

    private int Tcaption;

    private int TimageID = NO_IMAGE_PROVIDED;

    private String Turl;

    private static final int NO_IMAGE_PROVIDED = -1;

    private int TAudioResId;

    //Create new Tour guide object

    public TourGuide(int exhibit, int caption, int imageID){
        Texhibit = exhibit;
        Tcaption = caption;
        TimageID = imageID;
    }

    public TourGuide(int exhibit, int caption, int imageID, String site){
        Texhibit = exhibit;
        Tcaption = caption;
        TimageID = imageID;
        Turl = site;
    }

    //Get exhibit name
    public int getExhibit(){
        return Texhibit;
    }

    public int getCaption(){
        return Tcaption;
    }

    //check for img
    public boolean hasImg(){
        return TimageID != NO_IMAGE_PROVIDED;
    }

    public int getImageID(){
        return TimageID;
    }

    //get site url
    public String getUrl() {return Turl;}
}
