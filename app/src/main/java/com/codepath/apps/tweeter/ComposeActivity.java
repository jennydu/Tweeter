package com.codepath.apps.tweeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.tweeter.models.Tweet;
import com.codepath.apps.tweeter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ComposeActivity extends AppCompatActivity {

    private TwitterClient client;
    TextView tvCharCount;
    public static final int COMPOSE_REQUEST_CODE = 69;
    EditText etValue;
    ImageView ivProfileImage;
    TextView tvName;
    TextView tvScreenName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        setContentView(R.layout.activity_compose);

        etValue = (EditText) findViewById(R.id.etTweet);
        tvCharCount = (TextView) findViewById(R.id.tvCharCount);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvScreenName = (TextView) findViewById(R.id.tvScreenName);
        tvName = (TextView) findViewById(R.id.tvName);

        etValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Fires right as the text is being changed (even supplies the range of text)
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // Fires right before text is changing
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
                int length = 140 - s.length();
                tvCharCount.setText(Integer.toString(length));
            }
        });

        client.getUserInfo(new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User user = User.fromJSON(response);

                // get propic, username, and name
                Picasso.with(getApplicationContext()).load(user.getProfileImageUrl())
                        .transform(new RoundedCornersTransformation(3, 3)).into(ivProfileImage);

                tvName.setText(user.getName());
                tvScreenName.setText("@" + user.getScreenName());

                getSupportActionBar().setTitle("Compose new tweet");


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }





    public void onSubmit(final View v){
        final String status = etValue.getText().toString();


        client.post(status, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {

                // give the tweet back to the home timeline
                Tweet newTweet = Tweet.fromJSON(json);

                Intent data = new Intent();
                data.putExtra("tweet", newTweet); // serializable?
                setResult(RESULT_OK, data);
                // on submit, leave the activity
                finish();

                // go to the home timeline

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });





    }
}
