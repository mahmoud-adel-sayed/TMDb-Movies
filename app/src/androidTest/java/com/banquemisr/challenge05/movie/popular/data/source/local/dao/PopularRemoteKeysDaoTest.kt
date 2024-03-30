@file:Suppress("DEPRECATION")

package com.banquemisr.challenge05.movie.popular.data.source.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.banquemisr.challenge05.AppDatabase
import com.banquemisr.challenge05.movie.popular.data.source.local.entity.PopularRemoteKeyEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PopularRemoteKeysDaoTest {
    private lateinit var popularRemoteKeysDao: PopularRemoteKeysDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        popularRemoteKeysDao = db.popularRemoteKeyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeRemoteKeysAndReadInList() = runBlocking {
        popularRemoteKeysDao.insertAll(RemoteKeys)
        val remoteKey = popularRemoteKeysDao.getRemoteKeyById(id = 1)
        assertNotNull(remoteKey)
        assertEquals(remoteKey, RemoteKeys[0])
    }

    @Test
    @Throws(Exception::class)
    fun writeRemoteKeysAndClearAllThenRead() = runBlocking {
        popularRemoteKeysDao.insertAll(RemoteKeys)
        popularRemoteKeysDao.clearAll()
        val remoteKey = popularRemoteKeysDao.getRemoteKeyById(1)
        assertNull(remoteKey)
    }
}

private val RemoteKeys = listOf(
    PopularRemoteKeyEntity(
        id = 1,
        prevKey = null,
        nextKey = 2
    ),
    PopularRemoteKeyEntity(
        id = 2,
        prevKey = null,
        nextKey = 2
    )
)