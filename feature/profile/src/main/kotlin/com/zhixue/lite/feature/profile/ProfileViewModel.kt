package com.zhixue.lite.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.data.repository.UpdateRepository
import com.zhixue.lite.core.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val message: String = "",
    val name: String = "",
    val schoolClass: String = ""
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
            schoolClass = "${userData.schoolName} ${userData.className}"
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ProfileUiState()
    )

    fun messageShown() {
        message.value = ""
    }

    fun checkUpdate(
        versionCode: Int,
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

    fun clearCache() {
        viewModelScope.launch {
            reportRepository.clearReportData()
            message.value = "缓存已清除"
        }
    }

    fun logout(
        onLogoutComplete: () -> Unit
    ) {
        viewModelScope.launch {
            userRepository.clearUser()
            reportRepository.clearReportData()
            onLogoutComplete()
        }
    }
}