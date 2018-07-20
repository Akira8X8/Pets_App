package com.example.myapplication.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    /**
     * Query Url
     */
    private String mUrl;

        /**
         * Constructs a new {@link BookLoader}.
         *
         * @param context
         * @param url
         */
        public BookLoader(Context context, String url) {
            super(context);
            mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        } else {
            // Perform the network request, parse the response and extract a list of books
            List<Book> books = QueryUtils.fetchBookData(mUrl);
            return books;
        }
    }
}
