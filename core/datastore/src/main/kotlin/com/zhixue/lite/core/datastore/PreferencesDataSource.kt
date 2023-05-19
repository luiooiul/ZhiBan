package com.zhixue.lite.core.datastore

import androidx.datastore.core.DataStore
import com.zhixue.lite.core.model.data.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {

    val userData = userPreferences.data.map {
        UserData(
            username = it.username,
            password = it.password,
            name = it.name,
            className = it.className,
            schoolName = it.schoolName,
            credentials = it.credentialsMap
        )
    }

    suspend fun setUser(username: String, password: String) {
        userPreferences.updateData {
            it.copy {
                this.username = username
                this.password = password
                this.credentials.put(username, password)
            }
        }
    }

    suspend fun clearUser() {
        userPreferences.updateData {
            it.defaultInstanceForType.copy {
                this.credentials.putAll(credentials)
            }
        }
    }

    suspend fun setUserInfo(name: String, className: String, schoolName: String) {
        userPreferences.updateData {
            it.copy {
                this.name = name
                this.className = className
                this.schoolName = schoolName
            }
        }
    }
}