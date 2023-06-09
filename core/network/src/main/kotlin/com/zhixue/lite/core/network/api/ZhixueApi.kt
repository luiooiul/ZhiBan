package com.zhixue.lite.core.network.api

import com.zhixue.lite.core.model.network.CasResponse
import com.zhixue.lite.core.model.network.CheckSheetResponse
import com.zhixue.lite.core.model.network.LevelTrendResponse
import com.zhixue.lite.core.model.network.PageAllExamListResponse
import com.zhixue.lite.core.model.network.PaperAnalysisResponse
import com.zhixue.lite.core.model.network.ReportMainResponse
import com.zhixue.lite.core.model.network.SubjectDiagnosisResponse
import com.zhixue.lite.core.model.network.UserInfoResponse
import com.zhixue.lite.core.model.network.ZhixueResponse
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
    @POST("container/app/modifyOriginPWD")
    suspend fun modifyPassword(
        @Field("loginName") loginName: String,
        @Field("originPWD") originPassword: String,
        @Field("newPWD") newPassword: String,
        @Field("token") token: String
    ): ZhixueResponse<Unit>

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
        @Field("examId") examId: String,
        @Field("token") token: String
    ): ZhixueResponse<ReportMainResponse>

    @FormUrlEncoded
    @POST("zxbReport/report/exam/getSubjectDiagnosis")
    suspend fun getSubjectDiagnosis(
        @Field("examId") examId: String,
        @Field("token") token: String
    ): ZhixueResponse<SubjectDiagnosisResponse>

    @FormUrlEncoded
    @POST("zxbReport/report/paper/getLevelTrend")
    suspend fun getLevelTrend(
        @Field("examId") examId: String,
        @Field("paperId") paperId: String,
        @Field("token") token: String,
        @FieldMap fields: Map<String, String> = mapOf(
            "pageSize" to "2",
            "pageIndex" to "1"
        )
    ): ZhixueResponse<LevelTrendResponse>

    @FormUrlEncoded
    @POST("zxbReport/report/paper/getCheckSheet")
    suspend fun getCheckSheet(
        @Field("examId") examId: String,
        @Field("paperId") paperId: String,
        @Field("token") token: String
    ): ZhixueResponse<CheckSheetResponse>

    @GET("zxbReport/report/getPaperAnalysis")
    suspend fun getPaperAnalysis(
        @Query("paperId") paperId: String,
        @Query("token") token: String
    ): ZhixueResponse<PaperAnalysisResponse>
}