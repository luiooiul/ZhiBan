package com.zhixue.lite.core.data.repository

interface ModifyRepository {

    suspend fun modifyPassword(originPassword: String, newPassword: String)
}