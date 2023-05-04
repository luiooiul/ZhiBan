package com.zhixue.lite.core.data.repository

import com.zhixue.lite.core.model.data.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val id: String
    val token: String

    val userData: Flow<UserData>

    suspend fun storeUser(username: String, password: String)

    suspend fun setUserInfo(
        id: String,
        token: String,
        name: String,
        className: String,
        schoolName: String
    )
}