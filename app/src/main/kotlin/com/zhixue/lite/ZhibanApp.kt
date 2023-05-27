package com.zhixue.lite

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.NavHost
import com.zhixue.lite.core.ui.component.HorizontalDivider
import com.zhixue.lite.core.ui.component.Text
import com.zhixue.lite.core.ui.theme.Theme
import com.zhixue.lite.feature.home.HOME_ROUTE
import com.zhixue.lite.feature.home.HomeBottomBar
import com.zhixue.lite.feature.home.homeGraph
import com.zhixue.lite.feature.login.LOGIN_ROUTE
import com.zhixue.lite.feature.login.loginScreen
import com.zhixue.lite.feature.modify.modifyScreen
import com.zhixue.lite.feature.report.reportDetailScreen
import com.zhixue.lite.feature.report.reportMainScreen

@Composable
fun ZhibanApp(
    loginState: LoginState,
    onRetryClick: () -> Unit,
    onOfflineClick: () -> Unit,
    appState: ZhibanAppState = rememberAppState()
) {
    when (loginState) {
        LoginState.LoggedIn, LoginState.NotLoggedIn -> {
            Box(
                modifier = Modifier
                    .background(Theme.colors.background)
                    .statusBarsPadding()
                    .navigationBarsPadding()
            ) {
                NavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = appState.navController,
                    startDestination = if (loginState == LoginState.LoggedIn) HOME_ROUTE else LOGIN_ROUTE
                ) {
                    homeGraph(
                        navigateToLogin = appState::navigateToLogin,
                        navigateToModify = appState::navigateToModify,
                        navigateToReportMain = appState::navigateToReportMain
                    )
                    loginScreen(
                        navigateToHome = appState::navigateToHome
                    )
                    modifyScreen(
                        onBackClick = appState::navigateBack
                    )
                    reportMainScreen(
                        onBackClick = appState::navigateBack,
                        navigateToReportDetail = appState::navigateToReportDetail
                    )
                    reportDetailScreen(
                        onBackClick = appState::navigateBack
                    )
                }
                Crossfade(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    targetState = appState.shouldShowHomeBottomBar
                ) { shouldShowHomeBottomBar ->
                    if (shouldShowHomeBottomBar) {
                        HorizontalDivider()
                        HomeBottomBar(
                            currentDestination = appState.currentDestination,
                            onNavigateToDestination = appState::navigateToHomeDestination
                        )
                    }
                }
            }
        }

        LoginState.LoggedInError -> {
            Dialog(onDismissRequest = {}) {
                LazyColumn(
                    modifier = Modifier.background(Theme.colors.background, Theme.shapes.small),
                    contentPadding = PaddingValues(top = 28.dp, bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            modifier = Modifier.padding(horizontal = 28.dp),
                            text = stringResource(R.string.text_login_error),
                            color = Theme.colors.onBackground,
                            style = Theme.typography.titleMedium
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                modifier = Modifier
                                    .clip(Theme.shapes.small)
                                    .clickable(onClick = onRetryClick)
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                text = stringResource(R.string.button_retry),
                                color = Theme.colors.primary,
                                style = Theme.typography.button
                            )
                            Text(
                                modifier = Modifier
                                    .clip(Theme.shapes.small)
                                    .clickable(onClick = onOfflineClick)
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                text = stringResource(R.string.button_offline),
                                color = Theme.colors.primary,
                                style = Theme.typography.button
                            )
                        }
                    }
                }
            }
        }

        LoginState.Loading -> {}
    }
}