package com.codepath.apps.tweeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codepath.apps.tweeter.models.User;

public class FollowersActivity extends AppCompatActivity {

    User user;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        Intent i = getIntent();
        user = (User) i.getSerializableExtra("user");

        loadFollowersInfo(user);
    }

    public void loadFollowersInfo(User user){
        client = TwitterApplication.getRestClient();
    }
}
