package com.zhixue.lite.core.model.network

import kotlinx.serialization.Serializable

@Serializable
data class ReportMainResponse(
    val paperList: List<PaperInfo>
) {
    @Serializable
    data class PaperInfo(
        val paperId: String,
        val subjectCode: String,
        val subjectName: String,
        val userLevel: String = "",
        val userScore: Double = 0.0,
        val standardScore: Double,
    )
}