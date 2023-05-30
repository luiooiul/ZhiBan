package com.zhixue.lite.core.model.network

import kotlinx.serialization.Serializable

@Serializable
data class NewVersionCodeResponse(
    val newVersionCode: Long
)