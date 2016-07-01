package com.codepath.apps.tweeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codepath.apps.tweeter.fragments.UserTimelineFragment;
import com.codepath.apps.tweeter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ProfileActivity extends AppCompatActivity {

    TwitterClient client;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent i = getIntent();
        user = (User) i.getSerializableExtra("user");

        loadUserInfo(user);


/**
            client.getUserInformation(user, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    user = User.fromJSON(response);

                    String sName = user.getScreenName();
                    getSupportActionBar().setTitle(sName);

                    populateProfileHeader(user);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
*/

    }

    public void loadUserTimeline(String screenName) {
        // create user timeline fragment
        UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(screenName);

        // display user fragment within this activity dynamically
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContainer, fragmentUserTimeline);
        ft.commit();
    }

    public void loadUserInfo(final User user) {


        client = TwitterApplication.getRestClient();

        if (user!= null){//screenName != null && !screenName.isEmpty()) {
            client.getUserInformation(user, new JsonHttpResponseHandler() {
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User user = User.fromJSON(response);
                    String sName = user.getScreenName();
                    getSupportActionBar().setTitle(sName);

                    populateProfileHeader(user);
                    loadUserTimeline(user.getScreenName());
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        } else {
            client.getUserInfo(new JsonHttpResponseHandler() {
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    User user = User.fromJSON(response);

                    String sName = user.getScreenName();
                    getSupportActionBar().setTitle(sName);

                    populateProfileHeader(user);
                    loadUserTimeline(user.getScreenName());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }


    }

    // progress within actionbar

    // Instance of the progress action-view
    MenuItem miActionProgressItem;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Store instance of the menu item containing progress
        miActionProgressItem = menu.findItem(R.id.miActionProgress);
        // Extract the action-view from the menu item
        ProgressBar v =  (ProgressBar) MenuItemCompat.getActionView(miActionProgressItem);
        // Return to finish
        return super.onPrepareOptionsMenu(menu);
    }
    public void showProgressBar() {
        // Show progress item
        miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        // Hide progress item
        miActionProgressItem.setVisible(false);
    }

    private void populateProfileHeader(final User user) {
        TextView tvName = (TextView) findViewById(R.id.tvFullName);
        TextView tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);

        tvName.setText(user.getName());
        tvScreenName.setText(user.getScreenName());
        tvTagline.setText(user.getTagline());
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowing.setText(user.getFollowingCount() + " Following");
        Picasso.with(this).load(user.getProfileImageUrl())
                .transform(new RoundedCornersTransformation(3,3)).into(ivProfileImage);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return true;
    }

    public void onComposeClick(MenuItem mi){
        Intent i = new Intent(this,ComposeActivity.class);
        startActivityForResult(i, ComposeActivity.COMPOSE_REQUEST_CODE);
    }

    public void goHome(MenuItem item) {
        Intent i = new Intent(this, TimelineActivity.class);
        startActivity(i);
    }
}
