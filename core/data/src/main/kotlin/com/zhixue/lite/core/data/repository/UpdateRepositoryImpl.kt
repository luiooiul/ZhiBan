package com.zhixue.lite.core.data.repository

import com.zhixue.lite.core.network.ApiNetworkDataSource
import javax.inject.Inject

class UpdateRepositoryImpl @Inject constructor(
    private val networkDataSource: ApiNetworkDataSource
) : UpdateRepository {

    override suspend fun getNewVersionCode(): Long {
        return networkDataSource.getNewVersionCode().newVersionCode
    }
}