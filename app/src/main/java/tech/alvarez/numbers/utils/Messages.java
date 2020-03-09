package tech.alvarez.numbers.utils;

import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import tech.alvarez.numbers.R;
import tech.alvarez.numbers.activities.ChannelActivity;
import tech.alvarez.numbers.activities.MainActivity;
import tech.alvarez.numbers.activities.SearchActivity;

/**
 * Created by Daniel Alvarez on 8/17/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class Messages {
    public static void showNetwotkError(View view, final MainActivity context) {
        Snackbar.make(view, context.getString(R.string.network_error), Snackbar.LENGTH_SHORT).setAction(context.getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.getChannelsFromAPI();
            }
        }).show();
    }

    public static void showNetwotkError(View view, final ChannelActivity context) {
        Snackbar.make(view, context.getString(R.string.network_error), Snackbar.LENGTH_SHORT).setAction(context.getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.getChannelFromAPI();
            }
        }).show();
    }

    public static void showNetwotkError(View view, final SearchActivity context, final String query) {
        Snackbar.make(view, context.getString(R.string.network_error), Snackbar.LENGTH_SHORT).setAction(context.getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.search(query);
            }
        }).show();
    }
}
