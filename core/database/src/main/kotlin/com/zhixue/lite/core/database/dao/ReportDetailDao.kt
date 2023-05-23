package com.zhixue.lite.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhixue.lite.core.model.database.ReportDetailEntity

@Dao
interface ReportDetailDao {

    @Query("SELECT * FROM ReportDetailEntity WHERE paperId = :paperId")
    suspend fun getReportDetail(paperId: String): ReportDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReportDetail(entity: ReportDetailEntity)
}