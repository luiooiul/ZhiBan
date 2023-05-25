package com.zhixue.lite.core.common

import kotlinx.serialization.json.Json

val Json = Json {
    allowSpecialFloatingPointValues = true
}