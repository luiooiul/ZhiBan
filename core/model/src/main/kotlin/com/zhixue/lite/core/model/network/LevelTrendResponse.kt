package com.zhixue.lite.core.model.network

import kotlinx.serialization.Serializable

@Serializable
data class LevelTrendResponse(
    val list: List<LevelTrendInfo>
) {
    @Serializable
    data class LevelTrendInfo(
        val improveBar: ImproveBar
    ) {
        @Serializable
        data class ImproveBar(
            val tag: Tag
        ) {
            @Serializable
            data class Tag(
                val code: String
            )
        }
    }
}