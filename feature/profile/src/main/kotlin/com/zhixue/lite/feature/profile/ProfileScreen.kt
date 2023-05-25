package com.zhixue.lite.feature.profile

import android.app.Activity
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
    navigateToModify: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var showFeedbackDialog by rememberSaveable { mutableStateOf(false) }
    var showCheckUpdateDialog by rememberSaveable { mutableStateOf(false) }
    var showSwitchAccountDialog by rememberSaveable { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val message = uiState.message

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.messageShown()
        }
    }

    ProfileScreen(
        uiState = uiState,
        onSettingItemClick = {
            when (it) {
                1 -> showSwitchAccountDialog = true
                2 -> navigateToModify()
                3 -> viewModel.checkUpdate(
                    versionCode = context.packageManager.getPackageInfo(
                        context.packageName, 0
                    ).versionCode,
                    onNewVersionAvailable = {
                        showCheckUpdateDialog = true
                    }
                )

                4 -> showFeedbackDialog = true
                5 -> viewModel.clearCache()
                6 -> viewModel.logout(onLogoutComplete = navigateToLogin)
            }
        }
    )

    if (showFeedbackDialog) {
        FeedbackDialog(
            onDismiss = { showFeedbackDialog = false }
        )
    }

    if (showCheckUpdateDialog) {
        CheckUpdateDialog(
            onDismiss = { showCheckUpdateDialog = false }
        )
    }

    if (showSwitchAccountDialog) {
        SwitchAccountDialog(
            credentials = uiState.credentials,
            onAccountClick = { username, password ->
                viewModel.switchAccount(
                    username = username,
                    password = password,
                    onSwitchAccountCompleted = {
                        with(context as Activity) {
                            finish()
                            startActivity(intent)
                        }
                    }
                )
            },
            onDismiss = { showSwitchAccountDialog = false }
        )
    }
}

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onSettingItemClick: (Int) -> Unit
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
        item { ProfileSettingPanel(onSettingItemClick = onSettingItemClick) }
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
    onSettingItemClick: (Int) -> Unit
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
                onClick = { onSettingItemClick(1) }
            )
            ProfileSettingItem(
                name = stringResource(R.string.setting_modify_password),
                onClick = { onSettingItemClick(2) }
            )
        }
        HorizontalDivider(spacing = 24.dp)
        Column {
            ProfileSettingItem(
                name = stringResource(R.string.setting_check_update),
                onClick = { onSettingItemClick(3) }
            )
            ProfileSettingItem(
                name = stringResource(R.string.setting_feedback),
                onClick = { onSettingItemClick(4) }
            )
            ProfileSettingItem(
                name = stringResource(R.string.setting_clear_cache),
                onClick = { onSettingItemClick(5) }
            )
        }
        HorizontalDivider(spacing = 24.dp)
        ProfileSettingItem(
            name = stringResource(R.string.setting_logout),
            onClick = { onSettingItemClick(6) }
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