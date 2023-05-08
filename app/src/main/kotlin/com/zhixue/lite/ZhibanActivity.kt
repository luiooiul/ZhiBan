package com.zhixue.lite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.zhixue.lite.core.ui.theme.ZhibanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ZhibanActivity : ComponentActivity() {

    private val _viewModel by viewModels<ZhibanActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            _viewModel.loginState == LoginState.Loading
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = isSystemInDarkTheme()

            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
                onDispose {}
            }

            ZhibanTheme(
                darkTheme = darkTheme
            ) {
                ZhibanApp(
                    loginState = _viewModel.loginState
                )
            }
        }
    }
}