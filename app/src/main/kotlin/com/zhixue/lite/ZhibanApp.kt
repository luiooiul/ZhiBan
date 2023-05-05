package com.zhixue.lite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.zhixue.lite.core.ui.theme.Theme
import com.zhixue.lite.feature.home.HOME_ROUTE
import com.zhixue.lite.feature.home.homeGraph
import com.zhixue.lite.feature.login.LOGIN_ROUTE
import com.zhixue.lite.feature.login.loginScreen

@Composable
fun ZhibanApp(
    loginState: LoginState,
    appState: ZhibanAppState = rememberAppState()
) {
    if (loginState != LoginState.Loading) {
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colors.background)
                .statusBarsPadding()
                .navigationBarsPadding(),
            navController = appState.navController,
            startDestination = if (loginState == LoginState.LoggedIn) HOME_ROUTE else LOGIN_ROUTE
        ) {
            homeGraph()
            loginScreen(
                navigateToHome = appState::navigateToHome
            )
        }
    }
}