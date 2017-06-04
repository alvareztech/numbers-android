package tech.alvarez.numbers.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import tech.alvarez.numbers.db.dao.ChannelDao;
import tech.alvarez.numbers.db.entity.ChannelEntity;

@Database(entities = {ChannelEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ChannelDao channelModel();


    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                    // To simplify the codelab, allow queries on the main thread.
                    // Don't do this on a real app! See PersistenceBasicSample for an example.
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
