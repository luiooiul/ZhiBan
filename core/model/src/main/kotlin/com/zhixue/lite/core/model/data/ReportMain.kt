package com.zhixue.lite.core.model.data

import com.zhixue.lite.core.common.Json
import com.zhixue.lite.core.model.database.ReportMainEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString

@Serializable
data class ReportMain(
    val total: Total,
    val trends: List<Trend>,
    val overviews: List<Overview>
) {
    @Serializable
    data class Total(
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

    @Serializable
    data class Overview(
        val id: String,
        val name: String,
        val level: String,
        val score: String,
        val standardScore: String,
        val rate: Float
    )
}

fun ReportMain.asEntity(examId: String): ReportMainEntity {
    return ReportMainEntity(examId, Json.encodeToString(this))
}