package com.cdlzrn.kwave

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.cdlzrn.kwave.data.dataStore.SessionManager
import com.cdlzrn.kwave.data.database.AppDataBase
import com.cdlzrn.kwave.data.repository.AuthRepository
import com.cdlzrn.kwave.data.repository.CartRepository
import com.cdlzrn.kwave.data.repository.PostRepository
import com.cdlzrn.kwave.data.repository.ProductRepository
import com.cdlzrn.kwave.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
object Graph {
    val applicationScope = CoroutineScope(SupervisorJob())

    lateinit var db: AppDataBase
        private set
    lateinit var session: SessionManager
        private set


    val authRepository by lazy {
        AuthRepository(
            userDao = db.userDao()
        )
    }
    val userRepository by lazy {
        UserRepository(
            userDao = db.userDao(),
            followDao = db.followDao(),
        )
    }
    val productRepository by lazy {
        ProductRepository(
            productDao = db.productDao()
        )
    }
    val cartRepository by lazy {
        CartRepository(
            cartDao = db.cartDao()
        )
    }
    val postRepository by lazy {
        PostRepository(
            postDao = db.postDao()
        )
    }


    var authUserIdFlow = MutableStateFlow(-1L)
        private set

    val authUserIdLong: Long
        get() = authUserIdFlow.value

    suspend fun saveUserId(userId: Long) {
        if (!::session.isInitialized) {
            throw IllegalStateException("Graph must be initialized via provide() before calling saveUserId.")
        }
        session.saveUserId(userId)
        authUserIdFlow.value = userId
    }

    suspend fun deleteUserId() {
        if (!::session.isInitialized) {
            throw IllegalStateException("Graph must be initialized via provide() before calling saveUserId.")
        }
        session.deleteUserId()
        authUserIdFlow.value = -1L
    }

    fun provide(context: Context){
        db = AppDataBase.getDataBase(context, applicationScope)
        session = SessionManager().init(context.applicationContext)

        applicationScope.launch {
            val savedId = session.userId.firstOrNull()?.toLongOrNull() ?: -1L
            authUserIdFlow.value = savedId
        }
    }
}