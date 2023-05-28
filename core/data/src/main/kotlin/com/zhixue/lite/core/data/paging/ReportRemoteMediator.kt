package com.zhixue.lite.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zhixue.lite.core.database.dao.ReportInfoDao
import com.zhixue.lite.core.model.database.ReportInfoEntity
import com.zhixue.lite.core.network.ApiNetworkDataSource
import kotlinx.coroutines.delay

private const val REPORT_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class ReportRemoteMediator(
    private val reportInfoDao: ReportInfoDao,
    private val networkDataSource: ApiNetworkDataSource,
    private val reportType: String,
    private val token: String
) : RemoteMediator<Int, ReportInfoEntity>() {

    override suspend fun initialize(): InitializeAction {
        return try {
            val localLatestReport =
                reportInfoDao.query(reportType)
            val networkLatestReport =
                networkDataSource.getPageAllExamList(reportType, REPORT_STARTING_PAGE_INDEX, token)
                    .examInfoList.firstOrNull()
            check(networkLatestReport != null && localLatestReport?.id != networkLatestReport.examId)
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } catch (e: Exception) {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReportInfoEntity>
    ): MediatorResult {
        return try {
            delay(500)

            val page = when (loadType) {
                LoadType.REFRESH -> REPORT_STARTING_PAGE_INDEX
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.next ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }

            val (hasNextPage, examInfoList) =
                networkDataSource.getPageAllExamList(reportType, page, token)

            val endOfPaginationReached = !hasNextPage

            if (loadType == LoadType.REFRESH) {
                reportInfoDao.deleteByReportType(reportType)
            }

            reportInfoDao.insertAll(
                examInfoList.map {
                    ReportInfoEntity(
                        id = it.examId,
                        name = it.examName,
                        date = it.examCreateDateTime,
                        next = if (endOfPaginationReached) null else page + 1,
                        type = reportType
                    )
                }
            )

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}