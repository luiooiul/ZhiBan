package com.zhixue.lite.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.zhixue.lite.core.data.paging.ReportRemoteMediator
import com.zhixue.lite.core.database.dao.ReportDetailDao
import com.zhixue.lite.core.database.dao.ReportInfoDao
import com.zhixue.lite.core.database.dao.ReportMainDao
import com.zhixue.lite.core.model.data.ReportDetail
import com.zhixue.lite.core.model.data.ReportMain
import com.zhixue.lite.core.model.data.asEntity
import com.zhixue.lite.core.model.database.ReportInfoEntity
import com.zhixue.lite.core.model.database.asExternalModel
import com.zhixue.lite.core.model.network.CheckSheetResponse
import com.zhixue.lite.core.model.network.LevelTrendResponse
import com.zhixue.lite.core.model.network.PaperAnalysisResponse
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
    private val reportInfoDao: ReportInfoDao,
    private val reportMainDao: ReportMainDao,
    private val reportDetailDao: ReportDetailDao,
    private val networkDataSource: ApiNetworkDataSource
) : ReportRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getReportList(reportType: String): Flow<PagingData<ReportInfoEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = REPORT_LIST_PAGE_SIZE
            ),
            remoteMediator = ReportRemoteMediator(
                reportInfoDao = reportInfoDao,
                networkDataSource = networkDataSource,
                reportType = reportType,
                token = userRepository.token
            ),
            pagingSourceFactory = {
                reportInfoDao.pagingSource(reportType)
            }
        ).flow
    }

    override fun getReportMain(examId: String): Flow<ReportMainResponse> {
        return flow {
            emit(networkDataSource.getReportMain(examId, userRepository.token))
        }
    }

    override fun getSubjectDiagnosis(examId: String): Flow<SubjectDiagnosisResponse> {
        return flow {
            emit(networkDataSource.getSubjectDiagnosis(examId, userRepository.token))
        }.catch {
            emit(SubjectDiagnosisResponse(list = emptyList()))
        }
    }

    override fun getLevelTrend(examId: String, paperId: String): Flow<LevelTrendResponse> {
        return flow {
            emit(networkDataSource.getLevelTrend(examId, paperId, userRepository.token))
        }
    }

    override fun getCheckSheet(examId: String, paperId: String): Flow<CheckSheetResponse> {
        return flow {
            emit(networkDataSource.getCheckSheet(examId, paperId, userRepository.token))
        }
    }

    override fun getPaperAnalysis(paperId: String): Flow<PaperAnalysisResponse> {
        return flow {
            emit(networkDataSource.getPaperAnalysis(paperId, userRepository.token))
        }
    }

    override suspend fun saveReportMain(examId: String, reportMain: ReportMain) {
        reportMainDao.insert(reportMain.asEntity(examId))
    }

    override suspend fun saveReportDetail(paperId: String, reportDetail: ReportDetail) {
        reportDetailDao.insert(reportDetail.asEntity(paperId))
    }

    override suspend fun getLocalReportMain(examId: String): ReportMain {
        return reportMainDao.get(examId)!!.asExternalModel()
    }

    override suspend fun getLocalReportDetail(paperId: String): ReportDetail {
        return reportDetailDao.get(paperId)!!.asExternalModel()
    }
}