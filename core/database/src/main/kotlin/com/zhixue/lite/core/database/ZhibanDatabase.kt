package com.zhixue.lite.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhixue.lite.core.database.dao.ReportDetailDao
import com.zhixue.lite.core.database.dao.ReportInfoDao
import com.zhixue.lite.core.database.dao.ReportMainDao
import com.zhixue.lite.core.model.database.ReportDetailEntity
import com.zhixue.lite.core.model.database.ReportInfoEntity
import com.zhixue.lite.core.model.database.ReportMainEntity

@Database(
    version = 2,
    entities = [ReportInfoEntity::class, ReportMainEntity::class, ReportDetailEntity::class],
    exportSchema = false
)
abstract class ZhibanDatabase : RoomDatabase() {
    abstract fun reportInfoDao(): ReportInfoDao
    abstract fun reportMainDao(): ReportMainDao
    abstract fun reportDetailDao(): ReportDetailDao
}