package com.zhixue.lite.core.model.network

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder

@Serializable
data class CheckSheetResponse(
    @Serializable(SheetImagesSerializer::class) val sheetImages: List<String>,
)

private object SheetImagesSerializer : KSerializer<List<String>> {

    private val serializer = ListSerializer(String.serializer())

    override val descriptor = serializer.descriptor

    override fun serialize(encoder: Encoder, value: List<String>) {
        serializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): List<String> {
        val input = decoder as JsonDecoder
        return input.json.decodeFromString(input.decodeString())
    }
}