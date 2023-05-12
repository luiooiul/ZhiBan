package com.zhixue.lite.core.network

import com.zhixue.lite.core.model.network.CasResponse
import com.zhixue.lite.core.model.network.CheckSheetResponse
import com.zhixue.lite.core.model.network.LevelTrendResponse
import com.zhixue.lite.core.model.network.PageAllExamListResponse
import com.zhixue.lite.core.model.network.ReportMainResponse
import com.zhixue.lite.core.model.network.SsoResponse
import com.zhixue.lite.core.model.network.SubjectDiagnosisResponse
import com.zhixue.lite.core.model.network.UserInfoResponse
import com.zhixue.lite.core.network.api.ChangYanApi
import com.zhixue.lite.core.network.api.ZhixueApi
import javax.inject.Inject

class ApiNetworkDataSource @Inject constructor(
    private val zhixueApi: ZhixueApi,
    private val changYanApi: ChangYanApi
) : NetworkDataSource {

    override suspend fun ssoLogin(username: String, password: String): SsoResponse {
        return changYanApi.ssoLogin(username, password).data
    }

    override suspend fun casLogin(at: String, userId: String): CasResponse {
        return zhixueApi.casLogin(at, userId).result
    }

    override suspend fun getUserInfo(token: String): UserInfoResponse {
        return zhixueApi.getUserInfo(token).result
    }

    override suspend fun getPageAllExamList(
        reportType: String, pageIndex: Int, token: String
    ): PageAllExamListResponse {
        return zhixueApi.getPageAllExamList(reportType, pageIndex, token).result
    }

    override suspend fun getReportMain(examId: String, token: String): ReportMainResponse {
        return zhixueApi.getReportMain(examId, token).result
    }

    override suspend fun getSubjectDiagnosis(
        examId: String, token: String
    ): SubjectDiagnosisResponse {
        return zhixueApi.getSubjectDiagnosis(examId, token).result
    }

    override suspend fun getLevelTrend(
        examId: String, paperId: String, token: String
    ): LevelTrendResponse {
        return zhixueApi.getLevelTrend(examId, paperId, token).result
    }

    override suspend fun getCheckSheet(
        examId: String, paperId: String, token: String
    ): CheckSheetResponse {
        return zhixueApi.getCheckSheet(examId, paperId, token).result
    }
}