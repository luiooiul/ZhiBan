package com.zhixue.lite.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhixue.lite.core.model.database.ReportMainEntity

@Dao
interface ReportMainDao {

    @Query("SELECT * FROM ReportMainEntity WHERE examId = :examId")
    suspend fun getReportMain(examId: String): ReportMainEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReportMain(entity: ReportMainEntity)
}