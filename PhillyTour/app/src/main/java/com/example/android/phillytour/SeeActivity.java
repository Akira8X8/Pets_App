package com.example.android.phillytour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Operator on 8/1/2016.
 */
public class SeeActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list);

        //create attractions list
        final ArrayList<TourGuide> tourGuide = new ArrayList<TourGuide>();
        tourGuide.add(new TourGuide(R.string.dog, R.string.hhr, R.drawable.snoop));
        tourGuide.add(new TourGuide(R.string.pdc, R.string.fus, R.drawable.philadanco));
        tourGuide.add(new TourGuide(R.string.gcj, R.string.br, R.drawable.gcj));
        tourGuide.add(new TourGuide(R.string.clk, R.string.the, R.drawable.shakespeare_clark));
        tourGuide.add(new TourGuide(R.string.div, R.string.mus, R.drawable.divine_sister));

        WordAdapter adapter = new WordAdapter(this, tourGuide, R.color.see);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link ReportCard} in the list.
        listView.setAdapter(adapter);
    }
}
