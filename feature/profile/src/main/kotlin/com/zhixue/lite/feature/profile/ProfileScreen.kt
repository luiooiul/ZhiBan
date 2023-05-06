package com.zhixue.lite.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhixue.lite.core.ui.component.HorizontalDivider
import com.zhixue.lite.core.ui.component.Image
import com.zhixue.lite.core.ui.component.Text
import com.zhixue.lite.core.ui.theme.Theme

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreen(
        uiState = uiState
    )
}

@Composable
fun ProfileScreen(
    uiState: ProfileUiState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        item { Spacer(modifier = Modifier) }
        item { Spacer(modifier = Modifier) }
        item { ProfileHeader(name = uiState.name, schoolClass = uiState.schoolClass) }
        item { HorizontalDivider(spacing = 36.dp) }
    }
}

@Composable
fun ProfileHeader(
    name: String,
    schoolClass: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .background(Theme.colors.surface, CircleShape)
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
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = schoolClass,
            color = Theme.colors.onBackgroundVariant,
            style = Theme.typography.subtitleSmall,
        )
    }
}