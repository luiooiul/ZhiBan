package com.zhixue.lite.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.zhixue.lite.feature.report.REPORT_LIST_ROUTE
import com.zhixue.lite.feature.report.reportListScreen

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome() {
    navigate(HOME_ROUTE) {
        popUpTo(graph.id) { inclusive = true }
    }
}

fun NavGraphBuilder.homeGraph(
    navigateToReportDetail: (String) -> Unit
) {
    navigation(
        route = HOME_ROUTE,
        startDestination = REPORT_LIST_ROUTE
    ) {
        reportListScreen(
            navigateToReportDetail = navigateToReportDetail
        )
    }
}