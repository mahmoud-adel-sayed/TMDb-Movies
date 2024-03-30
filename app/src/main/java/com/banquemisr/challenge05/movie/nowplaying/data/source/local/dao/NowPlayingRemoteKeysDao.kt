package com.banquemisr.challenge05.movie.nowplaying.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.banquemisr.challenge05.movie.nowplaying.data.source.local.entity.NowPlayingRemoteKeyEntity

@Dao
interface NowPlayingRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<NowPlayingRemoteKeyEntity>)

    @Query("SELECT * FROM now_playing_remote_keys WHERE id = :id")
    suspend fun getRemoteKeyById(id: Long): NowPlayingRemoteKeyEntity?

    @Query("Select created_at From now_playing_remote_keys Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

    @Query("DELETE FROM now_playing_remote_keys")
    suspend fun clearAll()
}