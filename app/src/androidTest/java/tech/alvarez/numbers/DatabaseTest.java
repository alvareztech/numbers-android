package tech.alvarez.numbers;

import androidx.room.Room;
import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import tech.alvarez.numbers.db.AppDatabase;
import tech.alvarez.numbers.db.dao.ChannelDao;
import tech.alvarez.numbers.db.entity.ChannelEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {

    private ChannelDao mChannelDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mChannelDao = mDb.channelModel();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {

        mDb.channelModel().deleteAll();

        List<ChannelEntity> channels = mChannelDao.getAllChannels();
        assertThat(channels.size(), equalTo(0));

        ChannelEntity channel = addChannelEntity(mDb, "1", "Canal 1", "Descripción Canal 1", 40);
        addChannelEntity(mDb, "2", "Canal 2", "Descripción Canal 2", 1432134);
        addChannelEntity(mDb, "3", "Canal 3", "Descripción Canal 3", 421234);

        channels = mChannelDao.getAllChannels();
        assertThat(channels.size(), equalTo(3));
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
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

}
