package tech.alvarez.numbers.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import io.realm.RealmResults;
import tech.alvarez.numbers.models.db.ChannelRealm;

/**
 * Created by Daniel Alvarez on 24/7/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class Util {


    public static String getURLChannel(String channelId) {
        return Constants.YOUTUBE_CHANNEL_BASE_URL + channelId;
    }


    public static void setTypeSortPreference(int a, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("type_sort_preference", a);
        editor.apply();
    }

    public static int getTypeSortPreference(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("type_sort_preference", Constants.TYPE_SORT_SUBS);
    }

    public static void setOrderPreference(int a, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("order_preference", a);
        editor.apply();
    }

    public static int getOrderPreference(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt("order_preference", Constants.DESCENDING_ORDER);
    }

    public static void setEnableDifference(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("difference", true);
        editor.apply();
    }

    public static void setDisableDifference(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("difference", false);
        editor.apply();
    }

    public static boolean isEnableDifference(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("difference", false);
    }
}
