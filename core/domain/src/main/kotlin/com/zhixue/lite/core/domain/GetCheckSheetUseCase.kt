package com.zhixue.lite.core.domain

import com.zhixue.lite.core.data.repository.ReportRepository
import com.zhixue.lite.core.model.data.ReportDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCheckSheetUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {
    operator fun invoke(examId: String, paperId: String): Flow<List<ReportDetail.CheckSheet>> {
        return reportRepository.getCheckSheet(examId, paperId).map { response ->
            response.sheetImages.map { ReportDetail.CheckSheet(it) }
        }.catch {
            emit(emptyList())
        }
    }
}