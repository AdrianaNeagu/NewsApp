package com.example.adriana.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<NewsFeed>> {
    private static final String LOG_TAG = NewsFeedActivity.class.getName();
    private static final String GUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?q=debates&section=politics&show-tags=contributor&api-key=43570cba-f440-4ed4-8a17-8181bffaf7b1";
    private NewsFeedAdapter mAdapter;
    private static final int NEWSFEED_LOADER_ID = 1;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_feed_activity);

        ListView newsFeedListView = findViewById(R.id.list);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsFeedListView.setEmptyView(mEmptyStateTextView);

        mAdapter = new NewsFeedAdapter(this, new ArrayList<NewsFeed>());
        newsFeedListView.setAdapter(mAdapter);

        newsFeedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NewsFeed currentNewsFeed = mAdapter.getItem(position);
                Uri newsFeedUri = Uri.parse(currentNewsFeed.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsFeedUri);
                startActivity(websiteIntent);
            }

        });
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWSFEED_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @NonNull
    @Override
    public Loader<List<NewsFeed>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsFeedLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsFeed>> loader, List<NewsFeed> newsFeeds) {
        View loadingIndicator = findViewById(R.id.loading_spinner);
        loadingIndicator.setVisibility(View.GONE);

        if (newsFeeds != null && !newsFeeds.isEmpty()) {
            mAdapter.addAll(newsFeeds);
        } else {
            mEmptyStateTextView.setText(R.string.no_news_available);
            mAdapter.clear();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsFeed>> loader) {
        mAdapter.clear();

    }
}
