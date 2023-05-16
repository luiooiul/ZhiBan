package com.zhixue.lite.core.domain

import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.data.ReportDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.math.BigDecimal
import javax.inject.Inject

class GetReportDetailUseCase @Inject constructor(
    private val reportRepository: ReportRepository,
    private val getCheckSheetUseCase: GetCheckSheetUseCase
) {
    operator fun invoke(examId: String, paperId: String): Flow<ReportDetail> {
        return combine(
            reportRepository.getPaperAnalysis(paperId),
            getCheckSheetUseCase(examId, paperId)
        ) { paperAnalysisResponse, checkSheet ->
            val topicAnalysisDTO =
                paperAnalysisResponse.typeTopicAnalysis.flatMap { it.topicAnalysisDTOs }

            val totalScore = topicAnalysisDTO
                .sumOf { BigDecimal(it.score.toString()) }
            val totalStandardScore = topicAnalysisDTO
                .sumOf { BigDecimal(it.standardScore.toString()) }
            val totalRate =
                totalScore.toFloat() / totalStandardScore.toFloat()

            val total = ReportDetail.Total(
                score = totalScore.stripTrailingZeros().toPlainString(),
                standardScore = totalStandardScore.stripTrailingZeros().toPlainString(),
                rate = totalRate
            )

            val objectiveTopics = topicAnalysisDTO
                .filter { it.answerType == "s01Text" }
            val objectiveScore = objectiveTopics
                .sumOf { BigDecimal(it.score.toString()) }
            val objectiveStandardScore = objectiveTopics
                .sumOf { BigDecimal(it.standardScore.toString()) }
            val objectiveRate =
                objectiveScore.toFloat() / objectiveStandardScore.toFloat()

            val subjectiveTopics = topicAnalysisDTO
                .filter { it.answerType == "s02Image" }
            val subjectiveScore = subjectiveTopics
                .sumOf { BigDecimal(it.score.toString()) }
            val subjectiveStandardScore = subjectiveTopics
                .sumOf { BigDecimal(it.standardScore.toString()) }
            val subjectiveRate =
                subjectiveScore.toFloat() / subjectiveStandardScore.toFloat()

            val type = ReportDetail.Overview.Type(
                objective = ReportDetail.Overview.Type.Info(
                    score = objectiveScore.stripTrailingZeros().toPlainString(),
                    standardScore = objectiveStandardScore.stripTrailingZeros().toPlainString(),
                    rate = objectiveRate
                ),
                subjective = ReportDetail.Overview.Type.Info(
                    score = subjectiveScore.stripTrailingZeros().toPlainString(),
                    standardScore = subjectiveStandardScore.stripTrailingZeros().toPlainString(),
                    rate = subjectiveRate
                )
            )

            val topics = topicAnalysisDTO.flatMap { it.topicScoreDTOs }
            val correctTopics = topics.filter { it.score == it.standScore }
            val incorrectTopics = topics.filter { it.score == 0.0 }
            val partCorrectTopics = topics.filter { it.score != 0.0 && it.score != it.standScore }

            val answer = ReportDetail.Overview.Answer(
                correct = correctTopics.size,
                incorrect = incorrectTopics.size,
                partCorrect = partCorrectTopics.size
            )

            ReportDetail(
                total = total,
                overview = ReportDetail.Overview(type, answer),
                checkSheet = checkSheet
            )
        }
    }
}