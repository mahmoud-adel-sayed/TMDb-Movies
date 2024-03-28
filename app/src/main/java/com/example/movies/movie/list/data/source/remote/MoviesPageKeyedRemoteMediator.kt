package com.example.movies.movie.list.data.source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.movies.AppDatabase
import com.example.movies.api.MoviesApi
import com.example.movies.movie.list.data.mapper.toMovieEntities
import com.example.movies.movie.list.data.source.local.entity.MovieEntity
import com.example.movies.movie.list.data.source.local.entity.RemoteKeyEntity
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class MoviesPageKeyedRemoteMediator(
    private val initialPage: Int = 1,
    private val db: AppDatabase,
    private val moviesApi: MoviesApi
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
        return if (System.currentTimeMillis() - (db.remoteKeyDao().getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        return try {
            // Calculate the current page to load depending on the state
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKey = getRemoteKeyByPosition(state)
                    remoteKey?.nextKey?.minus(1) ?: initialPage
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                    prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }
            val response = moviesApi.getPopularMovies(page)
            val movies = response.results.toMovieEntities()
            val endOfPaginationReached = movies.isEmpty()
            db.withTransaction {
                // If refreshing, clear table and start over
                if (loadType == LoadType.REFRESH) {
                    db.remoteKeyDao().clearAll()
                    db.moviesDao().clearAll()
                }
                val prevKey = if (page == initialPage) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    RemoteKeyEntity(
                        id = it.serverId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                db.remoteKeyDao().insertAll(keys)
                db.moviesDao().insertAll(movies)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeyEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            db.remoteKeyDao().getRemoteKeyById(movie.serverId)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeyEntity? {
        return state.lastItemOrNull()?.let { movie ->
            db.withTransaction {
                db.remoteKeyDao().getRemoteKeyById(movie.serverId)
            }
        }
    }

    private suspend fun getRemoteKeyByPosition(
        state: PagingState<Int, MovieEntity>
    ): RemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.serverId?.let { id ->
                db.withTransaction {
                    db.remoteKeyDao().getRemoteKeyById(id)
                }
            }
        }
    }
}