package com.zhixue.lite.core.data.repository

import com.zhixue.lite.core.network.ApiNetworkDataSource
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val networkDataSource: ApiNetworkDataSource
) : LoginRepository {

    override suspend fun login(username: String, password: String) {
        val (at, userId) = networkDataSource.ssoLogin(username, password)
        val (token, userInfo, clazzInfo, childrens) = networkDataSource.casLogin(at, userId)
        val (curId) = networkDataSource.getUserInfo(token)
        val (curUserInfo, curClassInfo) = childrens
            ?.find { it.userInfo.id == curId }
            ?.let { it.userInfo to it.clazzInfo }
            ?: (userInfo to clazzInfo!!)

        userRepository.setUserInfo(
            id = curId,
            token = token,
            name = curUserInfo.name,
            className = curClassInfo.name,
            schoolName = curUserInfo.school!!.schoolName
        )
    }
}