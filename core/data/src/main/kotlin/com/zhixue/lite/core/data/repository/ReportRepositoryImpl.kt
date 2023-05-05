package com.zhixue.lite.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.zhixue.lite.core.data.paging.ReportPagingSource
import com.zhixue.lite.core.domain.FormatDateUseCase
import com.zhixue.lite.core.model.data.ReportInfo
import com.zhixue.lite.core.network.ApiNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val REPORT_LIST_PAGE_SIZE = 10

class ReportRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val networkDataSource: ApiNetworkDataSource,
    private val formatDateUseCase: FormatDateUseCase
) : ReportRepository {

    override fun getReportList(reportType: String): Flow<PagingData<ReportInfo>> {
        return Pager(
            config = PagingConfig(
                pageSize = REPORT_LIST_PAGE_SIZE
            ),
            pagingSourceFactory = {
                ReportPagingSource(
                    networkDataSource = networkDataSource,
                    reportType = reportType,
                    token = userRepository.token
                )
            }
        ).flow.map { paging ->
            paging.map { examInfo ->
                ReportInfo(
                    id = examInfo.examId,
                    name = examInfo.examName,
                    date = formatDateUseCase.invoke(examInfo.examCreateDateTime)
                )
            }
        }
    }
}