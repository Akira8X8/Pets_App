package com.example.zed.books;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>>{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * URL combination for the book search
     */
    private static final String BOOKS1 = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String BOOKS2 = "&maxResults=25";

    private static final int BOOK_LOADER_ID = 1;

    private ListAdapter mAdapter;

    private String wordSearch = null;
    private String searchWord = null;
    private TextView emptyView;
    private EditText bookSearch;
    private Editable searchEditable;

    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView bookListView = (ListView) findViewById(R.id.list);
        mAdapter = new ListAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(BOOK_LOADER_ID, null, this);

        emptyView = (TextView) findViewById(R.id.empty_view);
        bookSearch = (EditText) findViewById(R.id.book_search);

        searchButton = (Button) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoaderManager().restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                wordSearch = null;
                bookSearch = (EditText) findViewById(R.id.book_search);
                searchEditable = bookSearch.getText();
                searchWord = searchEditable.toString();
                wordSearch = BOOKS1 + searchWord + BOOKS2;

                // Get a reference to the ConnectivityManager to check the state of the network connectivity
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    emptyView.setVisibility(GONE);
                    //Get a reference to the LoaderManager, in order to interact with leaders.
                    LoaderManager loaderManager = getLoaderManager();
                    Log.i(LOG_TAG, "connection OK!");

                    // Initialize the loader.
                    loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
                } else {
                    // Update empty state with no connection error message
                    emptyView.setText(R.string.no_internet_connection);
                    emptyView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, wordSearch);

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Clear the adapter data
        mAdapter.clear();

        // Update the ListView of {@link Book} objects
        Log.i(LOG_TAG, "onLoadFinished");
        if (books != null && !books.isEmpty()) {
            mAdapter.clear();
            mAdapter.addAll(books);
        } else {
            emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
    }
}
