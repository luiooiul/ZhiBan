package com.zhixue.lite.feature.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhixue.lite.core.data.repository.LoginRepository
import com.zhixue.lite.core.data.repository.UserRepository
import com.zhixue.lite.core.domain.EncryptPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val message: String = "",
    val isLogging: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository,
    private val encryptPasswordUseCase: EncryptPasswordUseCase
) : ViewModel() {

    private val message = MutableStateFlow("")
    private val isLogging = MutableStateFlow(false)

    val uiState = combine(message, isLogging) { message, isLogging ->
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
        message.value = ""
    }

    fun doLogin(
        onLoginCompleted: () -> Unit
    ) {
        viewModelScope.launch {
            runCatching {
                checkInputAvailable()
                isLogging.value = true
                val encryptedPassword = encryptPasswordUseCase.invoke(password.reversed())
                loginRepository.login(username, encryptedPassword)
                userRepository.storeUser(username, encryptedPassword)
            }.onSuccess {
                onLoginCompleted()
            }.onFailure {
                message.value = it.message.orEmpty()
                isLogging.value = false
            }
        }
    }

    private fun checkInputAvailable() {
        check(username.isNotEmpty() && password.isNotEmpty()) { "账号密码不能为空" }
    }
}