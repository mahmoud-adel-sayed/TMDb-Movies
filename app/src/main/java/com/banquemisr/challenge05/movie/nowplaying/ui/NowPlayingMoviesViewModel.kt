package com.banquemisr.challenge05.movie.nowplaying.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.banquemisr.challenge05.movie.common.domain.model.Movie
import com.banquemisr.challenge05.movie.nowplaying.domain.usecase.GetNowPlayingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class NowPlayingMoviesViewModel @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMoviesUseCase
) : ViewModel() {

    fun getNowPlayingMovies(): Flow<PagingData<Movie>> =
        getNowPlayingMoviesUseCase().cachedIn(viewModelScope)
}