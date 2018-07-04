package com.example.adriana.newsapp;

public class NewsFeed {
    private String mTitle;
    private String mSection;
    private String mTimeInMilliseconds;
    private String mUrl;

    public NewsFeed(String title, String section, String timeInMilliseconds, String url) {
        mTitle = title;
        mSection = section;
        mTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;
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

