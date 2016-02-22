package com.myluco.tweet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;
import com.myluco.tweet.models.Tweet;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class TimelineActivity extends AppCompatActivity {

    private static final int PAGE_LIMIT = 5 ;
    public static final int PER_PAGE = 25 ;
    private int count = 0;

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter adTweet;
    private ListView lvTweets;
    private HometimeResponseHandler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handler = new HometimeResponseHandler();
        client = com.myluco.tweet.TwitterApplication.getRestClient();
//        getRateLimits();
        populate();
        populateTimeline(count);

    }

    private void populate() {
        lvTweets = (ListView)findViewById(R.id.lvTweets);
        tweets = new ArrayList<Tweet>();
        adTweet = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(adTweet);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(page);
                // or customLoadMoreDataFromApi(totalItemsCount);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
    }

    private boolean moreTweets() {
        if (tweets.size() == PER_PAGE) {
            return true;
        }
        return false;
    }

    private void customLoadMoreDataFromApi(int offset) {
        // This method probably sends out a network request and appends new data items to your adapter.
        // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
        // Deserialize API response and then construct new objects to append to the adapter
        count++;
        if ((count < PAGE_LIMIT) || (moreTweets())){
            loadMorePages(count);
        }


    }

    private void loadMorePages(int count) {

        populateTimeline(count);
    }



    //send API; fill listView
    private void populateTimeline(int count) {
        long maxId = 0;
        if (count > 0)  maxId = tweets.get(tweets.size() - 1).getUid();
        client.getHomeTimeline(handler, maxId);

    }

    private void getRateLimits() {

        client.getRateLimit(new RateLimitHandler());

    }

    class RateLimitHandler extends JsonHttpResponseHandler {


        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            Log.i("DEBUG", response.toString());
            Toast.makeText(getApplicationContext(),"SUCCESS 1",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            Log.i("DEBUG", response.toString());
            Toast.makeText(getApplicationContext(),"SUCCESS 2",Toast.LENGTH_LONG).show();
        }

        //FAILURE
        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.i("DEBUG-R", errorResponse.toString());
            Toast.makeText(getApplicationContext(),"FAILURE 1",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
            Log.i("DEBUG-R", errorResponse.toString());
            Toast.makeText(getApplicationContext(),"FAILURE 2",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            Log.i("DEBUG-R", responseString);
            Toast.makeText(getApplicationContext(),"FAILURE 3",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseString) {
            Log.i("DEBUG-R", responseString);
            Toast.makeText(getApplicationContext(),"SUCCESS 3",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onUserException(Throwable error) {
            Log.i("DEBUG-R", error.toString());
            Toast.makeText(getApplicationContext(),"FAILURE 4",Toast.LENGTH_LONG).show();
        }


    }

    class HometimeResponseHandler extends JsonHttpResponseHandler{
        //SUCCESS

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                Log.i("DEBUG", response.toString());
            List<Tweet> localList = Tweet.ParseTweets(response);
            if (localList != null) {
                adTweet.addAll(localList);
            }
//                Toast.makeText(getApplicationContext(),tweets.get(0).getBody(),Toast.LENGTH_LONG).show();
        }

        //FAILURE
        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            Log.i("DEBUG", errorResponse.toString());
            Toast.makeText(getApplicationContext(),"FAILURE 1",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {

        }


        @Override
        public void onUserException(Throwable error) {
            Log.i("DEBUG", error.toString());
//                Toast.makeText(getApplicationContext(),"FAILURE 4",Toast.LENGTH_LONG).show();
        }
    }

}
