package com.zhixue.lite.core.domain

import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.data.ReportMain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import java.math.BigDecimal
import javax.inject.Inject
import kotlin.math.roundToInt

class GetReportMainUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    operator fun invoke(examId: String): Flow<ReportMain> {
        return combine(
            reportRepository.getReportMain(examId),
            reportRepository.getSubjectDiagnosis(examId)
        ) { reportMainResponse, subjectDiagnosisResponse ->
            val paperList = reportMainResponse.paperList
            val subjectDiagnosisList = subjectDiagnosisResponse.list

            val totalScore = paperList
                .filterNot { it.paperId.contains("!") }
                .sumOf { BigDecimal(it.userScore.toString()) }
            val totalStandardScore = paperList
                .filterNot { it.paperId.contains("!") }
                .sumOf { BigDecimal(it.standardScore.toString()) }
            val totalRate = totalScore.toFloat() / totalStandardScore.toFloat()
            val total = ReportMain.Total(
                score = totalScore.stripTrailingZeros().toPlainString(),
                standardScore = totalStandardScore.stripTrailingZeros().toPlainString(),
                rate = totalRate
            )

            val overviews = paperList.map { paperInfo ->
                val score = BigDecimal(paperInfo.userScore.toString())
                val standardScore = BigDecimal(paperInfo.standardScore.toString())
                val rate = score.toFloat() / standardScore.toFloat()
                ReportMain.Overview(
                    id = paperInfo.paperId,
                    name = paperInfo.subjectName,
                    level = paperInfo.userLevel,
                    score = score.stripTrailingZeros().toPlainString(),
                    standardScore = standardScore.stripTrailingZeros().toPlainString(),
                    rate = rate
                )
            }

            val trends = paperList.map { paperInfo ->
                reportRepository.getLevelTrend(examId, paperInfo.paperId).map { response ->
                    val classTrendInfo = response.list.first()
                    val rank = subjectDiagnosisList
                        .find { it.subjectCode == paperInfo.subjectCode }
                        ?.let { calculateRank(classTrendInfo.totalNum, it.myRank) }
                    ReportMain.Trend(
                        name = paperInfo.subjectName,
                        code = classTrendInfo.improveBar.tag.code,
                        rank = rank?.toString()
                    )
                }.catch {
                    emit(ReportMain.Trend(name = paperInfo.subjectName, code = null, rank = null))
                }
            }.let { flows ->
                combine(flows) { it.toList() }
            }

            ReportMain(
                total = total,
                overviews = overviews,
                trends = trends.single()
            )
        }
    }
}

private fun calculateRank(totalNum: Int, myRank: Double): Int {
    return (totalNum - (totalNum - 1) * (100 - myRank) / 100).roundToInt()
}