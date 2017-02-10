package com.example.zed.books;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zed on 1/17/2017.
 */

public class ListAdapter extends ArrayAdapter<Book>{

    public ListAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result, parent, false);
        }

        // Set the text for the Title and Author of the book
        TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
        titleTextView.setText(book.getTitle());
        TextView authorTextView = (TextView) convertView.findViewById(R.id.author);
        authorTextView.setText(book.getAuthor());

        return convertView;
    }
}
