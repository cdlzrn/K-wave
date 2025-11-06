package com.cdlzrn.kwave.presentation.viewmodel

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdlzrn.kwave.Graph.authRepository
import com.cdlzrn.kwave.Graph.saveUserId
import com.cdlzrn.kwave.Graph.userRepository
import com.cdlzrn.kwave.data.model.UserRegistrationData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class AuthViewModel: ViewModel() {

    val login = TextFieldState("")
    val password = TextFieldState("")
    val name = TextFieldState("")
    val mail = TextFieldState("")
    val phone = TextFieldState("")
    val registerPassword = TextFieldState("")
    var date by mutableStateOf("Дата рождения*")
        private set
    var oneTimeEvent by mutableStateOf(Channel<AuthEvent>(Channel.BUFFERED))
        private set
    var errorVisible by mutableStateOf(false )
        private set
    var isAgreeUserAgreement by mutableStateOf(false)
        private set


    fun onGoogleClick(){
        viewModelScope.launch {
            val authUri = authRepository.getGoogleAuthUri()
            oneTimeEvent.send(AuthEvent.LaunchAuthIntent(authUri))
        }
    }
    fun onAppleClick(){
        viewModelScope.launch {
            val authUri = authRepository.getAppleAuthUri()
            oneTimeEvent.send(AuthEvent.LaunchAuthIntent(authUri))
        }
    }
    suspend fun getUserIdByLoginAndPassword(login: String, password: String): Long{
        return authRepository.getUserIdByLoginAndPassword(login, password)
    }
    suspend fun getUserIdByLogin(login: String): Boolean{
        return authRepository.isExistUserByLogin(login)
    }
    fun changeErrorVisible(value: Boolean) { errorVisible = value }
    fun onDateSelected(millis: Long) {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        date = formatter.format(Date(millis))
    }
    fun changeAgreeUserAgreement(value: Boolean) { isAgreeUserAgreement = value }
    fun getUserAgreementUrl(): String { return authRepository.getUserAgreementUrl() }
    fun onLoginClick(){
        viewModelScope.launch {
            val userId = getUserIdByLoginAndPassword(
                login = login.text.toString(),
                password = password.text.toString()
            )

            if (userId != -1L){
                saveUserId(userId)
                oneTimeEvent.send(AuthEvent.NavigateToMain)
            }
            else changeErrorVisible(true)
        }
    }
    suspend fun createUser(): Long {
        val newUser = UserRegistrationData(
            name = name.text.toString().trim(),
            dateOfBirth = date,
            mail = mail.text.toString().trim(),
            phone = "+7${phone.text}",
            password = registerPassword.text.toString().trim()
        )

        return userRepository.createUser(newUser)
    }
    suspend fun isExistUserMail(): Boolean{
         return getUserIdByLogin(mail.text.toString())
    }
    suspend fun isExistUserPhone(): Boolean{
         return getUserIdByLogin("+7${phone.text}")
    }
}

sealed class AuthEvent {
    data class LaunchAuthIntent(val uri: Uri) : AuthEvent()
    object NavigateToMain : AuthEvent()
    object NavigateToInterest : AuthEvent()
}
