package com.zhixue.lite.core.model.network

import kotlinx.serialization.Serializable

@Serializable
data class PaperAnalysisResponse(
    val typeTopicAnalysis: List<TypeTopicAnalysis>
) {
    @Serializable
    data class TypeTopicAnalysis(
        val topicAnalysisDTOs: List<TopicAnalysisDTO>
    ) {
        @Serializable
        data class TopicAnalysisDTO(
            val isCorrect: Boolean,
            val score: Double,
            val standardScore: Double,
            val userScoreRate: Double
        )
    }
}