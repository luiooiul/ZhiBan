package com.zhixue.lite.core.model.data

data class ReportMain(
    val total: Total,
    val overviews: List<Overview>,
    val trends: List<Trend>
) {
    data class Total(
        val score: String,
        val standardScore: String,
        val rate: Float
    )

    data class Overview(
        val id: String,
        val name: String,
        val level: String,
        val score: String,
        val standardScore: String,
        val rate: Float
    )

    data class Trend(
        val name: String,
        val code: String?,
        val rank: String?
    )
}