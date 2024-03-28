package com.example.movies.movie.list.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.movie.list.domain.model.Movie
import com.example.movies.movie.list.domain.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    fun getPopularMovies(): Flow<PagingData<Movie>> =
        getPopularMoviesUseCase().cachedIn(viewModelScope)
}