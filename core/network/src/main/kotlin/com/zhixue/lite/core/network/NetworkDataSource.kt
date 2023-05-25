package com.zhixue.lite.core.network

import com.zhixue.lite.core.model.network.CasResponse
import com.zhixue.lite.core.model.network.CheckSheetResponse
import com.zhixue.lite.core.model.network.LevelTrendResponse
import com.zhixue.lite.core.model.network.NewVersionCodeResponse
import com.zhixue.lite.core.model.network.PageAllExamListResponse
import com.zhixue.lite.core.model.network.PaperAnalysisResponse
import com.zhixue.lite.core.model.network.ReportMainResponse
import com.zhixue.lite.core.model.network.SsoResponse
import com.zhixue.lite.core.model.network.SubjectDiagnosisResponse
import com.zhixue.lite.core.model.network.UserInfoResponse

interface NetworkDataSource {

    suspend fun getNewVersionCode(): NewVersionCodeResponse

    suspend fun ssoLogin(username: String, password: String): SsoResponse

    suspend fun casLogin(at: String, userId: String): CasResponse

    suspend fun getUserInfo(token: String): UserInfoResponse

    suspend fun modifyPassword(
        loginName: String, originPassword: String, newPassword: String, token: String
    ): String

    suspend fun getPageAllExamList(
        reportType: String, pageIndex: Int, token: String
    ): PageAllExamListResponse

    suspend fun getReportMain(examId: String, token: String): ReportMainResponse

    suspend fun getSubjectDiagnosis(
        examId: String, token: String
    ): SubjectDiagnosisResponse

    suspend fun getLevelTrend(
        examId: String, paperId: String, token: String
    ): LevelTrendResponse

    suspend fun getCheckSheet(
        examId: String, paperId: String, token: String
    ): CheckSheetResponse

    suspend fun getPaperAnalysis(paperId: String, token: String): PaperAnalysisResponse
}