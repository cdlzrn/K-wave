package com.cdlzrn.kwave.data.repository

import com.cdlzrn.kwave.data.dao.PostDao
import com.cdlzrn.kwave.data.model.PostData
import kotlinx.coroutines.flow.Flow

class PostRepository (
    private val postDao: PostDao
){

    fun getFollowedAndUserPosts(userId: Long): Flow<List<PostData>>{
        return postDao.getFollowedAndUserPostWithAuthor(userId)
    }

    fun getPostsByUser(userId: Long): Flow<List<PostData>>{
        return postDao.getPostsByUser(userId)
    }

}