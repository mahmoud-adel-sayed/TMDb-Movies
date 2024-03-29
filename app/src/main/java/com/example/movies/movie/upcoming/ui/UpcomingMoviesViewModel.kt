package com.example.movies.movie.upcoming.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.movie.common.domain.model.Movie
import com.example.movies.movie.upcoming.domain.usecase.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
) : ViewModel() {

    fun getUpcomingMovies(): Flow<PagingData<Movie>> =
        getUpcomingMoviesUseCase().cachedIn(viewModelScope)
}