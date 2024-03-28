@file:Suppress("unused")

package com.example.movies.util

import retrofit2.Response

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Failure<T>(val code: Int, val message: String) : Resource<T>()
}

suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> = try {
    val response = apiCall()
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            Resource.Success(body)
        } else {
            Resource.Failure(code = response.code(), message = response.message())
        }
    } else {
        Resource.Failure(code = response.code(), message = response.message())
    }
} catch (e: Exception) {
    Resource.Failure(code = 0, message = e.message ?: e.toString())
}