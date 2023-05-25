package com.zhixue.lite.core.network.api

import com.zhixue.lite.core.model.network.NewVersionCodeResponse
import retrofit2.http.GET

interface GiteeApi {

    @GET("luiooiul/ZhiBan/raw/master/update.json")
    suspend fun getNewVersionCode(): NewVersionCodeResponse
}