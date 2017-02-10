package com.example.zed.books;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zed on 1/17/2017.
 */

public  final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }

    public static List<Book> fetchBooks(String requestURL) {
        Log.d(QueryUtils.class.getSimpleName(), requestURL);

        URL url = createUrl(requestURL);

        String JSONResponse = null;
        try {
            JSONResponse = makeHttpRequest(url);
        }
        catch (IOException e){
            Log.e(LOG_TAG, "Problem making HTTP request.", e);
        }

        List<Book> books = extractBooks(JSONResponse);
        return books;
    }

    private static URL createUrl(String urlString){
        URL url = null;
        try {
            url = new URL(urlString);
        }
        catch (MalformedURLException e){
            Log.e(LOG_TAG, "Problem building URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving Book API JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();

            }
        }
        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */


    private static List<Book> extractBooks(String bookJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        // Create an empty ArrayList for adding books
        List<Book> books = new ArrayList<>();

        // Catch the exception to avoid crash; error message to logs.
        try {
            JSONObject baseJsonResponse = new JSONObject(bookJSON);

            // Extract the JSONArray from "items",
            JSONArray bookArray = baseJsonResponse.getJSONArray("items");

            // For each book in the bookArray, create an {@link Book} object
            for (int i = 0; i < bookArray.length(); i++) {

                // Get a single book at position i within the list of books
                JSONObject currentBook = bookArray.getJSONObject(i);

                JSONObject properties = currentBook.getJSONObject("volumeInfo");
                // Extract the value for the key called "title"
                String title = properties.getString("title");

                JSONArray authors = null;

                try{
                    authors = properties.getJSONArray("authors");
                }

                catch (JSONException ignored){
                }

                String authorString = "";

                if (properties.has("authors")) {

                    JSONArray authorsArray = properties.optJSONArray("authors");

                    if (authorsArray == null) {
                        authorString = "Unknown";
                    }
                    else{
                        int numAuthors = authorsArray.length();
                        for (int a = 0; a < authorsArray.length(); a++) {
                            String author = authorsArray.getString(a);
                            if (authorString.isEmpty()){
                                authorString = author;
                            }
                            else if (a == numAuthors - 1) {
                                authorString += " and " + author;
                            }

                            else {
                                authorString += ", " + author;
                            }
                        }
                    }
                }

                // Create a new {@link Book} object with the title
                // and author from the JSON response.
                Book book = new Book(title, authorString);

                books.add(book);
            }

        }

        catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing JSON results", e);
        }

        // Return the list of books
        return books;
    }

}
