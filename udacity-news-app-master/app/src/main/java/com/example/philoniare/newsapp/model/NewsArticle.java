package com.example.philoniare.newsapp.model;

public class NewsArticle {
    private String mTitle;
    private String mSectionName;
    private String mThumbnail;
    private String mUrl;

    public NewsArticle(String mTitle, String mSectionName, String mThumbnail, String mUrl) {
        this.mTitle = mTitle;
        this.mSectionName = mSectionName;
        this.mThumbnail = mThumbnail;
        this.mUrl = mUrl;

    }

    public String getTitle() {return mTitle;}

    public String getSectionName() {
        return mSectionName;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public String getUrl() {
        return mUrl;
    }

}
