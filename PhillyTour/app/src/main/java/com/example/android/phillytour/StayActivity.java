package com.example.android.phillytour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Operator on 8/1/2016.
 */
public class StayActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list);

        //create attractions list
        final ArrayList<TourGuide> tourGuide = new ArrayList<TourGuide>();
        tourGuide.add(new TourGuide(R.string.hy, R.string.lux, R.drawable.hyatt_bellevue));
        tourGuide.add(new TourGuide(R.string.mer, R.string.mod, R.drawable.meridien));
        tourGuide.add(new TourGuide(R.string.chh, R.string.charm, R.drawable.chestnut_hill));
        tourGuide.add(new TourGuide(R.string.rit, R.string.ico, R.drawable.rittenhouse));
        tourGuide.add(new TourGuide(R.string.pen, R.string.ant, R.drawable.penns_view));

        WordAdapter adapter = new WordAdapter(this, tourGuide, R.color.stay);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link ReportCard} in the list.
        listView.setAdapter(adapter);
    }
}
