package com.example.zed.books;

/**
 * Created by Zed on 1/17/2017.
 */

public class Book {

    public final String title;
    public final String author;

    public Book (String mtitle, String mauthor){
        title = mtitle;
        author = mauthor;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }
}
