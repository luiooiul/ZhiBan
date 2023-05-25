package com.zhixue.lite.feature.modify

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val MODIFY_ROUTE = "modify_route"

fun NavController.navigateToModify(navOptions: NavOptions? = null) {
    this.navigate(MODIFY_ROUTE, navOptions)
}

fun NavGraphBuilder.modifyScreen(
    onBackClick: () -> Unit
) {
    composable(route = MODIFY_ROUTE) {
        ModifyScreen(
            onBackClick = onBackClick
        )
    }
}