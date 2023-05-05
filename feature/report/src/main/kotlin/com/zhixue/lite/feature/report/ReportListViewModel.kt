package com.zhixue.lite.feature.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.data.ReportInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ReportListViewModel @Inject constructor(
    reportRepository: ReportRepository
) : ViewModel() {

    val examList: Flow<PagingData<ReportInfo>> =
        reportRepository.getReportList("exam").cachedIn(viewModelScope)
    val homeworkList: Flow<PagingData<ReportInfo>> =
        reportRepository.getReportList("homework").cachedIn(viewModelScope)
}