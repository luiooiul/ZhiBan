package com.zhixue.lite.core.data.repository

import androidx.paging.PagingData
import com.zhixue.lite.core.model.data.ReportInfo
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun getReportList(reportType: String): Flow<PagingData<ReportInfo>>
}