package com.zhixue.lite.core.data.di

import com.zhixue.lite.core.data.repository.LoginRepository
import com.zhixue.lite.core.data.repository.LoginRepositoryImpl
import com.zhixue.lite.core.data.repository.ModifyRepository
import com.zhixue.lite.core.data.repository.ModifyRepositoryImpl
import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.data.repository.ReportRepositoryImpl
import com.zhixue.lite.core.data.repository.UpdateRepository
import com.zhixue.lite.core.data.repository.UpdateRepositoryImpl
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

    @Binds
    fun bindReportRepository(
        reportRepository: ReportRepositoryImpl
    ): ReportRepository

    @Binds
    fun bindUpdateRepository(
        updateRepository: UpdateRepositoryImpl
    ): UpdateRepository

    @Binds
    fun bindModifyRepository(
        modifyRepository: ModifyRepositoryImpl
    ): ModifyRepository
}