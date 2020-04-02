package tech.alvarez.numbers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Channel::class], version = 1, exportSchema = false)
abstract class ChannelsDatabase : RoomDatabase() {

    abstract val databaseDao: ChannelDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: ChannelsDatabase? = null

        fun getInstance(context: Context): ChannelsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ChannelsDatabase::class.java,
                        "channels_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}