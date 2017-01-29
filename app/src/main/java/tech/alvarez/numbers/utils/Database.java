package tech.alvarez.numbers.utils;

import android.util.Log;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import tech.alvarez.numbers.models.db.ChannelRealm;

/**
 * Created by Daniel Alvarez on 8/5/16.
 * Copyright © 2016 Alvarez.tech. All rights reserved.
 */
public class Database {

    public static boolean inTheDatabase(String channelId) {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ChannelRealm> query = realm.where(ChannelRealm.class);
        ChannelRealm channelRealm = query.equalTo("id", channelId).findFirst();
        realm.close();
        return channelRealm != null;
    }

    public static String getIdChannelsInRealm(Realm realm) {
        String ids = "";
        RealmResults<ChannelRealm> results = realm.where(ChannelRealm.class).findAll();
        Log.d(Constants.TAG, "getIdChannelsInRealm> " + results.size());
        for (ChannelRealm channelRealm : results) {
            ids += channelRealm.getId() + ",";
        }
        return ids;
    }

    public static int countChannels(Realm realm) {
        return realm.where(ChannelRealm.class).findAll().size();
    }
}
