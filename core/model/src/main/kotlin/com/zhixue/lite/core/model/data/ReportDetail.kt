package com.zhixue.lite.core.model.data

data class ReportDetail(
    val total: Total,
    val overview: Overview,
    val checkSheets: List<CheckSheet>
) {
    data class Total(
        val score: String,
        val standardScore: String,
        val rate: Float
    )

    data class Overview(
        val type: Type,
        val answer: Answer
    ) {
        data class Type(
            val objective: Info,
            val subjective: Info
        ) {
            data class Info(
                val score: String,
                val standardScore: String,
                val rate: Float
            )
        }

        data class Answer(
            val correct: Int,
            val incorrect: Int,
            val partCorrect: Int
        )
    }

    data class CheckSheet(
        val url: String
    )
}