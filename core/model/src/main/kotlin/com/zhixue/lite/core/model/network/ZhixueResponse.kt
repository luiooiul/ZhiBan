package com.zhixue.lite.core.model.network

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

@Serializable(ZhixueResponseSerializer::class)
data class ZhixueResponse<out T>(
    val result: T
)

private class ZhixueResponseSerializer<T>(
    private val serializer: KSerializer<T>
) : KSerializer<ZhixueResponse<T>> {

    override val descriptor = serializer.descriptor

    override fun serialize(encoder: Encoder, value: ZhixueResponse<T>) {
        serializer.serialize(encoder, value.result)
    }

    override fun deserialize(decoder: Decoder): ZhixueResponse<T> {
        val input = decoder as JsonDecoder
        val element = input.decodeJsonElement() as JsonObject

        val errorCode = element["errorCode"]!!.jsonPrimitive.int
        val errorInfo = element["errorInfo"]!!.jsonPrimitive.content

        require(errorCode == 0) { errorInfo }

        val result = element["result"]!!

        return ZhixueResponse(input.json.decodeFromJsonElement(serializer, result))
    }
}