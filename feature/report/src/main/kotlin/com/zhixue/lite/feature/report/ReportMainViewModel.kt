package com.zhixue.lite.feature.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhixue.lite.core.common.result.Result
import com.zhixue.lite.core.common.result.asResult
import com.zhixue.lite.core.domain.GetReportMainUseCase
import com.zhixue.lite.core.model.data.ReportMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

sealed interface ReportMainUiState {
    object Loading : ReportMainUiState
    data class Success(val reportMain: ReportMain) : ReportMainUiState
    data class Error(val message: String) : ReportMainUiState
}

@HiltViewModel
class ReportMainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getReportMainUseCase: GetReportMainUseCase
) : ViewModel() {

    private val reportId: String = checkNotNull(savedStateHandle["reportId"])

    val uiState = getReportMainUseCase(reportId).asResult().map {
        when (it) {
            is Result.Success -> ReportMainUiState.Success(it.data)
            is Result.Error -> ReportMainUiState.Error(it.exception.message.orEmpty())
            is Result.Loading -> ReportMainUiState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ReportMainUiState.Loading
    )
}