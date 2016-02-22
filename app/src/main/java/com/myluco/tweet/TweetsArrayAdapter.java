package com.myluco.tweet;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.myluco.tweet.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by lcc on 2/21/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1,tweets);
    }
    //override and setup own template (instead of simple_list_item_1

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       //get tweet
        Tweet tweet = getItem(position);
       // find and inflate template
        if (convertView == null) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
        }
       // find subviews
        ImageView ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView)convertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView)convertView.findViewById(R.id.tvBody);
        TextView tvTime = (TextView)convertView.findViewById(R.id.tvTime);
        //populate data into subviews
        tvBody.setText(tweet.getBody());
        tvUsername.setText(tweet.getUser().getScreenName());
        tvTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        //dealing with possible recycling issue
        ivProfileImage.setImageResource(android.R.color.transparent);
        //get the image
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        //return the view
        return convertView;
    }
}
