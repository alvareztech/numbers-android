package tech.alvarez.numbers

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import tech.alvarez.numbers.database.Channel
import tech.alvarez.numbers.database.ChannelDatabaseDao
import tech.alvarez.numbers.database.ChannelsDatabase
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ChannelsDatabaseTest {
    private lateinit var sleepDao: ChannelDatabaseDao
    private lateinit var db: ChannelsDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ChannelsDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        sleepDao = db.databaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetChannel() {
        val channel = Channel("123", "Daniel", "Daniel Alvarez channel")
        sleepDao.insert(channel)
        val c = sleepDao.getFirstChannel()
        assertEquals(c?.title, "Daniel")
    }

}