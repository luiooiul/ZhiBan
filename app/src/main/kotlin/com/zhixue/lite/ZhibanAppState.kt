package com.zhixue.lite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.zhixue.lite.feature.home.HOME_ROUTE
import com.zhixue.lite.feature.home.HomeDestination
import com.zhixue.lite.feature.home.navigateToHome
import com.zhixue.lite.feature.login.navigateToLogin
import com.zhixue.lite.feature.modify.navigateToModify
import com.zhixue.lite.feature.profile.navigateToProfile
import com.zhixue.lite.feature.report.navigateToReportDetail
import com.zhixue.lite.feature.report.navigateToReportList
import com.zhixue.lite.feature.report.navigateToReportMain

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): ZhibanAppState {
    return remember(navController) {
        ZhibanAppState(navController)
    }
}

class ZhibanAppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val shouldShowHomeBottomBar: Boolean
        @Composable get() = currentDestination?.parent?.route == HOME_ROUTE

    fun navigateBack() {
        navController.popBackStack()
    }

    fun navigateToHome() {
        val navOptions = navOptions {
            popUpTo(navController.graph.id) { inclusive = true }
        }

        navController.navigateToHome(navOptions)
    }

    fun navigateToLogin() {
        val navOptions = navOptions {
            launchSingleTop = true
            popUpTo(navController.graph.id) { inclusive = true }
        }

        navController.navigateToLogin(navOptions)
    }

    fun navigateToModify() {
        val navOptions = navOptions {
            launchSingleTop = true
        }

        navController.navigateToModify(navOptions)
    }

    fun navigateToReportMain(examId: String) {
        val navOptions = navOptions {
            launchSingleTop = true
        }

        navController.navigateToReportMain(examId, navOptions)
    }

    fun navigateToReportDetail(examId: String, paperId: String, name: String) {
        val navOptions = navOptions {
            launchSingleTop = true
        }

        navController.navigateToReportDetail(examId, paperId, name, navOptions)
    }

    fun navigateToHomeDestination(destination: HomeDestination) {
        val navOptions = navOptions {
            restoreState = true
            launchSingleTop = true
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }

        when (destination) {
            HomeDestination.REPORT_LIST -> navController.navigateToReportList(navOptions)
            HomeDestination.PROFILE -> navController.navigateToProfile(navOptions)
        }
    }
}