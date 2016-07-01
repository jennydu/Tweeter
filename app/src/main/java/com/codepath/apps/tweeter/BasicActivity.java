package com.codepath.apps.tweeter;

import android.view.MenuItem;

/**
 * Created by jenniferdu on 7/1/16.
 */
public abstract class BasicActivity {
    public MenuItem miActionProgressItem;

    public void showProgressBar() {
        if (miActionProgressItem != null)
            miActionProgressItem.setVisible(true);
    }

    public void hideProgressBar() {
        if (miActionProgressItem != null)
            miActionProgressItem.setVisible(false);
    }

}
