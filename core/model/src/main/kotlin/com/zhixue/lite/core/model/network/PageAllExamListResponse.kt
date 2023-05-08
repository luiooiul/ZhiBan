package com.zhixue.lite.core.model.network

import kotlinx.serialization.Serializable

@Serializable
data class PageAllExamListResponse(
    val hasNextPage: Boolean,
    val examInfoList: List<ExamInfo>
) {
    @Serializable
    data class ExamInfo(
        val examId: String,
        val examName: String,
        val examCreateDateTime: Long,
        val isSinglePublish: Boolean
    )
}