package com.zhixue.lite.feature.home

import androidx.annotation.DrawableRes
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.zhixue.lite.feature.profile.profileScreen
import com.zhixue.lite.feature.report.REPORT_LIST_ROUTE
import com.zhixue.lite.feature.report.reportListScreen

const val HOME_ROUTE = "home_route"

enum class HomeDestination(
    @DrawableRes val iconRes: Int
) {
    REPORT_LIST(com.zhixue.lite.core.ui.R.drawable.ic_report),
    PROFILE(com.zhixue.lite.core.ui.R.drawable.ic_profile)
}

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HOME_ROUTE, navOptions)
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
        profileScreen()
    }
}