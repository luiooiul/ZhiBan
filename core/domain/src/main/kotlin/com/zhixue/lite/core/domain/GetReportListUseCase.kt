package com.zhixue.lite.core.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.data.ReportInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetReportListUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
    private val formatDateUseCase: FormatDateUseCase
) {
    operator fun invoke(reportType: String): Flow<PagingData<ReportInfo>> {
        return reportRepository.getReportList(reportType).map { paging ->
            paging.map {
                ReportInfo(
                    id = it.id,
                    name = it.name,
                    date = formatDateUseCase(it.date),
                    isSinglePublish = it.isSinglePublish
                )
            }
        }.flowOn(Dispatchers.Default)
    }
}