package com.zhixue.lite.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.data.repository.UpdateRepository
import com.zhixue.lite.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

data class ProfileUiState(
    val message: String = "",
    val name: String = "",
    val schoolClass: String = "",
    val credentials: Map<String, String> = emptyMap()
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val reportRepository: ReportRepository,
    private val updateRepository: UpdateRepository
) : ViewModel() {

    private val message = MutableStateFlow("")

    val uiState = combine(message, userRepository.userData) { message, userData ->
        ProfileUiState(
            message = message,
            name = userData.name,
            schoolClass = "${userData.schoolName} ${userData.className}",
            credentials = userData.credentials
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProfileUiState()
    )

    fun messageShown() {
        message.value = ""
    }

    fun switchAccount(
        username: String,
        password: String,
        onSwitchAccountCompleted: () -> Unit
    ) {
        viewModelScope.launch {
            userRepository.clearUser()
            userRepository.storeUser(username, password)
            reportRepository.clearReportList()
            reportRepository.clearReportData()
            onSwitchAccountCompleted()
        }
    }

    fun checkUpdate(
        versionCode: Long,
        onNewVersionAvailable: () -> Unit
    ) {
        viewModelScope.launch {
            runCatching {
                message.value = "检测更新中"
                updateRepository.getNewVersionCode()
            }.onSuccess { newVersionCode ->
                if (versionCode < newVersionCode) {
                    onNewVersionAvailable()
                } else {
                    message.value = "已是最新版本"
                }
            }.onFailure {
                message.value = "获取更新失败"
            }
        }
    }

    fun clearCache(cacheDir: File) {
        viewModelScope.launch(Dispatchers.IO) {
            cacheDir.deleteRecursively()
            message.value = "缓存已清除"
        }
    }

    fun logout(
        onLogoutComplete: () -> Unit
    ) {
        viewModelScope.launch {
            userRepository.clearUser()
            reportRepository.clearReportList()
            reportRepository.clearReportData()
            onLogoutComplete()
        }
    }
}