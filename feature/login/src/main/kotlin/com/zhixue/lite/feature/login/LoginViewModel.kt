package com.zhixue.lite.feature.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class LoginUiState(
    val message: String = "",
    val isLogging: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _message = MutableStateFlow("")
    private val _isLogging = MutableStateFlow(false)

    val uiState = combine(_message, _isLogging) { message, isLogging ->
        LoginUiState(message, isLogging)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LoginUiState()
    )

    var username by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun updateUsername(username: String) {
        this.username = username
    }

    fun updatePassword(password: String) {
        this.password = password
    }

    fun messageShown() {
        _message.value = ""
    }

    fun doLogin(
        onLoginCompleted: () -> Unit
    ) {
    }
}