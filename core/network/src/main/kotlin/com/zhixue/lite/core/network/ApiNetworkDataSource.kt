package com.zhixue.lite.core.network

import com.zhixue.lite.core.model.network.CasResponse
import com.zhixue.lite.core.model.network.PageAllExamListResponse
import com.zhixue.lite.core.model.network.SsoResponse
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
}