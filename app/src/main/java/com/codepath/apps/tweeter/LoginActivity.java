package com.codepath.apps.tweeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.codepath.oauth.OAuthLoginActionBarActivity;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
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


	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
	@Override
	public void onLoginSuccess() {


		Intent i = new Intent(this, TimelineActivity.class);
		startActivity(i);
		//Toast.makeText(this, "Success!", Toast.LENGTH_LONG).show();
	}

	// OAuth authentication flow failed, handle the error
	// i.e Display an error dialog or toast
	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}

	// Click handler method for the button used to start OAuth flow
	// Uses the client to initiate OAuth authorization
	// This should be tied to a button used to login
	public void loginToRest(View view) {
		getClient().connect();
	}

}


