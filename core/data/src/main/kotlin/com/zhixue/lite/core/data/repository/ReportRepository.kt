package com.zhixue.lite.core.data.repository

import androidx.paging.PagingData
import com.zhixue.lite.core.model.data.ReportDetail
import com.zhixue.lite.core.model.data.ReportMain
import com.zhixue.lite.core.model.database.ReportInfoEntity
import com.zhixue.lite.core.model.network.CheckSheetResponse
import com.zhixue.lite.core.model.network.LevelTrendResponse
import com.zhixue.lite.core.model.network.PaperAnalysisResponse
import com.zhixue.lite.core.model.network.ReportMainResponse
import com.zhixue.lite.core.model.network.SubjectDiagnosisResponse
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun getReportList(reportType: String): Flow<PagingData<ReportInfoEntity>>

    fun getReportMain(examId: String): Flow<ReportMainResponse>

    fun getSubjectDiagnosis(examId: String): Flow<SubjectDiagnosisResponse>

    fun getLevelTrend(examId: String, paperId: String): Flow<LevelTrendResponse>

    fun getCheckSheet(examId: String, paperId: String): Flow<CheckSheetResponse>

    fun getPaperAnalysis(paperId: String): Flow<PaperAnalysisResponse>

    suspend fun saveReportMain(examId: String, reportMain: ReportMain)

    suspend fun saveReportDetail(paperId: String, reportDetail: ReportDetail)

    suspend fun getLocalReportMain(examId: String): ReportMain

    suspend fun getLocalReportDetail(paperId: String): ReportDetail

    suspend fun clearReportList()

    suspend fun clearReportData()
}