package com.zhixue.lite.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhixue.lite.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ProfileUiState(
    val name: String = "",
    val schoolClass: String = ""
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {

    val uiState = userRepository.userData.map {
        ProfileUiState(
            name = it.name,
            schoolClass = "${it.schoolName} ${it.className}"
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProfileUiState()
    )
}