package com.zhixue.lite.core.domain

import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.data.ReportMain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
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
        ) { (paperList), (subjectDiagnosisList) ->

            val trendList = withContext(Dispatchers.IO) {
                paperList.map {
                    async {
                        runCatching {
                            reportRepository.getLevelTrend(examId, it.paperId)
                        }.getOrNull()
                    }
                }.awaitAll()
            }

            var totalScore = BigDecimal.ZERO
            var totalStandardScore = BigDecimal.ZERO

            val reportMainTrends = mutableListOf<ReportMain.Trend>()
            val reportMainOverviews = mutableListOf<ReportMain.Overview>()

            for (index in paperList.indices) {
                val paper = paperList[index]
                val trend = trendList[index]?.list?.firstOrNull()

                val name = paper.subjectName
                val score = paper.userScore.toBigDecimal()
                val standardScore = paper.standardScore.toBigDecimal()

                val rank = paper.clazzRank ?: trend?.totalNum?.let { totalNum ->
                    subjectDiagnosisList
                        .find { it.subjectCode == paper.subjectCode }
                        ?.let { calculateRank(totalNum, it.myRank) }
                }

                if (!paper.paperId.contains("!")) {
                    totalScore += score
                    totalStandardScore += standardScore
                }

                reportMainTrends.add(
                    ReportMain.Trend(
                        name = name,
                        code = trend?.improveBar?.tag?.code,
                        rank = rank
                    )
                )

                reportMainOverviews.add(
                    ReportMain.Overview(
                        id = paper.paperId,
                        name = name,
                        level = paper.userLevel ?: trend?.improveBar?.levelScale,
                        score = score.stripTrailingZeros().toPlainString(),
                        standardScore = standardScore.stripTrailingZeros().toPlainString(),
                        rate = score.toFloat() / standardScore.toFloat()
                    )
                )
            }

            val reportMainTotal = ReportMain.Total(
                score = totalScore.stripTrailingZeros().toPlainString(),
                standardScore = totalStandardScore.stripTrailingZeros().toPlainString(),
                rate = totalScore.toFloat() / totalStandardScore.toFloat()
            )

            ReportMain(
                total = reportMainTotal,
                trends = reportMainTrends,
                overviews = reportMainOverviews
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