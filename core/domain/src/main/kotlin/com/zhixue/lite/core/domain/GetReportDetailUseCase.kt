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
        ) { (typeTopicAnalysis), reportDetailCheckSheets ->

            var correctTopics = 0
            var incorrectTopics = 0
            var partCorrectTopics = 0

            var objectiveScore = BigDecimal.ZERO
            var objectiveStandardScore = BigDecimal.ZERO
            var subjectiveScore = BigDecimal.ZERO
            var subjectiveStandardScore = BigDecimal.ZERO

            for (topicAnalysisDTO in typeTopicAnalysis.flatMap { it.topicAnalysisDTOs }) {
                val score = topicAnalysisDTO.score.toBigDecimal()
                val standardScore = topicAnalysisDTO.standardScore.toBigDecimal()

                if (topicAnalysisDTO.answerType == "s01Text") {
                    objectiveScore += score
                    objectiveStandardScore += standardScore
                } else {
                    subjectiveScore += score
                    subjectiveStandardScore += standardScore
                }

                with(topicAnalysisDTO.topicScoreDTOs) {
                    correctTopics += count { it.score == it.standScore }
                    incorrectTopics += count { it.score == 0.0 }
                    partCorrectTopics += count { it.score != 0.0 && it.score != it.standScore }
                }
            }

            val totalScore = objectiveScore + subjectiveScore
            val totalStandardScore = objectiveStandardScore + subjectiveStandardScore

            val reportDetailTotal = ReportDetail.Total(
                score =
                totalScore.stripTrailingZeros().toPlainString(),
                standardScore =
                totalStandardScore.stripTrailingZeros().toPlainString(),
                rate =
                totalScore.toFloat() / totalStandardScore.toFloat()
            )

            val reportDetailOverview = ReportDetail.Overview(
                type = ReportDetail.Overview.Type(
                    objective = ReportDetail.Overview.Type.Info(
                        score =
                        objectiveScore.stripTrailingZeros().toPlainString(),
                        standardScore =
                        objectiveStandardScore.stripTrailingZeros().toPlainString(),
                        rate =
                        objectiveScore.toFloat() / objectiveStandardScore.toFloat()
                    ),
                    subjective = ReportDetail.Overview.Type.Info(
                        score =
                        subjectiveScore.stripTrailingZeros().toPlainString(),
                        standardScore =
                        subjectiveStandardScore.stripTrailingZeros().toPlainString(),
                        rate =
                        subjectiveScore.toFloat() / subjectiveStandardScore.toFloat()
                    )
                ),
                answer = ReportDetail.Overview.Answer(
                    correct = correctTopics,
                    incorrect = incorrectTopics,
                    partCorrect = partCorrectTopics
                )
            )

            ReportDetail(
                total = reportDetailTotal,
                overview = reportDetailOverview,
                checkSheets = reportDetailCheckSheets
            )
        }
    }
}