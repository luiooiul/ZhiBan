package com.zhixue.lite.feature.modify

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhixue.lite.core.data.repository.ModifyRepository
import com.zhixue.lite.core.data.repository.UserRepository
import com.zhixue.lite.core.domain.EncryptPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ModifyUiState(
    val message: String = "",
    val isModifying: Boolean = false
)

@HiltViewModel
class ModifyViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val modifyRepository: ModifyRepository,
    private val encryptPasswordUseCase: EncryptPasswordUseCase
) : ViewModel() {

    private val message = MutableStateFlow("")
    private val isModifying = MutableStateFlow(false)

    val uiState = combine(message, isModifying) { message, isModifying ->
        ModifyUiState(message, isModifying)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ModifyUiState()
    )

    var originPassword by mutableStateOf("")
        private set

    var newPassword by mutableStateOf("")
        private set

    fun updateOriginPassword(originPassword: String) {
        this.originPassword = originPassword
    }

    fun updateNewPassword(newPassword: String) {
        this.newPassword = newPassword
    }

    fun messageShown() {
        message.value = ""
    }

    fun doModify() {
        viewModelScope.launch {
            runCatching {
                isModifying.value = true
                modifyRepository.modifyPassword(originPassword, newPassword)
            }.onSuccess {
                userRepository.storeUser(
                    username = userRepository.loginName,
                    password = encryptPasswordUseCase(newPassword)
                )
                message.value = "修改成功"
            }.onFailure {
                message.value = it.message.orEmpty()
                isModifying.value = false
            }
        }
    }
}