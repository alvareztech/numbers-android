package tech.alvarez.numbers.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import tech.alvarez.numbers.db.entity.ChannelEntity;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface ChannelDao {

    @Query("SELECT * FROM channels")
    List<ChannelEntity> getAllChannels();

    @Query("SELECT * FROM channels ORDER BY subscribers DESC")
    LiveData<List<ChannelEntity>> findAllChannels();

    @Query("SELECT * FROM channels WHERE favorite=1")
    List<ChannelEntity> getFavoriteChannels();

    @Query("SELECT * FROM channels WHERE favorite=1")
    LiveData<List<ChannelEntity>> findFavoriteChannels();

    @Query("SELECT * FROM channels WHERE id=:id")
    ChannelEntity findChannelById(String id);

    @Query("SELECT * FROM channels WHERE id=:id")
    LiveData<ChannelEntity> findChannel(String id);

    @Query("SELECT id FROM channels")
    List<String> getChannelIds();

    @Query("SELECT * FROM channels ORDER BY subscribers ASC")
    List<ChannelEntity> getChannelsAsc();

    @Query("SELECT * FROM channels ORDER BY subscribers DESC")
    List<ChannelEntity> getChannelsDesc();

    @Query("SELECT * FROM channels WHERE favorite=1 ORDER BY subscribers ASC")
    List<ChannelEntity> getFavoriteChannelsAsc();

    @Query("SELECT * FROM channels WHERE favorite=1 ORDER BY subscribers DESC")
    List<ChannelEntity> getFavoriteChannelsDesc();

    @Query("SELECT COUNT(*) FROM channels")
    int getCountChannels();

    @Query("SELECT COUNT(*) FROM channels WHERE id=:id")
    int getCountChannels(String id);

    @Insert(onConflict = IGNORE)
    void insertChannel(ChannelEntity channel);

    @Update
    void updateChannel(ChannelEntity... channel);

    @Update
    void updateChannel(List<ChannelEntity> channels);

    @Delete
    void deleteChannel(ChannelEntity channel);

    @Query("DELETE FROM channels")
    void deleteAll();

}
