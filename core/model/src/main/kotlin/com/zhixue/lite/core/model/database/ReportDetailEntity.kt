package com.zhixue.lite.core.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhixue.lite.core.common.Json
import com.zhixue.lite.core.model.data.ReportDetail
import kotlinx.serialization.decodeFromString

@Entity
data class ReportDetailEntity(
    @PrimaryKey
    val paperId: String,
    val data: String
)

fun ReportDetailEntity.asExternalModel(): ReportDetail {
    return Json.decodeFromString(data)
}