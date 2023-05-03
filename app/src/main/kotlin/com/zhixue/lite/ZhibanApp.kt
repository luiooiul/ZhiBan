package com.zhixue.lite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.zhixue.lite.core.ui.theme.Theme
import com.zhixue.lite.feature.login.LOGIN_ROUTE
import com.zhixue.lite.feature.login.loginScreen

@Composable
fun ZhibanApp(
    appState: ZhibanAppState = rememberAppState()
) {
    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colors.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
        navController = appState.navController,
        startDestination = LOGIN_ROUTE
    ) {
        loginScreen()
    }
}