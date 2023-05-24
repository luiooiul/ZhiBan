package com.zhixue.lite.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.zhixue.lite.core.database.dao.ReportInfoDao
import com.zhixue.lite.core.model.database.ReportInfoEntity
import com.zhixue.lite.core.network.ApiNetworkDataSource

private const val REPORT_STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class ReportRemoteMediator(
    private val reportInfoDao: ReportInfoDao,
    private val networkDataSource: ApiNetworkDataSource,
    private val reportType: String,
    private val token: String
) : RemoteMediator<Int, ReportInfoEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReportInfoEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> REPORT_STARTING_PAGE_INDEX
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.next ?: return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }

            val (hasNextPage, examInfoList) =
                networkDataSource.getPageAllExamList(reportType, page, token)

            if (loadType == LoadType.REFRESH) {
                reportInfoDao.clearAll(reportType)
            }

            reportInfoDao.insertAll(
                examInfoList
                    .filter { it.isSinglePublish }
                    .map {
                        ReportInfoEntity(
                            id = it.examId,
                            name = it.examName,
                            date = it.examCreateDateTime,
                            next = if (hasNextPage) page + 1 else null,
                            type = reportType
                        )
                    }
            )

            MediatorResult.Success(endOfPaginationReached = !hasNextPage)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}