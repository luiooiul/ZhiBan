package com.zhixue.lite.core.model.data

data class UserData(
    val username: String,
    val password: String,
    val name: String,
    val className: String,
    val schoolName: String,
    val credentials: Map<String, String>
)