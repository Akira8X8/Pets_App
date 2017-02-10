package com.example.zed.books;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Zed on 1/17/2017.
 */

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String mUrl;

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
            List<Book> books = QueryUtils.fetchBooks(mUrl);
            return books;
        }
    }
}
