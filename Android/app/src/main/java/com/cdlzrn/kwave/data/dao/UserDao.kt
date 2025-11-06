package com.cdlzrn.kwave.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.cdlzrn.kwave.data.entity.UserEntity
import com.cdlzrn.kwave.data.model.UserData
import com.cdlzrn.kwave.data.model.UserForProductData
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert
    suspend fun create(user: UserEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(users: List<UserEntity>): List<Long>

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("""
        SELECT *
        FROM user
    """)
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("""
        SELECT
            u.id AS id,
            u.name AS name,
            u.avatar AS avatar,
            u.rate AS rate
        FROM user AS u
        WHERE id = :userId
    """)
    fun getFlowUserByIdForProduct(userId: Long): Flow<UserForProductData>

    @Query("""
        SELECT * 
        FROM user 
        WHERE user.id = :id
    """)
    suspend fun getUserById(id: Long): UserEntity?

    @Query("""
        SELECT *
        FROM user
        WHERE phone = :login OR mail = :login
    """)
    suspend fun getUserByLogin(login: String): UserEntity?

    @Query("""
        SELECT
            u.id AS id,
            u.name AS name,
            u.avatar AS avatar,
            u.mail AS mail,
            u.phone AS phone,
            u.nick_name AS nickName,
            u.rate AS rate,
            u.date_of_birth AS dateOfBirth
        FROM user AS u
        WHERE u.id = :id
    """)
    fun getUserDataById(id: Long): Flow<UserData>

    @Query("""
        SELECT * 
        FROM user 
        WHERE user.mail = :mail
    """)
    suspend fun getUserByEmail(mail: String): UserEntity?

    @Query("""
        SELECT * 
        FROM user 
        WHERE user.phone = :phone
    """)
    suspend fun getUserByPhone(phone: String): UserEntity?

    @Query("""
        SELECT * 
        FROM user 
        WHERE user.mail = :nickName
    """)
    suspend fun getUserByNickName(nickName: String): UserEntity?

    @Query("""
        SELECT *
        FROM user
        WHERE is_artist
    """)
    fun getArtists(): Flow<List<UserEntity>>

    @Query("""
        SELECT *
        FROM user
        WHERE id IN (:ids)
    """)
    fun getUserByIds(ids: List<Long>): List<UserEntity>

    @Query("""
        SELECT 
            u.id, 
            u.name, 
            u.avatar, 
            u.mail, 
            u.phone, 
            u.nick_name AS nickName, 
            u.rate, 
            u.date_of_birth AS dateOfBirth
            
        FROM user_follow AS f1
        INNER JOIN user_follow AS f2 ON f1.followed_id = f2.follower_id
        INNER JOIN user AS u ON u.id = f1.followed_id
        
        WHERE f1.follower_id = :userId 
            AND f2.followed_id = :userId
    """)
    fun getFriendUserById(userId: Long): Flow<List<UserData>>
}