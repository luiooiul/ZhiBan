package com.zhixue.lite.core.data.di

import com.zhixue.lite.core.data.repository.LoginRepository
import com.zhixue.lite.core.data.repository.LoginRepositoryImpl
import com.zhixue.lite.core.data.repository.UserRepository
import com.zhixue.lite.core.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun bindLoginRepository(
        loginRepository: LoginRepositoryImpl
    ): LoginRepository
}