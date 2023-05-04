package com.zhixue.lite.core.network.api

import com.zhixue.lite.core.model.network.ChangYanResponse
import com.zhixue.lite.core.model.network.SsoResponse
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ChangYanApi {

    @FormUrlEncoded
    @POST("sso/v1/api")
    suspend fun ssoLogin(
        @Field("username") username: String,
        @Field("password") password: String,
        @FieldMap fields: Map<String, String> = mapOf(
            "mac" to "0",
            "key" to "auto",
            "encode" to "true",
            "encodeType" to "R2/P",
            "client" to "android",
            "appId" to "zhixue_parent",
            "method" to "sso.login.account",
            "extInfo" to "{\"deviceId\":\"0\"}"
        )
    ): ChangYanResponse<SsoResponse>
}