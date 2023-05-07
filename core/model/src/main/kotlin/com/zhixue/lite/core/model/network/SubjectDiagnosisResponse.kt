package com.zhixue.lite.core.model.network

import kotlinx.serialization.Serializable

@Serializable
data class SubjectDiagnosisResponse(
    val list: List<SubjectDiagnosis>
) {
    @Serializable
    data class SubjectDiagnosis(
        val myRank: Double,
        val subjectCode: String
    )
}