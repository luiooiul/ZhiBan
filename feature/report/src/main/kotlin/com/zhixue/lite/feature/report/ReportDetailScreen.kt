package com.zhixue.lite.feature.report

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.request.ImageRequest
import com.zhixue.lite.core.model.data.ReportDetail
import com.zhixue.lite.core.ui.component.AsyncImage
import com.zhixue.lite.core.ui.component.CircularChart
import com.zhixue.lite.core.ui.component.HorizontalDivider
import com.zhixue.lite.core.ui.component.Image
import com.zhixue.lite.core.ui.component.ProgressBar
import com.zhixue.lite.core.ui.component.Text
import com.zhixue.lite.core.ui.theme.Theme
import kotlin.math.absoluteValue

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
fun ReportDetailHeadline(
    name: String
) {
    Text(
        text = name,
        color = Theme.colors.onBackground,
        style = Theme.typography.headline
    )
}

@Composable
fun ReportDetailContent(
    reportDetail: ReportDetail
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Theme.colors.outline, Theme.shapes.medium)
            .padding(vertical = 36.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        ReportDetailTotalPanel(total = reportDetail.total)
        HorizontalDivider()
        ReportDetailOverviewPanel(overview = reportDetail.overview)
        HorizontalDivider()
        ReportDetailCheckSheetPanel(checkSheets = reportDetail.checkSheets)
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
                text = stringResource(R.string.label_score),
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
fun ReportDetailOverviewPanel(
    overview: ReportDetail.Overview
) {
    Column(
        modifier = Modifier.padding(horizontal = 28.dp)
    ) {
        Text(
            text = stringResource(R.string.label_overview),
            color = Theme.colors.onBackgroundVariant,
            style = Theme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(22.dp))
        ReportDetailOverviewTypePanel(type = overview.type)
        Spacer(modifier = Modifier.height(28.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(28.dp))
        ReportDetailOverviewAnswerPanel(answer = overview.answer)
    }
}

@Composable
fun ReportDetailOverviewTypePanel(
    type: ReportDetail.Overview.Type
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        ReportDetailOverviewTypeItem(
            name = stringResource(R.string.text_subjective),
            info = type.subjective
        )
        Spacer(modifier = Modifier.height(24.dp))
        ReportDetailOverviewTypeItem(
            name = stringResource(R.string.text_objective),
            info = type.objective
        )
    }
}

@Composable
fun ReportDetailOverviewTypeItem(
    name: String,
    info: ReportDetail.Overview.Type.Info
) {
    Column {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = name,
                color = Theme.colors.onBackground,
                style = Theme.typography.titleSmall
            )
            Text(
                text = "${info.score} / ${info.standardScore}",
                color = Theme.colors.onBackgroundVariant,
                style = Theme.typography.subtitleSmall
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        ProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            value = info.rate
        )
    }
}


@Composable
fun ReportDetailOverviewAnswerPanel(
    answer: ReportDetail.Overview.Answer
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ReportDetailOverviewAnswerItem(
            size = answer.correct,
            name = stringResource(R.string.text_correct),
            color = Theme.colors.primary
        )
        ReportDetailOverviewAnswerItem(
            size = answer.partCorrect,
            name = stringResource(R.string.text_part_correct),
            color = Theme.colors.part
        )
        ReportDetailOverviewAnswerItem(
            size = answer.incorrect,
            name = stringResource(R.string.text_incorrect),
            color = Theme.colors.error
        )
    }
}

@Composable
fun ReportDetailOverviewAnswerItem(
    size: Int,
    name: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            color = color,
            style = Theme.typography.titleSmall
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(R.string.text_topics, size),
            color = Theme.colors.onBackgroundVariant,
            style = Theme.typography.subtitleSmall
        )
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ReportDetailCheckSheetPanel(
    checkSheets: List<ReportDetail.CheckSheet>
) {
    val textMeasurer = rememberTextMeasurer()

    Column(
        modifier = Modifier.padding(horizontal = 28.dp)
    ) {
        Text(
            text = stringResource(R.string.label_check_sheet),
            color = Theme.colors.onBackgroundVariant,
            style = Theme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(22.dp))
        Column(
            modifier = Modifier
                .clip(Theme.shapes.small)
                .border(1.dp, Theme.colors.outline, Theme.shapes.small)
        ) {
            checkSheets.forEach { checkSheet ->
                ReportDetailCheckSheet(
                    checkSheet = checkSheet,
                    textMeasurer = textMeasurer
                )
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ReportDetailCheckSheet(
    checkSheet: ReportDetail.CheckSheet,
    textMeasurer: TextMeasurer
) {
    var isShowFullSheet by rememberSaveable { mutableStateOf(false) }

    ReportDetailCheckSheetImage(
        modifier = Modifier.clickable { isShowFullSheet = true },
        textMeasurer = textMeasurer,
        checkSheet = checkSheet
    )

    if (isShowFullSheet) {
        var scale by remember { mutableStateOf(1f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        var sheetSize = Size.Zero

        val state = rememberTransformableState { scaleChange, offsetChange, _ ->
            val changedOffset = offset + offsetChange

            val px = sheetSize.width * (scale - 1f) / 2f
            val py = sheetSize.height * (scale - 1f) / 2f

            scale = (scale * scaleChange).coerceIn(1f, 4f)
            offset = Offset(
                x = if (changedOffset.x.absoluteValue - px > 0) px * changedOffset.x.absoluteValue / changedOffset.x else changedOffset.x,
                y = if (changedOffset.y.absoluteValue - py > 0) py * changedOffset.y.absoluteValue / changedOffset.y else changedOffset.y
            )
        }

        Dialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = { isShowFullSheet = false }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .transformable(state)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { isShowFullSheet = false }
                    ),
                contentAlignment = Alignment.Center
            ) {
                ReportDetailCheckSheetImage(
                    modifier = Modifier.graphicsLayer {
                        sheetSize = size
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    },
                    textMeasurer = textMeasurer,
                    checkSheet = checkSheet
                )
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun ReportDetailCheckSheetImage(
    modifier: Modifier,
    textMeasurer: TextMeasurer,
    checkSheet: ReportDetail.CheckSheet
) {
    val (sheetUrl, currentSize, sections) = checkSheet

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(sheetUrl)
            .size(coil.size.Size.ORIGINAL)
            .diskCacheKey(sheetUrl.substringBefore("?"))
            .memoryCacheKey(sheetUrl.substringBefore("?"))
            .build(),
        modifier = modifier.drawWithContent {
            drawContent()
            val widthScale = size.width / currentSize.first
            val heightScale = size.height / currentSize.second
            sections.forEach { section ->
                val result = textMeasurer.measure(
                    text = "${section.score}/${section.standardScore}",
                    style = TextStyle(Color.Red, 6.sp, FontWeight.Medium)
                )
                drawText(
                    textLayoutResult = result,
                    topLeft = Offset(
                        x = (section.x + section.width) * widthScale - result.size.width,
                        y = section.y * heightScale
                    )
                )
            }
        },
        colorFilter = if (Theme.colors.isLight) {
            null
        } else {
            ColorFilter.colorMatrix(
                ColorMatrix(
                    floatArrayOf(
                        -1f, 0f, 0f, 0f, 255f,
                        0f, -1f, 0f, 0f, 255f,
                        0f, 0f, -1f, 0f, 255f,
                        0f, 0f, 0f, 1f, 0f
                    )
                )
            )
        }
    )
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