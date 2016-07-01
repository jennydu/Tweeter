package com.codepath.apps.tweeter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.tweeter.fragments.HomeTimelineFragment;
import com.codepath.apps.tweeter.fragments.MentionsTimelineFragment;
import com.codepath.apps.tweeter.models.Tweet;

public class TimelineActivity extends AppCompatActivity {
    private ViewPager vpPager;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        // on some click or some loading we need to wait for...
        ProgressBar pb = (ProgressBar) findViewById(R.id.pbLoading);
        pb.setVisibility(ProgressBar.VISIBLE);
// run a background job and once complete
        pb.setVisibility(ProgressBar.INVISIBLE);

        // get viewpager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set adapter for viewpager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        // find pager sliding tabs
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // attach pager tabs to viewpager
        tabStrip.setViewPager(vpPager);
    }


    public void onProfileView(MenuItem mi){
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    public void onComposeClick(MenuItem mi){
        Intent i = new Intent(this,ComposeActivity.class);
        startActivityForResult(i, ComposeActivity.COMPOSE_REQUEST_CODE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ComposeActivity.COMPOSE_REQUEST_CODE) {
            // Extract name value from result extras
            Tweet tweet = (Tweet) data.getSerializableExtra("tweet");

            ((TweetsPagerAdapter)vpPager.getAdapter()).htFrag.tweets.add(0,tweet);
            // access arraylist
            // access adapter
            ((TweetsPagerAdapter)vpPager.getAdapter()).htFrag.aTweets.notifyDataSetChanged();


        }
    }

    // reutrns order of framgetsn in viewpager
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        public HomeTimelineFragment htFrag;

        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm){
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position==0){
                htFrag = new HomeTimelineFragment();
                return htFrag;
                //return new HomeTimelineFragment();
            }
            else if (position == 1){
                return new MentionsTimelineFragment();
            }
            else{
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }



}
