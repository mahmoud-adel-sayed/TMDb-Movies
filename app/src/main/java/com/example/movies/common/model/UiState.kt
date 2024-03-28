package com.example.movies.common.model

sealed class UiState<T : Any> {
    class Loading<T : Any> : UiState<T>()

    data class Error<T : Any>(val errorMessage: String) : UiState<T>()

    data class Success<T : Any>(val body: T) : UiState<T>()
}