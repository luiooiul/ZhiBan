package com.zhixue.lite.core.network.api

import com.zhixue.lite.core.model.network.CasResponse
import com.zhixue.lite.core.model.network.PageAllExamListResponse
import com.zhixue.lite.core.model.network.ReportMainResponse
import com.zhixue.lite.core.model.network.SubjectDiagnosisResponse
import com.zhixue.lite.core.model.network.UserInfoResponse
import com.zhixue.lite.core.model.network.ZhixueResponse
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ZhixueApi {

    @FormUrlEncoded
    @POST("container/app/login/casLogin")
    suspend fun casLogin(
        @Field("at") at: String,
        @Field("userId") userId: String
    ): ZhixueResponse<CasResponse>

    @FormUrlEncoded
    @POST("zxbReport/base/common/getUserInfo")
    suspend fun getUserInfo(
        @Field("token") token: String
    ): ZhixueResponse<UserInfoResponse>

    @FormUrlEncoded
    @POST("zxbReport/report/getPageAllExamList")
    suspend fun getPageAllExamList(
        @Field("reportType") reportType: String,
        @Field("pageIndex") pageIndex: Int,
        @Field("token") token: String,
        @FieldMap fields: Map<String, String> = mapOf(
            "pageSize" to "10",
            "actualPosition" to "0"
        )
    ): ZhixueResponse<PageAllExamListResponse>

    @FormUrlEncoded
    @POST("zxbReport/report/exam/getReportMain")
    suspend fun getReportMain(
        @Field("examId") reportId: String,
        @Field("token") token: String
    ): ZhixueResponse<ReportMainResponse>

    @FormUrlEncoded
    @POST("zxbReport/report/exam/getSubjectDiagnosis")
    suspend fun getSubjectDiagnosis(
        @Field("examId") reportId: String,
        @Field("token") token: String
    ): ZhixueResponse<SubjectDiagnosisResponse>
}