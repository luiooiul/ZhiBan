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
    @Serializable(SheetDataSerializer::class) val sheetDatas: SheetData,
    @Serializable(SheetImagesSerializer::class) val sheetImages: List<String>
) {
    @Serializable
    data class SheetData(
        val userAnswerRecordDTO: UserAnswerRecordDTO,
        val answerSheetLocationDTO: AnswerSheetLocationDTO
    ) {
        @Serializable
        data class UserAnswerRecordDTO(
            val answerRecordDetails: List<AnswerRecordDetail>
        ) {
            @Serializable
            data class AnswerRecordDetail(
                val score: Double,
                val standardScore: Double
            )
        }

        @Serializable
        data class AnswerSheetLocationDTO(
            val comeFrom: Int,
            val paperType: String,
            val pageSheets: List<PageSheet>
        ) {
            @Serializable
            data class PageSheet(
                val sections: List<Section>,
                val widthAfterCorrect: Double? = null,
                val heightAfterCorrect: Double? = null
            ) {
                @Serializable
                data class Section(
                    val contents: Contents
                ) {
                    @Serializable
                    data class Contents(
                        val branch: List<Branch>,
                        val position: Position
                    ) {
                        @Serializable
                        data class Branch(
                            val ixList: List<Int>
                        )

                        @Serializable
                        data class Position(
                            val top: Int,
                            val left: Int
                        )
                    }
                }
            }
        }
    }
}

private object SheetDataSerializer : KSerializer<CheckSheetResponse.SheetData> {

    private val serializer = CheckSheetResponse.SheetData.serializer()

    override val descriptor = serializer.descriptor

    override fun serialize(encoder: Encoder, value: CheckSheetResponse.SheetData) {
        serializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): CheckSheetResponse.SheetData {
        require(decoder is JsonDecoder)
        return decoder.json.decodeFromString(decoder.decodeString())
    }
}

private object SheetImagesSerializer : KSerializer<List<String>> {

    private val serializer = ListSerializer(String.serializer())

    override val descriptor = serializer.descriptor

    override fun serialize(encoder: Encoder, value: List<String>) {
        serializer.serialize(encoder, value)
    }

    override fun deserialize(decoder: Decoder): List<String> {
        require(decoder is JsonDecoder)
        return decoder.json.decodeFromString(decoder.decodeString())
    }
}