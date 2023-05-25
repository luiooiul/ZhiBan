package com.zhixue.lite.core.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zhixue.lite.core.common.Json
import com.zhixue.lite.core.model.data.ReportMain
import kotlinx.serialization.decodeFromString

@Entity
data class ReportMainEntity(
    @PrimaryKey
    val examId: String,
    val data: String
)

fun ReportMainEntity.asExternalModel(): ReportMain {
    return Json.decodeFromString(data)
}