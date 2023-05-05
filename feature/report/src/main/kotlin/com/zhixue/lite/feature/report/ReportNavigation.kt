package com.zhixue.lite.feature.report

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val REPORT_LIST_ROUTE = "report_list_route"

fun NavGraphBuilder.reportListScreen(
    navigateToReportDetail: (String) -> Unit
) {
    composable(
        route = REPORT_LIST_ROUTE
    ) {
        ReportListScreen(
            navigateToReportDetail = navigateToReportDetail
        )
    }
}