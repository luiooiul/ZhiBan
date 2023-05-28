package com.zhixue.lite.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhixue.lite.core.model.database.ReportInfoEntity

@Dao
interface ReportInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<ReportInfoEntity>)

    @Query("SELECT * FROM ReportInfoEntity WHERE type = :reportType")
    fun pagingSource(reportType: String): PagingSource<Int, ReportInfoEntity>

    @Query("SELECT * FROM ReportInfoEntity WHERE type = :reportType")
    suspend fun query(reportType: String): ReportInfoEntity?

    @Query("DELETE FROM ReportInfoEntity WHERE type = :reportType")
    suspend fun deleteByReportType(reportType: String)

    @Query("DELETE FROM ReportInfoEntity")
    suspend fun clearAll()
}