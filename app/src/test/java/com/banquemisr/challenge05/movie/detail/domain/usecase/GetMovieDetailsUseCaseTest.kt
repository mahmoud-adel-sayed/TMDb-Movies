package com.banquemisr.challenge05.movie.detail.domain.usecase

import com.banquemisr.challenge05.movie.common.domain.repo.MoviesRepository
import com.banquemisr.challenge05.movie.detail.domain.model.Genre
import com.banquemisr.challenge05.movie.detail.domain.model.MovieDetails
import com.banquemisr.challenge05.util.Resource
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailsUseCaseTest {
    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var useCase: GetMovieDetailsUseCase

    @Before
    fun setup() {
        useCase = GetMovieDetailsUseCase(moviesRepository)
    }

    @Test
    fun `invoke() with proper id param then success response returned`() = runBlocking {
        val expected = Resource.Success(MovieDetails)
        doAnswer { expected }.whenever(moviesRepository).getMovieDetails(any())
        val actual = useCase(id = 1)
        assertEquals(expected, actual)
        assertEquals(expected.data, (actual as Resource.Success).data)
    }

    @Test
    fun `invoke() with exception then failure response returned`() = runBlocking {
        val expected = Resource.Failure<MovieDetails>(code = 400, message = "error")
        doAnswer { expected }.whenever(moviesRepository).getMovieDetails(any())
        val actual = useCase(id = 1)
        assertEquals(expected, actual)
        assertEquals(expected.code, (actual as Resource.Failure).code)
        assertEquals(expected.message, actual.message)
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