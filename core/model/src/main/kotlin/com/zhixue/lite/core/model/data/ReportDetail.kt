package com.zhixue.lite.core.model.data

import com.zhixue.lite.core.common.Json
import com.zhixue.lite.core.model.database.ReportDetailEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString

@Serializable
data class ReportDetail(
    val total: Total,
    val overview: Overview,
    val checkSheets: List<CheckSheet>
) {
    @Serializable
    data class Total(
        val score: String,
        val standardScore: String,
        val rate: Float
    )

    @Serializable
    data class Overview(
        val type: Type,
        val answer: Answer
    ) {
        @Serializable
        data class Type(
            val objective: Info,
            val subjective: Info
        ) {
            @Serializable
            data class Info(
                val score: String,
                val standardScore: String,
                val rate: Float
            )
        }

        @Serializable
        data class Answer(
            val correct: Int,
            val incorrect: Int,
            val partCorrect: Int
        )
    }

    @Serializable
    data class CheckSheet(
        val url: String,
        val size: Pair<Int, Int>,
        val sections: List<Section>
    ) {
        @Serializable
        data class Section(
            val x: Int,
            val y: Int,
            val width: Int,
            val score: String,
            val standardScore: String
        )
    }
}

fun ReportDetail.asEntity(paperId: String): ReportDetailEntity {
    return ReportDetailEntity(paperId, Json.encodeToString(this))
}