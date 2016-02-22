package com.myluco.tweet.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by lcc on 2/20/16.
 */
public class User implements Serializable{
    /*
    "user": {
      "name": "Taylor Singletary",
      "profile_image_url": "http://a0.twimg.com/profile_images/2546730059/f6a8zq58mg1hn0ha8vie_normal.jpeg",

      },
     */
    private String name;
    private long uid;
    private String profileImageUrl;
    private String screenName;

    public long getUid() {
        return uid;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }



    public User(JSONObject jsonUser) {
        try {
            name = jsonUser.getString("name");
            profileImageUrl = jsonUser.getString("profile_image_url");
            uid = jsonUser.getLong("id");
            screenName = jsonUser.getString("screen_name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public User() {

    }
}
