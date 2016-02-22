package com.myluco.tweet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.myluco.tweet.R;
import com.myluco.tweet.models.Tweet;
import com.myluco.tweet.models.User;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class ComposeActivity extends AppCompatActivity {
    private TextView tvUserName;
    private EditText etBody;
    private ImageView ivProfileImage;
    private Button btCancel;
    private Button btTweet;
    private Tweet tweet;
    private User user;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getUser();

        tweet = new Tweet(user);
        populate();

    }

    private void getUser() {
        user = (User) getIntent().getSerializableExtra("user");
    }

    private void populate() {
        client = com.myluco.tweet.TwitterApplication.getRestClient();


        tvUserName = (TextView)findViewById(R.id.tvUsername);
        etBody = (EditText)findViewById(R.id.etBody);
        ivProfileImage = (ImageView)findViewById(R.id.ivProfileImage);
        btCancel = (Button)findViewById(R.id.btCancel);
        btTweet = (Button)findViewById(R.id.btTweet);

        tvUserName.setText(user.getScreenName());
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfileImage);

    }

    private void sendTweet(final Tweet newTweet) {
        client.sendTweet(newTweet, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Toast.makeText(getApplicationContext(), "SUCCESS TWEET 2", Toast.LENGTH_LONG).show();
                Intent data = new Intent();
                Tweet newTweet = new Tweet(response);
                data.putExtra("tweet", newTweet);
                setResult(RESULT_OK, data);
                finish();

//                Toast.makeText(getApplicationContext(),tweets.get(0).getBody(),Toast.LENGTH_LONG).show();
            }

           //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                com.activeandroid.util.Log.v("DEBUG", errorResponse.toString());
//                Toast.makeText(getApplicationContext(), "FAILURE 1T", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onUserException(Throwable error) {
                com.activeandroid.util.Log.v("DEBUG", error.toString());
//                Toast.makeText(getApplicationContext(), "FAILURE 4T", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
    public void cancelClicked(View view) {
        finish();
    }
    public void tweetClicked(View view) {
        //get data from views
        tweet = new Tweet(user);
        tweet.setBody(etBody.getText().toString());
        if (!tweet.getBody().trim().isEmpty()) {
            sendTweet(tweet);
        }else {
            finish();
        }


    }

}
