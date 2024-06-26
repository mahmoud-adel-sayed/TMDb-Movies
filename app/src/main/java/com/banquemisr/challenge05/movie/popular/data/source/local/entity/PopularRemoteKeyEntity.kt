package com.banquemisr.challenge05.movie.popular.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_remote_keys")
data class PopularRemoteKeyEntity(
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