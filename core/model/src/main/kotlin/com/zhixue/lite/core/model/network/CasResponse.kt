package com.zhixue.lite.core.model.network

import kotlinx.serialization.Serializable

@Serializable
data class CasResponse(
    val token: String,
    val userInfo: UserInfo,
    val clazzInfo: ClassInfo? = null,
    val childrens: List<Child>? = null
) {
    @Serializable
    data class Child(
        val userInfo: UserInfo,
        val clazzInfo: ClassInfo
    )

    @Serializable
    data class UserInfo(
        val id: String,
        val name: String,
        val school: SchoolInfo? = null
    ) {
        @Serializable
        data class SchoolInfo(
            val schoolName: String
        )
    }

    @Serializable
    data class ClassInfo(
        val name: String
    )
}