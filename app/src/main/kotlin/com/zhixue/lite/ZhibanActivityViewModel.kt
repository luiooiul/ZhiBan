package com.zhixue.lite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhixue.lite.core.data.repository.LoginRepository
import com.zhixue.lite.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class LoginState {
    Loading, LoggedIn, NotLoggedIn
}

@HiltViewModel
class ZhibanActivityViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {

    var loginState: LoginState by mutableStateOf(LoginState.Loading)
        private set

    init {
        viewModelScope.launch {
            userRepository.userData.map { (username, password) ->
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    loginRepository.login(username, password)
                    LoginState.LoggedIn
                } else {
                    LoginState.NotLoggedIn
                }
            }.catch {
                emit(LoginState.LoggedIn)
            }.also {
                loginState = it.first()
            }
        }
    }
}