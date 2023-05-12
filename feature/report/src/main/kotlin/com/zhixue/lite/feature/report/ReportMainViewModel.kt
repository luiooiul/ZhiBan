package com.zhixue.lite.feature.report

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhixue.lite.core.domain.GetReportMainUseCase
import com.zhixue.lite.core.model.data.ReportMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ReportMainUiState {
    object Error : ReportMainUiState
    object Loading : ReportMainUiState
    data class Success(val reportMain: ReportMain) : ReportMainUiState
}

@HiltViewModel
class ReportMainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getReportMainUseCase: GetReportMainUseCase
) : ViewModel() {

    private val examId: String = checkNotNull(savedStateHandle["examId"])

    var uiState: ReportMainUiState by mutableStateOf(ReportMainUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            uiState = getReportMainUseCase(examId).map<ReportMain, ReportMainUiState> {
                ReportMainUiState.Success(reportMain = it)
            }.catch {
                emit(ReportMainUiState.Error)
            }.single()
        }
    }
}