package com.zhixue.lite.core.domain

import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.data.ReportMain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class GetReportMainUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    operator fun invoke(reportId: String): Flow<ReportMain> {
        return combine(
            reportRepository.getReportMain(reportId),
            reportRepository.getSubjectDiagnosis(reportId)
        ) { reportMainResponse, subjectDiagnosisResponse ->
            val paperList = reportMainResponse.paperList
            val subjectDiagnosisList = subjectDiagnosisResponse.list

            val totalScore = paperList
                .filterNot { it.paperId.contains("!") }
                .sumOf { BigDecimal(it.userScore) }
            val totalStandardScore = paperList
                .filterNot { it.paperId.contains("!") }
                .sumOf { BigDecimal(it.standardScore) }
            val totalScale = totalScore.divide(totalStandardScore, 2, RoundingMode.DOWN)
            val total = ReportMain.Total(
                score = totalScore.toString(),
                standardScore = totalStandardScore.toString(),
                scale = totalScale.toFloat()
            )

            val overviews = paperList.map { paperInfo ->
                val score = BigDecimal(paperInfo.userScore)
                val standardScore = BigDecimal(paperInfo.standardScore)
                val scale = score.divide(standardScore, 2, RoundingMode.DOWN)
                ReportMain.Overview(
                    id = paperInfo.paperId,
                    name = paperInfo.subjectName,
                    score = score.toString(),
                    standardScore = standardScore.toString(),
                    scale = scale.toFloat()
                )
            }

            val trends = paperList.map { paperInfo ->
                reportRepository.getLevelTrend(reportId, paperInfo.paperId).map { response ->
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
                    ReportMain.Trend(name = paperInfo.subjectName, code = null, rank = null)
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

private fun calculateRank(totalNum: Int, myRank: Double): BigDecimal {
    return BigDecimal(totalNum).multiply(BigDecimal(myRank))
        .divide(BigDecimal(100), 0, RoundingMode.UP)
}