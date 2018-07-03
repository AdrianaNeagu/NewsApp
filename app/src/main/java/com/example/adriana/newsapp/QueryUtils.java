package com.example.adriana.newsapp;

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

public class QueryUtils {
    private static final String LOG_TAG = NewsFeedActivity.class.getName();

    private QueryUtils() {
    }

    public static List<NewsFeed> fetchNewsFeedData(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<NewsFeed> newsFeeds = extractFeatureFromJson(jsonResponse);

        return newsFeeds;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
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
            Log.e(LOG_TAG, "Problem retrieving the newsFeed JSON results.", e);
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


    public static List<NewsFeed> extractFeatureFromJson(String newsFeedJSON) {
        if (TextUtils.isEmpty(newsFeedJSON)) {
            return null;
        }

        List<NewsFeed> newsFeeds = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(newsFeedJSON);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            Log.d("response", "Value: " + response);

            JSONArray newsFeedArray = response.getJSONArray("results");

            for (int i = 0; i < newsFeedArray.length(); i++) {
                JSONObject currentNewsFeed = newsFeedArray.getJSONObject(i);

                JSONArray tagsArray = currentNewsFeed.getJSONArray("tags");
                String author = "";
                if (tagsArray.length() != 0) {
                    JSONObject tagsObject = tagsArray.getJSONObject(0);
                    if (tagsObject.has("webTitle")) {
                        author = tagsObject.getString("webTitle");
                        Log.d("tagsObject", "Value: " + author);
                    }
                }

                String title = currentNewsFeed.getString("webTitle");
                Log.d("webTitle", "Value: " + title);

                String section = currentNewsFeed.getString("sectionName");
                Log.d("sectionName", "Value: " + section);

                String timeInMilliseconds = currentNewsFeed.getString("webPublicationDate");
                Log.d("webPublicationDate", "Value: " + timeInMilliseconds);

                String url = currentNewsFeed.getString("webUrl");
                Log.d("webUrl", "Value: " + url);

                if (!author.isEmpty()) {
                    NewsFeed newsFeed = new NewsFeed(title, section, timeInMilliseconds, url, author);
                    newsFeeds.add(newsFeed);
                }else{
                    NewsFeed newsFeed = new NewsFeed(title, section, timeInMilliseconds, url, "");
                    newsFeeds.add(newsFeed);
                }
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the NewsFeed JSON results", e);
        }

        return newsFeeds;
    }

}

