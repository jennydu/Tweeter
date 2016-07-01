package com.codepath.apps.tweeter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.tweeter.models.Tweet;
import com.codepath.apps.tweeter.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * takes tweets and turns them into views that will be displayed in a list
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{



    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0, tweets);
    }

    //override and setup our own template

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User user;
        // 1. get tweet
        Tweet tweet = getItem(position);

        // 2. find/inflate template
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }

        // 3. find subviews to fill with data in teh template
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
        TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);

        // populate data into subviews
        user = tweet.getUser();
        String screenName = user.getScreenName();
        tvScreenName.setText(screenName);

        String userName = user.getName();
        tvUserName.setText(userName);

        tvTimeStamp.setText(tweet.getRelativeDate());

        tvBody.setText(tweet.getBody());
        ivProfileImage.setImageResource(android.R.color.transparent); // clears out old image for recycler view
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        ivProfileImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "clicked on image!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("user", user);
                getContext().startActivity(i);
            }
        });

        // return view to be inserted into the list
        return convertView;
    }

}
