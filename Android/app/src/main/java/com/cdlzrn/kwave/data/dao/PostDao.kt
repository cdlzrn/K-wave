package com.cdlzrn.kwave.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cdlzrn.kwave.data.entity.PostEntity
import com.cdlzrn.kwave.data.model.PostData
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(posts: List<PostEntity>)

    @Query("""
        SELECT 
            p.description, 
            p.image, 
            p.date_of_publication AS time, 
            p.like_count AS countLike, 
            
            COUNT(DISTINCT c.id) AS countComment,
                        
            p.reposts_count AS countRepost, 
            u.name AS authorName, 
            u.avatar AS authorImage
            
        FROM post AS p 
        INNER JOIN user AS u ON p.author_id = u.id 
        LEFT JOIN comment AS c ON c.comment_target_id = p.id AND c.comment_target_type = "POST"
            
        WHERE p.author_id = :userId
        GROUP BY p.id, u.id
    """)
    fun getPostsByUser(userId: Long): Flow<List<PostData>>

    @Query("""
        SELECT 
            p.description, 
            p.image, 
            p.date_of_publication AS time, 
            p.like_count AS countLike, 
            
            COUNT(DISTINCT c.id) AS countComment,
                        
            p.reposts_count AS countRepost, 
            u.name AS authorName, 
            u.avatar AS authorImage
            
        FROM post AS p 
        INNER JOIN user AS u ON p.author_id = u.id 
        LEFT JOIN user_follow AS f ON p.author_id = f.followed_id 
        LEFT JOIN comment AS c ON c.comment_target_id = p.id AND c.comment_target_type = "POST"
            
        WHERE f.follower_id = :userId OR u.id = :userId
        GROUP BY p.id, u.id 
        ORDER BY p.date_of_publication DESC 
    """)
    fun getFollowedAndUserPostWithAuthor(userId: Long): Flow<List<PostData>>

}