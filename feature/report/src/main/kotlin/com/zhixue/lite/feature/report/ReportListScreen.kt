package com.zhixue.lite.feature.report

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.zhixue.lite.core.model.data.ReportInfo
import com.zhixue.lite.core.ui.component.HorizontalDivider
import com.zhixue.lite.core.ui.component.Image
import com.zhixue.lite.core.ui.component.Text
import com.zhixue.lite.core.ui.theme.Theme
import kotlinx.coroutines.launch

enum class ReportType(
    @StringRes val nameRes: Int
) {
    EXAM(R.string.report_type_exam),
    HOMEWORK(R.string.report_type_homework)
}

@Composable
fun ReportListScreen(
    navigateToReportMain: (String) -> Unit,
    viewModel: ReportListViewModel = hiltViewModel()
) {
    ReportListScreen(
        examList = viewModel.examList.collectAsLazyPagingItems(),
        homeworkList = viewModel.homeworkList.collectAsLazyPagingItems(),
        navigateToReportMain = navigateToReportMain
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReportListScreen(
    examList: LazyPagingItems<ReportInfo>,
    homeworkList: LazyPagingItems<ReportInfo>,
    navigateToReportMain: (String) -> Unit,
    reportTypes: Array<ReportType> = ReportType.values()
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
    ) {
        ReportListTopTabs(
            names = reportTypes.map { stringResource(it.nameRes) },
            currentPagerIndex = pagerState.currentPage,
            onTabClick = { index ->
                scope.launch { pagerState.animateScrollToPage(index) }
            }
        )
        HorizontalDivider()
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            pageCount = reportTypes.size,
            beyondBoundsPageCount = reportTypes.size
        ) { index ->
            ReportListPagerContent(
                pagerList = when (reportTypes[index]) {
                    ReportType.EXAM -> examList
                    ReportType.HOMEWORK -> homeworkList
                },
                onItemClick = { id ->
                    if (id != null) {
                        navigateToReportMain(id)
                    } else {
                        Toast.makeText(context, R.string.text_no_support, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

@Composable
fun ReportListTopTabs(
    names: List<String>,
    currentPagerIndex: Int,
    onTabClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        names.forEachIndexed { index, name ->
            Text(
                modifier = Modifier
                    .clip(Theme.shapes.small)
                    .clickable { onTabClick(index) }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = name,
                color = if (currentPagerIndex == index) Theme.colors.primary else Theme.colors.onBackgroundVariant,
                style = Theme.typography.labelMedium
            )
        }
    }
}

@Composable
fun ReportListPagerContent(
    pagerList: LazyPagingItems<ReportInfo>,
    onItemClick: (String?) -> Unit
) {
    Crossfade(pagerList.loadState.refresh is LoadState.Loading) { isLoading ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            userScrollEnabled = !isLoading
        ) {
            if (isLoading) {
                items(10) {
                    ReportListPagerItem(null, onItemClick)
                    Spacer(modifier = Modifier.height(1.dp))
                }
            } else {
                items(pagerList) { reportInfo ->
                    ReportListPagerItem(reportInfo, onItemClick)
                    HorizontalDivider(spacing = 24.dp)
                }
            }
        }
    }
}

@Composable
fun ReportListPagerItem(
    reportInfo: ReportInfo?,
    onClick: (String?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = reportInfo != null,
                onClick = { onClick(if (reportInfo!!.isSinglePublish) reportInfo.id else null) }
            )
            .padding(horizontal = 28.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .defaultMinSize(minWidth = 168.dp)
                    .placeholder(
                        visible = reportInfo == null,
                        color = Theme.colors.surface,
                        shape = Theme.shapes.small,
                        highlight = PlaceholderHighlight.shimmer(Theme.colors.background)
                    ),
                text = reportInfo?.name.orEmpty(),
                color = Theme.colors.onBackground,
                style = Theme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .defaultMinSize(minWidth = 84.dp)
                    .placeholder(
                        visible = reportInfo == null,
                        color = Theme.colors.surface,
                        shape = Theme.shapes.small,
                        highlight = PlaceholderHighlight.shimmer(Theme.colors.background)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.size(12.dp),
                    painter = painterResource(com.zhixue.lite.core.ui.R.drawable.ic_time),
                    tint = Theme.colors.onBackgroundVariant
                )
                Spacer(
                    modifier = Modifier.width(4.dp)
                )
                Text(
                    text = reportInfo?.date.orEmpty(),
                    color = Theme.colors.onBackgroundVariant,
                    style = Theme.typography.subtitleSmall,
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Image(
            modifier = Modifier.placeholder(
                visible = reportInfo == null,
                color = Theme.colors.surface,
                shape = Theme.shapes.small,
                highlight = PlaceholderHighlight.shimmer(Theme.colors.background)
            ),
            painter = painterResource(com.zhixue.lite.core.ui.R.drawable.ic_next),
            tint = Theme.colors.onBackgroundVariant
        )
    }
}