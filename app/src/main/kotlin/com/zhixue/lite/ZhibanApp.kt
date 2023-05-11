package com.zhixue.lite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.zhixue.lite.core.ui.component.HorizontalDivider
import com.zhixue.lite.core.ui.theme.Theme
import com.zhixue.lite.feature.home.HOME_ROUTE
import com.zhixue.lite.feature.home.HomeBottomBar
import com.zhixue.lite.feature.home.homeGraph
import com.zhixue.lite.feature.login.LOGIN_ROUTE
import com.zhixue.lite.feature.login.loginScreen
import com.zhixue.lite.feature.report.reportDetailScreen
import com.zhixue.lite.feature.report.reportMainScreen

@Composable
fun ZhibanApp(
    loginState: LoginState,
    appState: ZhibanAppState = rememberAppState()
) {
    if (loginState != LoginState.Loading) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            NavHost(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                navController = appState.navController,
                startDestination = if (loginState == LoginState.LoggedIn) HOME_ROUTE else LOGIN_ROUTE
            ) {
                homeGraph(
                    navigateToLogin = appState::navigateToLogin,
                    navigateToReportMain = appState::navigateToReportMain
                )
                loginScreen(
                    navigateToHome = appState::navigateToHome
                )
                reportMainScreen(
                    onBackClick = appState::navigateBack,
                    navigateToReportDetail = appState::navigateToReportDetail
                )
                reportDetailScreen(
                    onBackClick = appState::navigateBack
                )
            }
            if (appState.shouldShowHomeBottomBar) {
                HorizontalDivider()
                HomeBottomBar(
                    currentDestination = appState.currentDestination,
                    onNavigateToDestination = appState::navigateToHomeDestination
                )
            }
        }
    }
}