package com.example.movies.di

import com.example.movies.api.MoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.addInterceptor { chain ->
            var request = chain.request()
            val url = request.url.newBuilder()
                .addQueryParameter("language","en-US")
                .build()
            request = request
                .newBuilder()
                .url(url)
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer $AUTHORIZATION_TOKEN")
                .build()
            chain.proceed(request)
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi = retrofit.create(MoviesApi::class.java)
}

private const val BASE_URL = "https://api.themoviedb.org/3/"

// NOTE: Token should not be embedded in source code but it is fixed here for the sack of this sample
private const val AUTHORIZATION_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxNmRiYzc2YWM1MjU5ZDZiYz" +
        "RhNTY4N2I0NDJkNzczYSIsInN1YiI6IjY0ZmIwMWI0ZmZjOWRlMDBhYzUwOWZlZSIsInNjb3BlcyI6WyJhcGlfc" +
        "mVhZCJdLCJ2ZXJzaW9uIjoxfQ.z9t3f4tDUA8Zlr1BatY0nod8WbQiBPrilOQQHf3ISwU"