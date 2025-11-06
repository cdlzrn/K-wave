package com.cdlzrn.kwave.data.repository

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.cdlzrn.kwave.data.dao.UserDao
import org.mindrot.jbcrypt.BCrypt

@RequiresApi(Build.VERSION_CODES.O)
class AuthRepository(
    private val userDao: UserDao
) {

    fun getGoogleAuthUri(): Uri = "https://www.youtube.com/watch?v=dQw4w9WgXcQ".toUri()
    fun getAppleAuthUri(): Uri = "https://www.youtube.com/watch?v=dQw4w9WgXcQ".toUri()
    fun getUserAgreementUrl(): String = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"

    suspend fun getUserIdByLoginAndPassword(login: String, password: String): Long{
        val user =
            userDao.getUserByEmail(login)
            ?: userDao.getUserByPhone(login)
            ?: userDao.getUserByNickName(login)
        if (user == null || !BCrypt.checkpw(password, user.passwordHash)) return -1L
        return user.id
    }

    suspend fun isExistUserByLogin(login: String): Boolean{
        return userDao.getUserByLogin(login) != null
    }
}