package com.zhixue.lite.feature.report

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

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

const val REPORT_MAIN_ROUTE = "report_main_route?examId={examId}"

fun NavController.navigateToReportMain(examId: String, navOptions: NavOptions? = null) {
    this.navigate(createReportMainRoute(examId), navOptions)
}

fun NavGraphBuilder.reportMainScreen(
    onBackClick: () -> Unit,
    navigateToReportDetail: (String, String, String) -> Unit
) {
    composable(
        route = REPORT_MAIN_ROUTE,
        arguments = listOf(navArgument("examId") { type = NavType.StringType })
    ) { backStackEntry ->
        val examId = checkNotNull(backStackEntry.arguments?.getString("examId"))
        ReportMainScreen(
            onBackClick = onBackClick,
            navigateToReportDetail = { paperId, name ->
                navigateToReportDetail(examId, paperId, name)
            }
        )
    }
}

private fun createReportMainRoute(examId: String): String {
    return "report_main_route?examId=$examId"
}

const val REPORT_DETAIL_ROUTE = "report_detail_route?examId={examId}&paperId={paperId}&name={name}"

fun NavController.navigateToReportDetail(
    examId: String,
    paperId: String,
    name: String,
    navOptions: NavOptions? = null
) {
    this.navigate(createReportDetailRoute(examId, paperId, name), navOptions)
}

fun NavGraphBuilder.reportDetailScreen(
    onBackClick: () -> Unit
) {
    composable(
        route = REPORT_DETAIL_ROUTE,
        arguments = listOf(navArgument("name") { type = NavType.StringType })
    ) { backStackEntry ->
        val name = checkNotNull(backStackEntry.arguments?.getString("name"))
        ReportDetailScreen(
            name = name,
            onBackClick = onBackClick
        )
    }
}

private fun createReportDetailRoute(examId: String, paperId: String, name: String): String {
    return "report_detail_route?examId=$examId&paperId=$paperId&name=$name"
}