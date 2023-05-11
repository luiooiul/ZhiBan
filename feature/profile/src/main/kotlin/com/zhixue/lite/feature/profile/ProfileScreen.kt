package com.zhixue.lite.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhixue.lite.core.ui.component.HorizontalDivider
import com.zhixue.lite.core.ui.component.Image
import com.zhixue.lite.core.ui.component.Text
import com.zhixue.lite.core.ui.theme.Theme

@Composable
fun ProfileScreen(
    navigateToLogin: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        uiState = uiState,
        onLogoutClick = { viewModel.logout(onLogoutComplete = navigateToLogin) }
    )
}

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onLogoutClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 24.dp)
    ) {
        item { Spacer(modifier = Modifier) }
        item { Spacer(modifier = Modifier) }
        item { Spacer(modifier = Modifier) }
        item { ProfileHeader(name = uiState.name, schoolClass = uiState.schoolClass) }
        item { Spacer(modifier = Modifier) }
        item { HorizontalDivider() }
        item {
            ProfileSettingPanel(
                onLogoutClick = onLogoutClick
            )
        }
    }
}

@Composable
fun ProfileHeader(
    name: String,
    schoolClass: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .background(Theme.colors.surface, Theme.shapes.large)
                .padding(24.dp)
                .size(48.dp),
            painter = painterResource(com.zhixue.lite.core.ui.R.drawable.ic_logo)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = name,
            color = Theme.colors.onBackground,
            style = Theme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = schoolClass,
            color = Theme.colors.onBackgroundVariant,
            style = Theme.typography.subtitleSmall,
        )
    }
}

@Composable
fun ProfileSettingPanel(
    onLogoutClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column {
            Text(
                modifier = Modifier.padding(horizontal = 28.dp, vertical = 12.dp),
                text = stringResource(R.string.label_account),
                color = Theme.colors.onBackgroundVariant,
                style = Theme.typography.labelMedium
            )
            ProfileSettingItem(
                name = stringResource(R.string.setting_switch_account),
                onClick = {}
            )
            ProfileSettingItem(
                name = stringResource(R.string.setting_modify_password),
                onClick = {}
            )
        }
        HorizontalDivider(spacing = 24.dp)
        Column {
            ProfileSettingItem(
                name = stringResource(R.string.setting_check_update),
                onClick = {}
            )
            ProfileSettingItem(
                name = stringResource(R.string.setting_feedback),
                onClick = {}
            )
            ProfileSettingItem(
                name = stringResource(R.string.setting_about_us),
                onClick = {}
            )
        }
        HorizontalDivider(spacing = 24.dp)
        ProfileSettingItem(
            name = stringResource(R.string.setting_logout),
            onClick = onLogoutClick
        )
    }
}

@Composable
fun ProfileSettingItem(name: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 28.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = name,
            color = Theme.colors.onBackground,
            style = Theme.typography.titleSmall
        )
        Spacer(modifier = Modifier.width(24.dp))
        Image(
            painter = painterResource(com.zhixue.lite.core.ui.R.drawable.ic_next),
            tint = Theme.colors.onBackground
        )
    }
}