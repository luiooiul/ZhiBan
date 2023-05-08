package com.zhixue.lite.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zhixue.lite.core.data.paging.ReportPagingSource
import com.zhixue.lite.core.model.network.LevelTrendResponse
import com.zhixue.lite.core.model.network.PageAllExamListResponse
import com.zhixue.lite.core.model.network.ReportMainResponse
import com.zhixue.lite.core.model.network.SubjectDiagnosisResponse
import com.zhixue.lite.core.network.ApiNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val REPORT_LIST_PAGE_SIZE = 10

class ReportRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val networkDataSource: ApiNetworkDataSource
) : ReportRepository {

    override fun getReportList(reportType: String): Flow<PagingData<PageAllExamListResponse.ExamInfo>> {
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
        ).flow
    }

    override fun getReportMain(reportId: String): Flow<ReportMainResponse> {
        return flow {
            emit(networkDataSource.getReportMain(reportId, userRepository.token))
        }
    }

    override fun getSubjectDiagnosis(reportId: String): Flow<SubjectDiagnosisResponse> {
        return flow {
            emit(networkDataSource.getSubjectDiagnosis(reportId, userRepository.token))
        }.catch {
            emit(SubjectDiagnosisResponse(list = emptyList()))
        }
    }

    override fun getLevelTrend(reportId: String, paperId: String): Flow<LevelTrendResponse> {
        return flow {
            emit(networkDataSource.getLevelTrend(reportId, paperId, userRepository.token))
        }
    }
}