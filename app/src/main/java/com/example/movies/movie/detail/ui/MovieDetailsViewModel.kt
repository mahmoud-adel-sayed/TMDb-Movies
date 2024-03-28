package com.example.movies.movie.detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.common.model.UiState
import com.example.movies.movie.detail.domain.model.MovieDetails
import com.example.movies.movie.detail.domain.usecase.GetMovieDetailsUseCase
import com.example.movies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val _movieDetails: MutableStateFlow<UiState<MovieDetails>> =
        MutableStateFlow(UiState.Loading())
    val movieDetails: StateFlow<UiState<MovieDetails>> = _movieDetails

    fun getMovieDetails(id: Long) {
        viewModelScope.launch {
            when (val resource = getMovieDetailsUseCase(id)) {
                is Resource.Success -> {
                    _movieDetails.value = UiState.Success(body = resource.data)
                }
                is Resource.Failure -> {
                    _movieDetails.value = UiState.Error(errorMessage = resource.message)
                }
            }
        }
    }
}