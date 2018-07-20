package com.example.myapplication.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {

//    private static final String

    public BookAdapter(Context context, ArrayList<Book> bookArrayList) {
        super(context, 0, bookArrayList);
    }

    /**
     * Returns a list item view that displays information about the book
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Book book = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }

        // Set the text for the Title and Author of the book
        TextView titleTextView = (TextView) convertView.findViewById(R.id.bookTitle);
        titleTextView.setText(book.getBookTitle());
        TextView authorTextView = (TextView) convertView.findViewById(R.id.bookAuthor);
        authorTextView.setText(book.getBookAuthor());

        return convertView;
    }
}
