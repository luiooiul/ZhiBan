package com.zhixue.lite.core.domain

import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.data.ReportDetail
import com.zhixue.lite.core.model.network.CheckSheetResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.roundToInt

class GetCheckSheetUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    operator fun invoke(examId: String, paperId: String): Flow<List<ReportDetail.CheckSheet>> {
        return reportRepository.getCheckSheet(examId, paperId).map { (sheetData, sheetImages) ->
            val (answerRecordDetails) = sheetData.userAnswerRecordDTO
            val (comeFrom, paperType, pageSheets) = sheetData.answerSheetLocationDTO

            pageSheets.mapIndexed { index, (sections, correctWidth, correctHeight) ->
                ReportDetail.CheckSheet(
                    url = sheetImages[index],
                    size = calculateCorrectSize(comeFrom, paperType, correctWidth, correctHeight),
                    sections = mapToCheckSheetSections(sections, answerRecordDetails)
                )
            }
        }.catch {
            emit(emptyList())
        }
    }
}

private fun calculateCorrectSize(
    comeFrom: Int,
    paperType: String,
    correctWidth: Double?,
    correctHeight: Double?
): Pair<Int, Int> {
    val width: Int
    val height: Int

    if (comeFrom == 0) {
        width = if (paperType == "A3") 420 else 210
        height = 297
    } else {
        width = correctWidth?.roundToInt() ?: if (paperType == "A3") 2199 else 1100
        height = correctHeight?.roundToInt() ?: 1555
    }

    return width to height
}

private fun mapToCheckSheetSections(
    sections: List<CheckSheetResponse.SheetData.AnswerSheetLocationDTO.PageSheet.Section>,
    answerRecordDetails: List<CheckSheetResponse.SheetData.UserAnswerRecordDTO.AnswerRecordDetail>
): List<ReportDetail.CheckSheet.Section> {
    return sections.map { (contents) ->
        val ixList =
            contents.branch.flatMap { it.ixList }
        val sectionScore =
            ixList.sumOf { answerRecordDetails[it - 1].score.toBigDecimal() }
        val sectionStandardScore =
            ixList.sumOf { answerRecordDetails[it - 1].standardScore.toBigDecimal() }

        ReportDetail.CheckSheet.Section(
            x = contents.position.left,
            y = contents.position.top,
            width = contents.position.width,
            score = sectionScore.stripTrailingZeros().toPlainString(),
            standardScore = sectionStandardScore.stripTrailingZeros().toPlainString()
        )
    }
}