package com.banquemisr.challenge05.movie.popular.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.banquemisr.challenge05.movie.popular.data.source.local.entity.PopularRemoteKeyEntity

@Dao
interface PopularRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<PopularRemoteKeyEntity>)

    @Query("SELECT * FROM popular_remote_keys WHERE id = :id")
    suspend fun getRemoteKeyById(id: Long): PopularRemoteKeyEntity?

    @Query("Select created_at From popular_remote_keys Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

    @Query("DELETE FROM popular_remote_keys")
    suspend fun clearAll()
}