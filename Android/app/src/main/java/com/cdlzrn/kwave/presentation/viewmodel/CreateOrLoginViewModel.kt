package com.cdlzrn.kwave.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdlzrn.kwave.Graph.userRepository
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class CreateOrLoginViewModel: ViewModel() {

    fun fillDataBase() {
        viewModelScope.launch {
            userRepository.getUserById(0L)
        }
    }
}