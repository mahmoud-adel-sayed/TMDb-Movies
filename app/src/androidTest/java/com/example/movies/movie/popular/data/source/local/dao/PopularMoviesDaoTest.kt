@file:Suppress("DEPRECATION")

package com.example.movies.movie.popular.data.source.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.example.movies.AppDatabase
import com.example.movies.movie.popular.data.source.local.entity.PopularMovieEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PopularMoviesDaoTest {
    private lateinit var popularMoviesDao: PopularMoviesDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        popularMoviesDao = db.popularMoviesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeMoviesAndReadInList() = runBlocking {
        popularMoviesDao.insertAll(PopularMovies)
        val movies = popularMoviesDao.getPopularMovies()
        assertEquals(movies.size, PopularMovies.size)
        assertEquals(movies, PopularMovies)
    }

    @Test
    @Throws(Exception::class)
    fun writeMoviesAndClearAllThenRead() = runBlocking {
        popularMoviesDao.insertAll(PopularMovies)
        popularMoviesDao.clearAll()
        val movies = popularMoviesDao.getPopularMovies()
        assertEquals(movies.size, 0)
    }
}

private val PopularMovies = listOf(
    PopularMovieEntity(
        id = 1,
        serverId = 1,
        title = "Movie 1",
        releaseDate = "10 March, 2023",
        posterUrl = "image.jpg"
    ),
    PopularMovieEntity(
        id = 2,
        serverId = 2,
        title = "Movie 2",
        releaseDate = "20 March, 2023",
        posterUrl = "image2.jpg"
    ),
    PopularMovieEntity(
        id = 3,
        serverId = 3,
        title = "Movie 3",
        releaseDate = "30 March, 2023",
        posterUrl = "image3.jpg"
    )
)