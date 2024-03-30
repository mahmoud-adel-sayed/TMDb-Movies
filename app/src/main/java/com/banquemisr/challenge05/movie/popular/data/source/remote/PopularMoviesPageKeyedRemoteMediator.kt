package com.banquemisr.challenge05.movie.popular.data.source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.banquemisr.challenge05.AppDatabase
import com.banquemisr.challenge05.movie.common.data.source.remote.api.MoviesApi
import com.banquemisr.challenge05.movie.common.data.mapper.toPopularMovieEntities
import com.banquemisr.challenge05.movie.popular.data.source.local.entity.PopularMovieEntity
import com.banquemisr.challenge05.movie.popular.data.source.local.entity.PopularRemoteKeyEntity
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PopularMoviesPageKeyedRemoteMediator(
    private val initialPage: Int = 1,
    private val db: AppDatabase,
    private val moviesApi: MoviesApi
) : RemoteMediator<Int, PopularMovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
        return if (System.currentTimeMillis() - (db.popularRemoteKeyDao().getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PopularMovieEntity>
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
            val movies = response.results.toPopularMovieEntities()
            val endOfPaginationReached = movies.isEmpty()
            db.withTransaction {
                // If refreshing, clear table and start over
                if (loadType == LoadType.REFRESH) {
                    db.popularRemoteKeyDao().clearAll()
                    db.popularMoviesDao().clearAll()
                }
                val prevKey = if (page == initialPage) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = movies.map {
                    PopularRemoteKeyEntity(
                        id = it.serverId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                db.popularRemoteKeyDao().insertAll(keys)
                db.popularMoviesDao().insertAll(movies)
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, PopularMovieEntity>
    ): PopularRemoteKeyEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            db.popularRemoteKeyDao().getRemoteKeyById(movie.serverId)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PopularMovieEntity>
    ): PopularRemoteKeyEntity? {
        return state.lastItemOrNull()?.let { movie ->
            db.withTransaction {
                db.popularRemoteKeyDao().getRemoteKeyById(movie.serverId)
            }
        }
    }

    private suspend fun getRemoteKeyByPosition(
        state: PagingState<Int, PopularMovieEntity>
    ): PopularRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.serverId?.let { id ->
                db.withTransaction {
                    db.popularRemoteKeyDao().getRemoteKeyById(id)
                }
            }
        }
    }
}