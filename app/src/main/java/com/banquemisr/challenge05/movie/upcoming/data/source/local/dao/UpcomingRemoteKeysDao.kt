package com.banquemisr.challenge05.movie.upcoming.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.banquemisr.challenge05.movie.upcoming.data.source.local.entity.UpcomingRemoteKeyEntity

@Dao
interface UpcomingRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<UpcomingRemoteKeyEntity>)

    @Query("SELECT * FROM upcoming_remote_keys WHERE id = :id")
    suspend fun getRemoteKeyById(id: Long): UpcomingRemoteKeyEntity?

    @Query("Select created_at From upcoming_remote_keys Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

    @Query("DELETE FROM upcoming_remote_keys")
    suspend fun clearAll()
}