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
import com.zhixue.lite.core.model.data.ReportDetail
import com.zhixue.lite.core.ui.component.CircularChart
import com.zhixue.lite.core.ui.component.HorizontalDivider
import com.zhixue.lite.core.ui.component.Image
import com.zhixue.lite.core.ui.component.Text
import com.zhixue.lite.core.ui.theme.Theme

@Composable
fun ReportDetailScreen(
    name: String,
    onBackClick: () -> Unit,
    viewModel: ReportDetailViewModel = hiltViewModel()
) {
    ReportDetailScreen(
        name = name,
        uiState = viewModel.uiState,
        onBackClick = onBackClick
    )
}

@Composable
fun ReportDetailScreen(
    name: String,
    uiState: ReportDetailUiState,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        ReportDetailTopBar(
            onBackClick = onBackClick
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            item { ReportDetailHeadline(name = name) }
            item {
                Crossfade(uiState) {
                    when (it) {
                        is ReportDetailUiState.Success -> ReportDetailContent(
                            reportDetail = it.reportDetail
                        )

                        is ReportDetailUiState.Error -> ReportDetailErrorPanel()
                        else -> {}
                    }
                }
            }
        }
    }
}

@Composable
fun ReportDetailTopBar(
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
fun ReportDetailHeadline(name: String) {
    Text(
        text = name,
        color = Theme.colors.onBackground,
        style = Theme.typography.headline
    )
}

@Composable
fun ReportDetailContent(reportDetail: ReportDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Theme.colors.outline, Theme.shapes.medium)
            .padding(vertical = 36.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        ReportDetailTotalPanel(total = reportDetail.total)
        HorizontalDivider()
    }
}

@Composable
fun ReportDetailTotalPanel(
    total: ReportDetail.Total
) {
    Row(
        modifier = Modifier.padding(horizontal = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.label_raw_score),
                color = Theme.colors.onBackgroundVariant,
                style = Theme.typography.labelMedium
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
            value = total.rate,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun ReportDetailErrorPanel() {
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