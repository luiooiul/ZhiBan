package com.zhixue.lite.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zhixue.lite.core.model.network.PageAllExamListResponse
import com.zhixue.lite.core.network.ApiNetworkDataSource

private const val REPORT_STARTING_PAGE_INDEX = 1

class ReportPagingSource(
    private val networkDataSource: ApiNetworkDataSource,
    private val reportType: String,
    private val token: String
) : PagingSource<Int, PageAllExamListResponse.ExamInfo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PageAllExamListResponse.ExamInfo> {
        val pageIndex = params.key ?: REPORT_STARTING_PAGE_INDEX
        return try {
            val response = networkDataSource.getPageAllExamList(reportType, pageIndex, token)
            LoadResult.Page(
                data = response.examInfoList,
                prevKey = if (pageIndex == REPORT_STARTING_PAGE_INDEX) null else pageIndex - 1,
                nextKey = if (response.hasNextPage) pageIndex + 1 else null,
                itemsAfter = if (response.hasNextPage) 1 else 0
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PageAllExamListResponse.ExamInfo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}