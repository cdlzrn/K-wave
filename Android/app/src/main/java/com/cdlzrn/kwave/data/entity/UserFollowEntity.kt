package com.cdlzrn.kwave.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "user_follow",
    primaryKeys = ["follower_id", "followed_id"],
    indices = [Index(value = ["followed_id"])]
)
data class UserFollowEntity(
    @ColumnInfo(name = "follower_id") val followerId: Long,
    @ColumnInfo(name = "followed_id") val followedId: Long
)