package com.zhixue.lite.core.data.repository

import com.zhixue.lite.core.network.ApiNetworkDataSource
import javax.inject.Inject

class ModifyRepositoryImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val networkDataSource: ApiNetworkDataSource
) : ModifyRepository {

    override suspend fun modifyPassword(originPassword: String, newPassword: String) {
        networkDataSource.modifyPassword(
            loginName = userRepository.loginName,
            originPassword = originPassword,
            newPassword = newPassword,
            token = userRepository.token
        )
    }
}