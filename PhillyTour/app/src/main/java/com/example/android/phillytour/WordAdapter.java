package com.example.android.phillytour;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Operator on 8/1/2016.
 */
public class WordAdapter extends ArrayAdapter<TourGuide>{

    //background color id resource
    private int mcolorID;

    /**
     * Create a new {@link WordAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param tourGuide is the list of {@link TourGuide}s to be displayed.
     */
    public WordAdapter(Context context, ArrayList<TourGuide> tourGuide, int colorID) {
        super(context, 0, tourGuide);
        mcolorID = colorID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.exhibit_layout, parent, false);
        }

        // Get the {@link TourGuide} object located at this position in the list
        final TourGuide currentTour = getItem(position);

        // Find the TextView in the exhibit.xml layout
        TextView ExhibitView = (TextView) listItemView.findViewById(R.id.exhibit_view);
        ExhibitView.setText(currentTour.getExhibit());

// Get the default caption from the currentTour object and set this text on
        // the default TextView.
        TextView CaptionView = (TextView) listItemView.findViewById(R.id.caption_view);
        CaptionView.setText(currentTour.getCaption());


        ImageView IconView = (ImageView) listItemView.findViewById(R.id.pic);

        if(currentTour.hasImg()){
            //set imageview to image src specified
            IconView.setImageResource(currentTour.getImageID());
            IconView.setVisibility(View.VISIBLE);
        }
        else {
            //else, hide imageview
            IconView.setVisibility(View.GONE);
        }


//        //set theme color for list item
        View textBox = listItemView.findViewById(R.id.textBox);
//        //find matching color
        int color = ContextCompat.getColor(getContext(), mcolorID);
//        //set background color of textbox
        textBox.setBackgroundColor(color);

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
