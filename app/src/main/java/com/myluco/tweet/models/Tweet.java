package com.myluco.tweet.models;


import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by lcc on 2/20/16.
 */
public class Tweet implements Serializable{
    /*
    "text": "You'd be right more often if you thought you were wrong.",
    "contributors": null,
     "created_at": "Tue Aug 28 21:16:23 +0000 2012",
    "id": 240539141056638977,
    "retweet_count": 1,
    "in_reply_to_status_id_str": null,
    "geo": null,
    "retweeted": false,
    "in_reply_to_user_id": null,
    "place": null,
    "source": "web",
    "user": {
      "name": "Taylor Singletary",
      "profile_image_url": "http://a0.twimg.com/profile_images/2546730059/f6a8zq58mg1hn0ha8vie_normal.jpeg",

      },
     */

    private String body;
    private long uid; //unique id for tweet
    private User user;
    private String createdAt;

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Tweet(JSONObject jsonTweet) {
        try {
            body = jsonTweet.getString("text");
            uid = jsonTweet.getLong("id");
            createdAt = jsonTweet.getString("created_at");
            user = new User(jsonTweet.getJSONObject("user"));
        } catch (JSONException e) {

            e.printStackTrace();
        }

    }

    public Tweet(User user) {
        this.user = user;
    }
    public static List<Tweet> ParseTweets (JSONArray response) {
        ArrayList<Tweet> result = new ArrayList<Tweet>();
        JSONObject jsonObject;
        for (int i = 0; i < response.length(); i++) {
            try {
                result.add(new Tweet(response.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
