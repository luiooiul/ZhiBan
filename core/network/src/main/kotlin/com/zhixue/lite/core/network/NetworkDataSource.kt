package com.zhixue.lite.core.network

import com.zhixue.lite.core.model.network.CasResponse
import com.zhixue.lite.core.model.network.SsoResponse
import com.zhixue.lite.core.model.network.UserInfoResponse

interface NetworkDataSource {

    suspend fun ssoLogin(username: String, password: String): SsoResponse

    suspend fun casLogin(at: String, userId: String): CasResponse

    suspend fun getUserInfo(token: String): UserInfoResponse
}