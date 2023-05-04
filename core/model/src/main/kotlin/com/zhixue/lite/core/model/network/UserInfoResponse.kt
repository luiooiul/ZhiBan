package com.zhixue.lite.core.model.network

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val curChildId: String
)