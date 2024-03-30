package com.banquemisr.challenge05.movie.popular.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.banquemisr.challenge05.movie.common.domain.model.Movie
import com.banquemisr.challenge05.movie.popular.domain.usecase.GetPopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    fun getPopularMovies(): Flow<PagingData<Movie>> =
        getPopularMoviesUseCase().cachedIn(viewModelScope)
}