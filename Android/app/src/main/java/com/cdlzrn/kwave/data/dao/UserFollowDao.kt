package com.cdlzrn.kwave.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cdlzrn.kwave.data.entity.UserEntity
import com.cdlzrn.kwave.data.entity.UserFollowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFollowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllFollows(follow: List<UserFollowEntity>)

    @Query("""
        SELECT u.*
        FROM user_follow AS uf 
        INNER JOIN user AS u ON uf.followed_id = u.id
        WHERE uf.follower_id = :userId
    """)
    fun getFollowed(userId: Long): Flow<List<UserEntity>>
}