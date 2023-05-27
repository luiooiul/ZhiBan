package com.zhixue.lite.core.model.network

import kotlinx.serialization.Serializable

@Serializable
data class ReportMainResponse(
    val paperList: List<Paper>
) {
    @Serializable
    data class Paper(
        val paperId: String,
        val subjectCode: String,
        val subjectName: String,
        val userLevel: String = "",
        val userScore: Double = 0.0,
        val standardScore: Double,
        val clazzRank: Int? = null
    )
}