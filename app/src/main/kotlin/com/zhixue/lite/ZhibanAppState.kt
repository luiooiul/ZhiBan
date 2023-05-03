package com.zhixue.lite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

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
)