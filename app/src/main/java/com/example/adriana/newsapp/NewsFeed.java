package com.example.adriana.newsapp;

public class NewsFeed {
    private String mTitle;
    private String mSection;
    private String mTimeInMilliseconds;
    private String mUrl;
    private String mAuthor;

    public NewsFeed(String title, String section, String timeInMilliseconds, String url, String author) {
        mTitle = title;
        mSection = section;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getAuthor() {
        return mAuthor;
    }
}

