package com.example.android.dummylisttester;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Operator on 10/17/2016.
 */

public class FlavorAdapter extends ArrayAdapter<AndroidFlavor> {

    public FlavorAdapter(Context context, ArrayList<AndroidFlavor> flavor){
        super(context, 0, flavor);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View ListItemView = convertView;
        if (ListItemView == null){
            ListItemView  = LayoutInflater.from(getContext()).inflate(R.layout.entry, parent, false);
        }

        AndroidFlavor flavorAdapter = getItem(position);
        TextView namewView = (TextView) ListItemView.findViewById(R.id.name);
        namewView.setText(flavorAdapter.getName());
        TextView numberView = (TextView) ListItemView.findViewById(R.id.number);
        numberView.setText(flavorAdapter.getNumber());
        ImageView picView = (ImageView) ListItemView.findViewById(R.id.pic);
        picView.setImageResource(flavorAdapter.getImageID());


        return ListItemView;
    }
}
