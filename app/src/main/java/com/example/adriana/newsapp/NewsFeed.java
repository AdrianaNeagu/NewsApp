package com.example.adriana.newsapp;

public class NewsFeed {

    private String mTitle;
    private String mSection;
    private String mTimeInMilliseconds;
    private String mUrl;

    public NewsFeed(String mTitle, String mSection, String mTimeInMilliseconds, String mUrl) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mTimeInMilliseconds = mTimeInMilliseconds;
        this.mUrl = mUrl;
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
}
