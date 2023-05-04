package com.zhixue.lite.core.model.network

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(ChangYanResponseSerializer::class)
data class ChangYanResponse<out T>(
    val data: T
)

private class ChangYanResponseSerializer<T>(
    private val serializer: KSerializer<T>
) : KSerializer<ChangYanResponse<T>> {

    override val descriptor = serializer.descriptor

    override fun serialize(encoder: Encoder, value: ChangYanResponse<T>) {
        serializer.serialize(encoder, value.data)
    }

    override fun deserialize(decoder: Decoder): ChangYanResponse<T> {
        val input = decoder as JsonDecoder
        val element = input.decodeJsonElement() as JsonObject

        val code = element["code"]!!.jsonPrimitive.content
        val message = element["message"]!!.jsonPrimitive.content

        require(code == "success") { message }

        val data = element["data"]!!

        return ChangYanResponse(input.json.decodeFromJsonElement(serializer, data))
    }
}