package com.zhixue.lite.feature.report

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhixue.lite.core.domain.GetReportMainUseCase
import com.zhixue.lite.core.model.data.ReportMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class ReportMainUiState(
    val reportMain: ReportMain? = null
)

@HiltViewModel
class ReportMainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getReportMainUseCase: GetReportMainUseCase
) : ViewModel() {

    private val reportId: String = checkNotNull(savedStateHandle["reportId"])

    val uiState = getReportMainUseCase(reportId).map {
        ReportMainUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ReportMainUiState()
    )
}