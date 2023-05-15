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
            val answerType: String,
            val score: Double,
            val standardScore: Double,
            val userScoreRate: Double,
            val topicScoreDTOs: List<TopicScoreDTO>
        ) {
            @Serializable
            data class TopicScoreDTO(
                val score: Double,
                val standScore: Double
            )
        }
    }
}