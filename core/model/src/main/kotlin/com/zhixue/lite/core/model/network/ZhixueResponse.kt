package com.zhixue.lite.core.model.network

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.int
import kotlinx.serialization.json.jsonPrimitive

@Serializable(ZhixueResponseSerializer::class)
data class ZhixueResponse<out T>(
    val result: T?
)

private class ZhixueResponseSerializer<T>(
    private val serializer: KSerializer<T>
) : KSerializer<ZhixueResponse<T?>> {

    override val descriptor = serializer.descriptor

    override fun serialize(encoder: Encoder, value: ZhixueResponse<T?>) {
        serializer.serialize(encoder, value.result!!)
    }

    override fun deserialize(decoder: Decoder): ZhixueResponse<T?> {
        val input = decoder as JsonDecoder
        val element = input.decodeJsonElement() as JsonObject

        val errorCode = element["errorCode"]!!.jsonPrimitive.int
        val errorInfo = element["errorInfo"]!!.jsonPrimitive.content

        require(errorCode == 0) { errorInfo }

        return ZhixueResponse(
            when (val result = element["result"]!!) {
                is JsonObject, is JsonArray -> input.json.decodeFromJsonElement(serializer, result)
                else -> result.jsonPrimitive.content.takeIf { it.isNotEmpty() }?.let {
                    input.json.decodeFromString(serializer, result.jsonPrimitive.content)
                }
            }
        )
    }
}