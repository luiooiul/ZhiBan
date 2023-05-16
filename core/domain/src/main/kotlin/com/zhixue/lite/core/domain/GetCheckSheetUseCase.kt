package com.zhixue.lite.core.domain

import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.data.ReportDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import javax.inject.Inject

class GetCheckSheetUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    operator fun invoke(examId: String, paperId: String): Flow<ReportDetail.CheckSheet> {
        return reportRepository.getCheckSheet(examId, paperId).map { response ->
            val (sheetData, sheetImages) = response

            val (comeFrom, paperType, pageSheets) = sheetData.answerSheetLocationDTO
            val (currentWidth, currentHeight) = getCurrentSize(comeFrom, paperType)

            val answerRecordDetails = sheetData.userAnswerRecordDTO.answerRecordDetails

            ReportDetail.CheckSheet(
                currentWidth = currentWidth,
                currentHeight = currentHeight,
                pages = pageSheets.mapIndexed { index, pageSheet ->
                    ReportDetail.CheckSheet.Page(
                        url = sheetImages[index],
                        sections = pageSheet.sections.map { section ->
                            val (branch, position) = section.contents
                            val ixList = branch.flatMap { it.ixList }

                            val score = ixList.sumOf {
                                BigDecimal(answerRecordDetails[it - 1].score.toString())
                            }

                            val standardScore = ixList.sumOf {
                                BigDecimal(answerRecordDetails[it - 1].standardScore.toString())
                            }

                            ReportDetail.CheckSheet.Page.Section(
                                x = position.left,
                                y = position.top,
                                score = score.stripTrailingZeros().toPlainString(),
                                standardScore = standardScore.stripTrailingZeros().toPlainString()
                            )
                        }
                    )
                }
            )
        }.catch {
            emit(ReportDetail.CheckSheet())
        }
    }
}

private fun getCurrentSize(comeFrom: Int, paperType: String): Pair<Int, Int> {
    var currentWidth = 0
    var currentHeight = 0

    if (comeFrom == 1) {

    } else {
        currentWidth = if (paperType == "A3") 420 else 210
        currentHeight = 297
    }

    return currentWidth to currentHeight
}