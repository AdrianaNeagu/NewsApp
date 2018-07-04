package com.example.adriana.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsFeedAdapter extends ArrayAdapter<NewsFeed> {

    private static final String DATE_SEPARATOR = "T";

    public NewsFeedAdapter(Context context, List<NewsFeed> newsFeeds) {
        super(context, 0, newsFeeds);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_feed_list_item, parent, false);
        }
        NewsFeed currentNewsFeed = getItem(position);

        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(currentNewsFeed.getTitle());

        TextView sectionView = listItemView.findViewById(R.id.section_name);
        sectionView.setText(currentNewsFeed.getSection());


        String originalDate = currentNewsFeed.getTimeInMilliseconds();
        String date = "";
        String time = "";

        if (originalDate.contains(DATE_SEPARATOR)) {
            String[] parts = originalDate.split(DATE_SEPARATOR);
            date = parts[0];
            time = parts[1];
        }

        TextView dateLocationView = listItemView.findViewById(R.id.date);
        dateLocationView.setText(currentNewsFeed.getTimeInMilliseconds());
        dateLocationView.setText(date);

        TextView timeOffsetView = listItemView.findViewById(R.id.time);
        String timeRemovedLastLetter = removeLastChar(time);
        timeOffsetView.setText(timeRemovedLastLetter);

        return listItemView;
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 1);
           }

}
