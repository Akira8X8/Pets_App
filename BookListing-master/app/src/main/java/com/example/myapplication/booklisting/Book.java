package com.example.myapplication.booklisting;

/**
 * Created by Eric Emery on 10/8/2016.
 */

public class Book {

    /**
     * Title of the book
     */
    private String mBookTitle;

    /**
     * Author of the book
     */
    private String mBookAuthor;

    /**
     * Website URL of the book
     */
    private String mURL;

    /**
     * Create a Book Object
     *
     * @param bookTitle
     * @param bookAuthor
     * @param url
     */
    public Book(String bookTitle, String bookAuthor, String url) {
        mBookTitle = bookTitle;
        mBookAuthor = bookAuthor;
        mURL = url;
    }

    /* Get the Book title */
    public String getBookTitle() {
        return mBookTitle;
    }

    /* Get the Book Author */
    public String getBookAuthor() {
        return mBookAuthor;
    }

    /* Get the URL of the book */
    public String getURL() {
        return mURL;
    }
}