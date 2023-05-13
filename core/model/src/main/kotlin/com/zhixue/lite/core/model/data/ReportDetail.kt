package com.zhixue.lite.core.model.data

data class ReportDetail(
    val total: Total,
    val overview: Overview
) {
    data class Total(
        val score: String,
        val standardScore: String,
        val rate: Float
    )

    data class Overview(
        val correctTopics: List<String>,
        val incorrectTopics: List<String>,
        val partCorrectTopics: List<String>
    )
}