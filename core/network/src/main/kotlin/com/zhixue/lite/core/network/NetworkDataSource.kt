package com.zhixue.lite.core.network

import com.zhixue.lite.core.model.network.CasResponse
import com.zhixue.lite.core.model.network.PageAllExamListResponse
import com.zhixue.lite.core.model.network.ReportMainResponse
import com.zhixue.lite.core.model.network.SsoResponse
import com.zhixue.lite.core.model.network.SubjectDiagnosisResponse
import com.zhixue.lite.core.model.network.UserInfoResponse

interface NetworkDataSource {

    suspend fun ssoLogin(username: String, password: String): SsoResponse

    suspend fun casLogin(at: String, userId: String): CasResponse

    suspend fun getUserInfo(token: String): UserInfoResponse

    suspend fun getPageAllExamList(
        reportType: String, pageIndex: Int, token: String
    ): PageAllExamListResponse

    suspend fun getReportMain(reportId: String, token: String): ReportMainResponse

    suspend fun getSubjectDiagnosis(
        reportId: String, token: String
    ): SubjectDiagnosisResponse
}