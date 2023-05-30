package com.zhixue.lite.core.data.repository

interface UpdateRepository {

    suspend fun getNewVersionCode(): Long
}