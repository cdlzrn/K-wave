package com.cdlzrn.kwave.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.cdlzrn.kwave.Graph.applicationScope
import com.cdlzrn.kwave.data.dao.UserDao
import com.cdlzrn.kwave.data.dao.UserFollowDao
import com.cdlzrn.kwave.data.entity.UserFollowEntity
import com.cdlzrn.kwave.data.mapper.toUserData
import com.cdlzrn.kwave.data.mapper.toUserEntity
import com.cdlzrn.kwave.data.model.UserData
import com.cdlzrn.kwave.data.model.UserForProductData
import com.cdlzrn.kwave.data.model.UserRegistrationData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.collections.map


@RequiresApi(Build.VERSION_CODES.O)
class UserRepository(
    private val userDao: UserDao,
    private val followDao: UserFollowDao,
) {

    suspend fun createUser(newUser: UserRegistrationData): Long{
        val user = toUserEntity(newUser)
        return userDao.create(user)
    }

    suspend fun addFollowedByIds(followedIds: List<Long>, userId: Long){
        val user = userDao.getUserById(userId)

        if (user == null) return

        followDao.insertAllFollows(
            follow = followedIds.map { entity ->
                UserFollowEntity(
                    followerId = user.id,
                    followedId = entity
                )
            }
        )
    }

    suspend fun getUserById(userId: Long): UserData?{
        val user = userDao.getUserById(userId) ?: return null
        return toUserData(user)
    }

    fun getUserDataById(userId: Long): Flow<UserData>{
        return userDao.getUserDataById(userId)
    }

    fun getFlowUserByIdForProduct(userId: Long): Flow<UserForProductData>{
        return userDao.getFlowUserByIdForProduct(userId)
    }

    fun getAllArtists(): StateFlow<List<UserData>> {
        return userDao.getArtists()
            .map { entities ->
                entities.map { entity -> toUserData(entity) }
            }
            .stateIn(
                scope = applicationScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun getAllArtistsByUser(userId: Long): Flow<List<UserData>> {
        return followDao.getFollowed(userId).map { flowList ->
            flowList.map{ entity -> toUserData(entity) }
        }
    }

    fun getFriendUserById(userId: Long): Flow<List<UserData>>{
        return userDao.getFriendUserById(userId)
    }

}