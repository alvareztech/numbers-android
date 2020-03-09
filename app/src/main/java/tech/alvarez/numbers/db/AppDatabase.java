package tech.alvarez.numbers.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Context;

import tech.alvarez.numbers.db.dao.ChannelDao;
import tech.alvarez.numbers.db.entity.ChannelEntity;

@Database(entities = {ChannelEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ChannelDao channelModel();


    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "numbers")
                    .allowMainThreadQueries()
                    .build();
//            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
//                    .allowMainThreadQueries()
//                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
