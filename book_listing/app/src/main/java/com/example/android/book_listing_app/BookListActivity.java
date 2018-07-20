package com.example.android.book_listing_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;


public class BookListActivity extends AppCompatActivity implements BookListFragment.OnListFragmentInteractionListener {
    //private static final String LOG_TAG = BookListActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
    }

}
