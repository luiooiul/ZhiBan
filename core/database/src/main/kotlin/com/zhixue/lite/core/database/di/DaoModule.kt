package com.zhixue.lite.core.database.di

import com.zhixue.lite.core.database.ZhibanDatabase
import com.zhixue.lite.core.database.dao.ReportDetailDao
import com.zhixue.lite.core.database.dao.ReportInfoDao
import com.zhixue.lite.core.database.dao.ReportMainDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideReportInfoDao(
        database: ZhibanDatabase
    ): ReportInfoDao {
        return database.reportInfoDao()
    }

    @Provides
    fun provideReportMainDao(
        database: ZhibanDatabase
    ): ReportMainDao {
        return database.reportMainDao()
    }

    @Provides
    fun provideReportDetailDao(
        database: ZhibanDatabase
    ): ReportDetailDao {
        return database.reportDetailDao()
    }
}