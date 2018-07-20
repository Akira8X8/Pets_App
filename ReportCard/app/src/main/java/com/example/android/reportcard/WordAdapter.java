package com.example.android.reportcard;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Operator on 7/5/2016.
 */
public class WordAdapter extends ArrayAdapter<ReportCard>{

    //background color id resource
    private int mcolorID;
    /**
     * Create a new {@link WordAdapter} object.
     *
     * @param context is the current context (i.e. Activity) that the adapter is being created in.
     * @param reportCard is the list of {@link ReportCard}s to be displayed.
     */
    public WordAdapter(Context context, ArrayList<ReportCard> reportCard, int colorID) {
        super(context, 0, reportCard);
        mcolorID = colorID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.grading, parent, false);
        }

            // Get the {@link ReportCard} object located at this position in the list
            ReportCard currentReportCard = getItem(position);

        // Find the TextView in the grading.xml layout
        TextView SubjectView = (TextView) listItemView.findViewById(R.id.subject_view);
        SubjectView.setText(currentReportCard.getSubject());


        TextView GradeView = (TextView) listItemView.findViewById(R.id.grade_view);
        // Get the default translation from the currentReportCard object and set this text on
        // the default TextView.
        GradeView.setText(currentReportCard.getGrade());

        ImageView IconView = (ImageView) listItemView.findViewById(R.id.icon);
        if(currentReportCard.hasImg()){
            //set imageview to image src specified
            IconView.setImageResource(currentReportCard.getImageID());
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
