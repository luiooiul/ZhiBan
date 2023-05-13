package com.zhixue.lite.feature.report

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhixue.lite.core.domain.GetReportDetailUseCase
import com.zhixue.lite.core.model.data.ReportDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ReportDetailUiState {
    object Error : ReportDetailUiState
    object Loading : ReportDetailUiState
    data class Success(val reportDetail: ReportDetail) : ReportDetailUiState
}

@HiltViewModel
class ReportDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getReportDetailUseCase: GetReportDetailUseCase
) : ViewModel() {

    var uiState: ReportDetailUiState by mutableStateOf(ReportDetailUiState.Loading)
        private set

    private val examId: String = checkNotNull(savedStateHandle["examId"])
    private val paperId: String = checkNotNull(savedStateHandle["paperId"])

    init {
        viewModelScope.launch {
            uiState = getReportDetailUseCase(examId, paperId)
                .map<ReportDetail, ReportDetailUiState> {
                    ReportDetailUiState.Success(reportDetail = it)
                }.catch {
                    emit(ReportDetailUiState.Error)
                }.single()
        }
    }
}