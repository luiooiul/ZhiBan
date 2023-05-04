package com.zhixue.lite.core.model.network

import kotlinx.serialization.Serializable

@Serializable
data class SsoResponse(
    val at: String,
    val userId: String
)