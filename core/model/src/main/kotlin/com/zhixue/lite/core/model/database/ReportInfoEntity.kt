package com.zhixue.lite.core.model.database

import androidx.room.Entity

@Entity(primaryKeys = ["id", "type", "index"])
data class ReportInfoEntity(
    val id: String,
    val name: String,
    val date: Long,
    val isSinglePublish: Boolean,
    val next: Int?,
    val index: Int,
    val type: String
)