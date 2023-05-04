package com.zhixue.lite.core.data.repository

interface LoginRepository {

    suspend fun login(username: String, password: String)
}