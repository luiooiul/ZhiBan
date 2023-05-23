package com.zhixue.lite.core.model.data

import com.zhixue.lite.core.model.database.ReportMainEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class ReportMain(
    val total: Total,
    val overviews: List<Overview>,
    val trends: List<Trend>
) {
    @Serializable
    data class Total(
        val score: String,
        val standardScore: String,
        val rate: Float
    )

    @Serializable
    data class Overview(
        val id: String,
        val name: String,
        val level: String,
        val score: String,
        val standardScore: String,
        val rate: Float
    )

    @Serializable
    data class Trend(
        val name: String,
        val code: String?,
        val rank: String?
    )
}

fun ReportMain.asEntity(examId: String): ReportMainEntity {
    return ReportMainEntity(examId, Json.encodeToString(this))
}