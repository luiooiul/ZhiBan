package com.zhixue.lite.feature.report

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val REPORT_LIST_ROUTE = "report_list_route"

fun NavController.navigateToReportList(navOptions: NavOptions? = null) {
    this.navigate(REPORT_LIST_ROUTE, navOptions)
}

fun NavGraphBuilder.reportListScreen(
    navigateToReportMain: (String) -> Unit
) {
    composable(route = REPORT_LIST_ROUTE) {
        ReportListScreen(
            navigateToReportMain = navigateToReportMain
        )
    }
}

const val REPORT_MAIN_ROUTE = "report_main_route?reportId={reportId}"

fun NavController.navigateToReportMain(reportId: String, navOptions: NavOptions? = null) {
    this.navigate(createReportMainRoute(reportId), navOptions)
}

fun NavGraphBuilder.reportMainScreen(
    onBackClick: () -> Unit
) {
    composable(REPORT_MAIN_ROUTE) {
        ReportMainScreen(
            onBackClick = onBackClick
        )
    }
}

private fun createReportMainRoute(reportId: String): String {
    return "report_main_route?reportId=$reportId"
}