package com.zhixue.lite.feature.report

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val REPORT_LIST_ROUTE = "report_list_route"

fun NavController.navigateToReportList(navOptions: NavOptions) {
    this.navigate(REPORT_LIST_ROUTE, navOptions)
}

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