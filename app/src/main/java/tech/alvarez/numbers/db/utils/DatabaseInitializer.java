package tech.alvarez.numbers.db.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import tech.alvarez.numbers.db.AppDatabase;
import tech.alvarez.numbers.db.entity.ChannelEntity;

public class DatabaseInitializer {

    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static void populateWithTestData(AppDatabase db) {
        db.channelModel().deleteAll();

        addChannelEntity(db, "1", "Canal 1", "Descripción Canal 1", 40);
        addChannelEntity(db, "2", "Canal 2", "Descripción Canal 2", 1432134);
        addChannelEntity(db, "3", "Canal 3", "Descripción Canal 3", 421234);
    }

    private static ChannelEntity addChannelEntity(final AppDatabase db, final String id, final String name,
                                                  final String description, final int subscribers) {
        ChannelEntity channel = new ChannelEntity();
        channel.id = id;
        channel.name = name;
        channel.description = description;
        channel.subscribers = subscribers;
        db.channelModel().insertChannel(channel);
        return channel;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
