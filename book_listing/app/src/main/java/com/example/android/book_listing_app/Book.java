package com.example.android.book_listing_app;

/**
 * Book
 * A custom class to store Book information
 */
class Book {

    final String title;
    final String authors;
    final String picLink;


    public Book(
            String title,
            String authors,
            String picLink
    ) {

        this.title = title;
        this.authors = authors;
        this.picLink = picLink;

    }

}
