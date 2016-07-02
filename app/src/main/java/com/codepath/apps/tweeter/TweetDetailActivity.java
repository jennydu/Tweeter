package com.codepath.apps.tweeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.tweeter.models.Tweet;
import com.codepath.apps.tweeter.models.User;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class TweetDetailActivity extends AppCompatActivity {

    TwitterClient client;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        Intent i = getIntent();
        tweet = (Tweet) i.getSerializableExtra("tweet");

        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        ImageView ivProfilePicture = (ImageView) findViewById(R.id.ivProfilePicture);
        TextView tvBody = (TextView) findViewById(R.id.tvBody);

        User user = tweet.getUser();

        tvName.setText(user.getName());
        Picasso.with(this).load(user.getProfileImageUrl())
                .transform(new RoundedCornersTransformation(3,3)).into(ivProfilePicture);
        tvBody.setText(tweet.getBody());



    }
}
