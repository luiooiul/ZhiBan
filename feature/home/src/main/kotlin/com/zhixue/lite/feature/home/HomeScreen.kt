package com.zhixue.lite.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.zhixue.lite.core.ui.component.Image
import com.zhixue.lite.core.ui.theme.Theme

@Composable
fun HomeBottomBar(
    currentDestination: NavDestination?,
    onNavigateToDestination: (HomeDestination) -> Unit,
    destinations: Array<HomeDestination> = HomeDestination.values()
) {
    Row(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination?.route?.contains(destination.name, true) ?: false
            Image(
                modifier = Modifier
                    .weight(1f)
                    .clip(Theme.shapes.small)
                    .clickable { onNavigateToDestination(destination) }
                    .padding(8.dp),
                painter = painterResource(destination.iconRes),
                tint = if (selected) Theme.colors.primary else Theme.colors.onBackgroundVariant
            )
        }
    }
}