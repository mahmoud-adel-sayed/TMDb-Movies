package com.example.movies.movie.detail.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.example.movies.common.model.UiState
import com.example.movies.movie.detail.domain.model.Genre
import com.example.movies.movie.detail.domain.model.MovieDetails
import com.example.movies.movie.detail.domain.usecase.GetMovieDetailsUseCase
import com.example.movies.util.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class MovieDetailsViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getMovieDetailsUseCase: GetMovieDetailsUseCase

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    @Before
    fun setup() {
        movieDetailsViewModel = MovieDetailsViewModel(getMovieDetailsUseCase)
    }

    @Test
    fun `getMovieDetails() with id param then stateFlow is loading then success`() = runTest {
        val expected = Resource.Success(MovieDetails)
        doAnswer { expected }.whenever(getMovieDetailsUseCase).invoke(any())

        Dispatchers.setMain(Dispatchers.IO)
        val latch = CountDownLatch(2)

        val observer = movieDetailsViewModel.movieDetails.asLiveData()
        var firstEnter = true
        observer.observeForever {
            if (firstEnter) {
                assertTrue(it is UiState.Loading)
            } else {
                assertTrue(it is UiState.Success)
                assertTrue((it as UiState.Success).body == expected.data)
            }
            firstEnter = false
            latch.countDown()
        }

        movieDetailsViewModel.getMovieDetails(id = 1)

        assertTrue(withContext(Dispatchers.IO) {
            latch.await(5, TimeUnit.SECONDS)
        })
    }
}

private val MovieDetails = MovieDetails(
    id = 1,
    title = "title",
    releaseDate = "release date",
    posterUrl = "image.jpg",
    overview = "test overview",
    rating = 7.0,
    genres = listOf(
        Genre(id = 10, name = "Action"),
        Genre(id = 11, name = "Drama"),
        Genre(id = 12, name = "Thriller")
    )
)