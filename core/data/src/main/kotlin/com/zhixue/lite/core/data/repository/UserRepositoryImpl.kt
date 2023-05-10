package com.zhixue.lite.core.data.repository

import com.zhixue.lite.core.datastore.PreferencesDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val preferencesDataSource: PreferencesDataSource
) : UserRepository {

    override var id = ""
        private set
    override var token = ""
        private set

    override val userData = preferencesDataSource.userData

    override suspend fun storeUser(username: String, password: String) {
        preferencesDataSource.setUser(username, password)
    }

    override suspend fun clearUser() {
        preferencesDataSource.clearUser()
    }

    override suspend fun setUserInfo(
        id: String,
        token: String,
        name: String,
        className: String,
        schoolName: String
    ) {
        this.id = id
        this.token = token
        preferencesDataSource.setUserInfo(name, className, schoolName)
    }
}