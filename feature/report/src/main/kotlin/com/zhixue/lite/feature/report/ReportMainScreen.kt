package com.zhixue.lite.feature.report

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhixue.lite.core.model.data.ReportMain
import com.zhixue.lite.core.ui.component.CircularChart
import com.zhixue.lite.core.ui.component.HorizontalDivider
import com.zhixue.lite.core.ui.component.Image
import com.zhixue.lite.core.ui.component.ProgressBar
import com.zhixue.lite.core.ui.component.Text
import com.zhixue.lite.core.ui.theme.Theme

@Composable
fun ReportMainScreen(
    onBackClick: () -> Unit,
    viewModel: ReportMainViewModel = hiltViewModel()
) {
    ReportMainScreen(
        uiState = viewModel.uiState,
        onBackClick = onBackClick
    )
}

@Composable
fun ReportMainScreen(
    uiState: ReportMainUiState,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        ReportMainTopBar(
            onBackClick = onBackClick
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { ReportMainHeadline() }
            item {
                Crossfade(uiState) {
                    when (it) {
                        is ReportMainUiState.Success -> ReportMainContent(reportMain = it.reportMain)
                        is ReportMainUiState.Error -> ReportMainErrorPanel()
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun ReportMainTopBar(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 12.dp)
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = onBackClick)
                .padding(8.dp)
                .size(20.dp),
            painter = painterResource(com.zhixue.lite.core.ui.R.drawable.ic_back),
            tint = Theme.colors.onBackground
        )
    }
}

@Composable
fun ReportMainHeadline() {
    Text(
        text = stringResource(R.string.headline_report_main),
        color = Theme.colors.onBackground,
        style = Theme.typography.headline
    )
}

@Composable
fun ReportMainContent(
    reportMain: ReportMain
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Theme.colors.outline, Theme.shapes.medium)
            .padding(horizontal = 28.dp, vertical = 36.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        ReportMainTotalPanel(total = reportMain.total)
        HorizontalDivider()
        ReportMainOverviewPanel(overviews = reportMain.overviews)
        HorizontalDivider()
        ReportMainTrendPanel(trends = reportMain.trends)
    }
}

@Composable
fun ReportMainTotalPanel(
    total: ReportMain.Total
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.label_total),
                color = Theme.colors.onBackgroundVariant,
                style = Theme.typography.label
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(Theme.typography.headline.toSpanStyle()) {
                        append(total.score)
                    }
                    withStyle(Theme.typography.titleSmall.toSpanStyle()) {
                        append(" / ${total.standardScore}")
                    }
                },
                color = Theme.colors.onBackground
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        CircularChart(
            value = total.scale,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun ReportMainOverviewPanel(
    overviews: List<ReportMain.Overview>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(R.string.label_overview),
            color = Theme.colors.onBackgroundVariant,
            style = Theme.typography.label
        )
        overviews.forEach { overview ->
            ReportMainOverviewItem(overview)
        }
    }
}

@Composable
fun ReportMainOverviewItem(
    overview: ReportMain.Overview
) {
    Column {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = overview.name,
                color = Theme.colors.onBackground,
                style = Theme.typography.titleSmall
            )
            Text(
                text = "${overview.score} / ${overview.standardScore}",
                color = Theme.colors.onBackgroundVariant,
                style = Theme.typography.subtitleSmall
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        ProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            value = overview.scale
        )
    }
}

@Composable
fun ReportMainTrendPanel(
    trends: List<ReportMain.Trend>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(R.string.label_trend),
            color = Theme.colors.onBackgroundVariant,
            style = Theme.typography.label
        )
        trends.forEach { trend ->
            ReportMainTrendItem(trend)
        }
    }
}

@Composable
fun ReportMainTrendItem(
    trend: ReportMain.Trend
) {
    val colors = when (trend.code) {
        "fastUp", "slowUp" -> Theme.colors.primary to Theme.colors.onPrimary
        "fastDown", "slowDown" -> Theme.colors.error to Theme.colors.onError
        else -> Theme.colors.surface to Theme.colors.onSurface
    }
    val iconRes = when (trend.code) {
        "fastUp", "slowUp" -> com.zhixue.lite.core.ui.R.drawable.ic_trending_up
        "fastDown", "slowDown" -> com.zhixue.lite.core.ui.R.drawable.ic_trending_down
        else -> com.zhixue.lite.core.ui.R.drawable.ic_trending_flat
    }
    val labelRes = when (trend.code) {
        "fastUp" -> R.string.label_fast_up
        "slowUp" -> R.string.label_slow_up
        "fastDown" -> R.string.label_fast_down
        "slowDown" -> R.string.label_slow_down
        "steady" -> R.string.label_steady
        else -> R.string.label_no_trend
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = trend.name,
                color = Theme.colors.onBackground,
                style = Theme.typography.titleSmall
            )
            trend.rank?.let { rank ->
                Text(
                    text = stringResource(R.string.text_rank, rank),
                    color = Theme.colors.onBackgroundVariant,
                    style = Theme.typography.subtitleSmall
                )
            }
        }
        Row(
            modifier = Modifier
                .background(colors.first, Theme.shapes.small)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(iconRes),
                tint = colors.second
            )
            Spacer(
                modifier = Modifier.width(4.dp)
            )
            Text(
                text = stringResource(labelRes),
                color = colors.second,
                style = Theme.typography.labelSmall
            )
        }
    }
}

@Composable
fun ReportMainErrorPanel() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.surface, Theme.shapes.medium)
            .padding(horizontal = 28.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(36.dp),
            painter = painterResource(com.zhixue.lite.core.ui.R.drawable.ic_error),
            tint = Theme.colors.error
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(R.string.text_error),
            color = Theme.colors.error,
            style = Theme.typography.titleMedium
        )
    }
}