package com.zhixue.lite.core.domain

import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.data.ReportDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class GetReportDetailUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {

    operator fun invoke(examId: String, paperId: String): Flow<ReportDetail> {
        return reportRepository.getPaperAnalysis(paperId).map { response ->
            val topicAnalysisDTO = response.typeTopicAnalysis.flatMap { it.topicAnalysisDTOs }

            val totalScore = topicAnalysisDTO
                .sumOf { BigDecimal(it.score.toString()) }
            val totalStandardScore = topicAnalysisDTO
                .sumOf { BigDecimal(it.standardScore.toString()) }
            val totalScale = totalScore.divide(totalStandardScore, 2, RoundingMode.DOWN)
            val total = ReportDetail.Total(
                score = totalScore.stripTrailingZeros().toPlainString(),
                standardScore = totalStandardScore.stripTrailingZeros().toPlainString(),
                scale = totalScale.toFloat()
            )

            val scoreRates = topicAnalysisDTO
                .map { it.userScoreRate }
            val scoreRatesAverage = scoreRates.average()
            val rate = ReportDetail.Rate(
                rates = scoreRates.map { it.toFloat() },
                average = scoreRatesAverage.toFloat()
            )

            val correctTopics = topicAnalysisDTO
                .filter { it.isCorrect }
            val (incorrectTopics, partCorrectTopics) = topicAnalysisDTO
                .filterNot { it.isCorrect }
                .partition { it.score == 0.0 }
            val overview = ReportDetail.Overview(
                correctTopics = correctTopics.map { it.disTitleNumber },
                incorrectTopics = incorrectTopics.map { it.disTitleNumber },
                partCorrectTopics = partCorrectTopics.map { it.disTitleNumber }
            )

            ReportDetail(
                total = total,
                rate = rate,
                overview = overview
            )
        }
    }
}