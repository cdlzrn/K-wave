package com.cdlzrn.kwave.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdlzrn.kwave.Graph.authUserIdLong
import com.cdlzrn.kwave.Graph.userRepository
import com.cdlzrn.kwave.data.model.InterestItem
import com.cdlzrn.kwave.data.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class InterestViewModel: ViewModel() {

    val allArtists: StateFlow<List<UserData>> = userRepository.getAllArtists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    var selectableArtists = MutableStateFlow<List<InterestItem>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            allArtists.collectLatest { artistList ->
                if (artistList.isNotEmpty()) {
                    val currentSelectionMap = selectableArtists.value.associate { it.id to it.isSelected }

                    selectableArtists.value = artistList.map { artist ->
                        InterestItem(
                            id = artist.id,
                            name = artist.name,
                            imageResId = artist.avatar,
                            isSelected = currentSelectionMap[artist.id] ?: false
                        )
                    }
                }
            }
        }
    }

    fun onInterestClick(id: Long) {
        selectableArtists.update { currentList ->
            currentList.map { item ->
                if (item.id == id) {
                    item.copy(isSelected = !item.isSelected)
                } else {
                    item
                }
            }
        }
    }

    fun saveInterests(){
        viewModelScope.launch {
            val actualUserId = authUserIdLong

            if (actualUserId == -1L) {
                return@launch
            }

            val selectedIds = selectableArtists.value
                .filter { it.isSelected }
                .map { it.id }

            userRepository.addFollowedByIds(selectedIds, userId = actualUserId)
        }
    }

}