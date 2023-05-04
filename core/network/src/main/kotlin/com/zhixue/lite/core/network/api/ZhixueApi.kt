package com.zhixue.lite.core.network.api

import com.zhixue.lite.core.model.network.CasResponse
import com.zhixue.lite.core.model.network.UserInfoResponse
import com.zhixue.lite.core.model.network.ZhixueResponse
import retrofit2.http.Field
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
}