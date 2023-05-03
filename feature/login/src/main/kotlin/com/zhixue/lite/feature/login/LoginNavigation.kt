package com.zhixue.lite.feature.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val LOGIN_ROUTE = "login_route"

fun NavGraphBuilder.loginScreen() {
    composable(
        route = LOGIN_ROUTE
    ) {
        LoginScreen()
    }
}