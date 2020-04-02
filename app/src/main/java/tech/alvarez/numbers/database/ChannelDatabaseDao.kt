package tech.alvarez.numbers.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChannelDatabaseDao {

    @Insert
    fun insert(channel: Channel)

    @Update
    fun update(channel: Channel)

    @Query("SELECT * FROM channels WHERE id = :key")
    fun get(key: String): Channel

    @Delete
    fun deleteAllChannels(channels: List<Channel>): Int

    @Query("SELECT * FROM channels ORDER BY title")
    fun getAllChannels(): LiveData<List<Channel>>

    @Query("SELECT * FROM channels ORDER BY title LIMIT 1")
    fun getFirstChannel(): Channel?
}