package com.example.myapplication.booklisting;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    private static final String LOG_TAG = BookActivity.class.getName();

    /**
     * URL for the book data Test
     */
    private static final String GOOGLE_BOOKS_1 = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String GOOGLE_BOOKS_2 = "&maxResults=30";

    /**
     * Constant value for the Book loader ID.
     */
    private static final int BOOK_LOADER_ID = 1;

    /**
     * Adapter for the list of books
     */
    private BookAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;
    private ListView bookListView;
    private EditText searchFieldEditText;
    private Editable searchFieldEditable;
    private TextView submitButton;

    private String searchQuery = null;
    private String searchTerm = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        // Find a reference to the {@link ListVew} in the layout
        bookListView = (ListView) findViewById(R.id.list);
        // Create a new adapter that takes the list of books as input
        mAdapter = new BookAdapter(BookActivity.this, new ArrayList<Book>());
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        searchFieldEditText = (EditText) findViewById(R.id.searchFieldEditText);

        bookListView.setEmptyView(mEmptyStateTextView);

        // Set the adapter on the {@link ListView}
        // so that the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the book that was selected
                Book currentBook = mAdapter.getItem(position);

                // Convert the String URL into a URL object (to pass into the Intent constructor)
                Uri bookUrl = Uri.parse(currentBook.getURL());

                // Create a new intent to view the book URL
                Intent infoLinkIntent = new Intent(Intent.ACTION_VIEW, bookUrl);

                // Send the intent to launch a new activity
                startActivity(infoLinkIntent);
            }
        });

        getLoaderManager().initLoader(BOOK_LOADER_ID, null, BookActivity.this);

        submitButton = (TextView) findViewById(R.id.searchBtn);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getLoaderManager().restartLoader(BOOK_LOADER_ID, null, BookActivity.this);

                View loadingIndicator = findViewById(R.id.loading_spinner);
                loadingIndicator.setVisibility(View.VISIBLE);

                searchQuery = null;
                searchFieldEditText = (EditText) findViewById(R.id.searchFieldEditText);
                searchFieldEditable = searchFieldEditText.getText();
                searchTerm = searchFieldEditable.toString();
                searchQuery = GOOGLE_BOOKS_1 + searchTerm + GOOGLE_BOOKS_2;

                // Get a reference to the ConnectivityManager to check the state of the network connectivity
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    //Get a reference to the LoaderManager, in order to interact with leaders.
                    LoaderManager loaderManager = getLoaderManager();

                    // Initialize the loader.
                    loaderManager.initLoader(BOOK_LOADER_ID, null, BookActivity.this);
                } else {
                    // Update empty state with no connection error message
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, searchQuery);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);
        // Clear the adapter of previous book data
        mAdapter.clear();

        // Update the ListView of {@link Book} objects
        Log.i(LOG_TAG, "onLoadFinished");
        if (books != null && !books.isEmpty()) {
            mAdapter.clear();
            mAdapter.addAll(books);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
    }
}