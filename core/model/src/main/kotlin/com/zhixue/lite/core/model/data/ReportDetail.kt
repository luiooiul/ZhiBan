package com.zhixue.lite.core.model.data

data class ReportDetail(
    val total: Total,
    val overview: Overview,
    val checkSheet: CheckSheet
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
        val currentWidth: Int = 0,
        val currentHeight: Int = 0,
        val pages: List<Page> = emptyList()
    ) {
        data class Page(
            val url: String,
            val sections: List<Section>
        ) {
            data class Section(
                val x: Int,
                val y: Int,
                val score: String,
                val standardScore: String
            )
        }
    }
}