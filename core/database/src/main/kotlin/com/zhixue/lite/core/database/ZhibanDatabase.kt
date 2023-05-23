package com.zhixue.lite.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zhixue.lite.core.database.dao.ReportDetailDao
import com.zhixue.lite.core.database.dao.ReportMainDao
import com.zhixue.lite.core.model.database.ReportDetailEntity
import com.zhixue.lite.core.model.database.ReportMainEntity

@Database(
    version = 1,
    entities = [ReportMainEntity::class, ReportDetailEntity::class],
    exportSchema = false
)
abstract class ZhibanDatabase : RoomDatabase() {
    abstract fun reportMainDao(): ReportMainDao
    abstract fun reportDetailDao(): ReportDetailDao
}