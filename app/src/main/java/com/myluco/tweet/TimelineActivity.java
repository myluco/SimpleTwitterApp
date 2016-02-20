package com.myluco.tweet;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;

    class FooHandler extends JsonHttpResponseHandler {
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

        @Override
        protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
            Toast.makeText(getApplicationContext(),"FAILURE 5",Toast.LENGTH_LONG).show();
            return super.obtainMessage(responseMessageId, responseMessageData);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        client = com.myluco.tweet.TwitterApplication.getRestClient();
        getRateLimits();
//        populateTimeline();

    }

    private void getRateLimits() {

       client.getRateLimit(new FooHandler());

    }

    //send API; fill listView
    private void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler(){

            //SUCCESS

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
                Log.i("DEBUG", errorResponse.toString());
                Toast.makeText(getApplicationContext(),"FAILURE 1",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.i("DEBUG", errorResponse.toString());
                Toast.makeText(getApplicationContext(),"FAILURE 2",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                 Log.i("DEBUG", responseString);
                Toast.makeText(getApplicationContext(),"FAILURE 3",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("DEBUG", responseString);
                Toast.makeText(getApplicationContext(),"SUCCESS 3",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUserException(Throwable error) {
                Log.i("DEBUG", error.toString());
                Toast.makeText(getApplicationContext(),"FAILURE 4",Toast.LENGTH_LONG).show();
            }

            @Override
            protected Message obtainMessage(int responseMessageId, Object responseMessageData) {
                Toast.makeText(getApplicationContext(),"FAILURE 5",Toast.LENGTH_LONG).show();
                return super.obtainMessage(responseMessageId, responseMessageData);
            }
        });
    }

}
