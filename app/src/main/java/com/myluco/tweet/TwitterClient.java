package com.myluco.tweet;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.myluco.tweet.models.Tweet;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "s8kB0ZTakBchK8kVHF0VogLsD";       // Change this
	public static final String REST_CONSUMER_SECRET = "AfOXj2UGILFgcvYoTbfTgPGV05p7lyqlZrrpDfYNhPUIfFa4LM"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);

	}


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
	void getHomeTimeline(AsyncHttpResponseHandler handler, long maxId) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", TimelineActivity.PER_PAGE);
		if (maxId == 0) {
			params.put("since_id", 1);
		}else {
			params.put("max_id",maxId);
		}
		getClient().get(apiUrl, params, handler);
	}

    //rate Limit
    void getRateLimit(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("application/rate_limit_status.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("resources", "users");
		getClient().get(apiUrl, params, handler);
    }

	void getUser(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		getClient().get(apiUrl, params, handler);
	}
    /*
    https://api.twitter.com/1.1/statuses/update.json
    status (body of text)
    in_reply_to_status_id (optional)
     */
    public void sendTweet(Tweet newTweet, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
        params.put("status",newTweet.getBody());
		getClient().post(apiUrl, params, handler);
    }

    //Compose a Tweet
}