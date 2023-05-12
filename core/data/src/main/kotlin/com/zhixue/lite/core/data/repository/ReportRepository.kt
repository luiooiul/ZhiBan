package com.zhixue.lite.core.data.repository

import androidx.paging.PagingData
import com.zhixue.lite.core.model.network.LevelTrendResponse
import com.zhixue.lite.core.model.network.PageAllExamListResponse
import com.zhixue.lite.core.model.network.ReportMainResponse
import com.zhixue.lite.core.model.network.SubjectDiagnosisResponse
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun getReportList(reportType: String): Flow<PagingData<PageAllExamListResponse.ExamInfo>>

    fun getReportMain(examId: String): Flow<ReportMainResponse>

    fun getSubjectDiagnosis(examId: String): Flow<SubjectDiagnosisResponse>

    fun getLevelTrend(examId: String, paperId: String): Flow<LevelTrendResponse>
}