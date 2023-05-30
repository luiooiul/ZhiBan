package com.zhixue.lite.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zhixue.lite.core.model.database.ReportMainEntity

@Dao
interface ReportMainDao {

    @Query("SELECT * FROM ReportMainEntity WHERE examId = :examId")
    suspend fun query(examId: String): ReportMainEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ReportMainEntity)

    @Query("DELETE FROM ReportMainEntity")
    suspend fun clearAll()
}