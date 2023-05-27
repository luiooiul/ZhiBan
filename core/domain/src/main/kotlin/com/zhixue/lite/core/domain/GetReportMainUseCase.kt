package com.zhixue.lite.core.domain

import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.data.ReportMain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import javax.inject.Inject
import kotlin.math.roundToInt

class GetReportMainUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    operator fun invoke(examId: String): Flow<ReportMain> {
        return combine(
            reportRepository.getReportMain(examId),
            reportRepository.getSubjectDiagnosis(examId)
        ) { (paperList), (subjectDiagnosisList) ->

            val totalScore = paperList
                .filterNot { it.paperId.contains("!") }
                .sumOf { it.userScore.toBigDecimal() }
            val totalStandardScore = paperList
                .filterNot { it.paperId.contains("!") }
                .sumOf { it.standardScore.toBigDecimal() }

            val reportMainTotal = ReportMain.Total(
                score = totalScore.stripTrailingZeros().toPlainString(),
                standardScore = totalStandardScore.stripTrailingZeros().toPlainString(),
                rate = totalScore.toFloat() / totalStandardScore.toFloat()
            )

            val reportMainOverviews = paperList.map { paperInfo ->
                val score = paperInfo.userScore.toBigDecimal()
                val standardScore = paperInfo.standardScore.toBigDecimal()
                ReportMain.Overview(
                    id = paperInfo.paperId,
                    name = paperInfo.subjectName,
                    level = paperInfo.userLevel,
                    score = score.stripTrailingZeros().toPlainString(),
                    standardScore = standardScore.stripTrailingZeros().toPlainString(),
                    rate = score.toFloat() / standardScore.toFloat()
                )
            }

            val reportMainTrends = paperList.map { paper ->
                var rank = paper.clazzRank
                reportRepository.getLevelTrend(examId, paper.paperId).map { (list) ->
                    val (totalNum, improveBar) = list.first()
                    if (rank == null) {
                        rank = subjectDiagnosisList
                            .find { it.subjectCode == paper.subjectCode }
                            ?.let { calculateRank(totalNum, it.myRank) }
                    }
                    ReportMain.Trend(
                        name = paper.subjectName,
                        code = improveBar.tag.code,
                        rank = rank?.toString()
                    )
                }.catch {
                    emit(
                        ReportMain.Trend(
                            name = paper.subjectName,
                            code = null,
                            rank = rank?.toString()
                        )
                    )
                }
            }.let { flows ->
                combine(flows) { it.toList() }.single()
            }

            ReportMain(
                total = reportMainTotal,
                overviews = reportMainOverviews,
                trends = reportMainTrends
            ).also {
                reportRepository.saveReportMain(examId, it)
            }
        }.catch {
            emit(reportRepository.getLocalReportMain(examId))
        }
    }
}

private fun calculateRank(totalNum: Int, myRank: Double): Int {
    return (totalNum - (totalNum - 1) * (100 - myRank) / 100).roundToInt()
}