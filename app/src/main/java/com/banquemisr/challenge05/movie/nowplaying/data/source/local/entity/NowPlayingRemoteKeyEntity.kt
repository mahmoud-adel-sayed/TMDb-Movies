package com.banquemisr.challenge05.movie.nowplaying.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "now_playing_remote_keys")
data class NowPlayingRemoteKeyEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "prev_key")
    val prevKey: Int?,

    @ColumnInfo(name = "next_key")
    val nextKey: Int?,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)