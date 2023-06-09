package com.zhixue.lite.feature.modify

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhixue.lite.core.ui.component.Image
import com.zhixue.lite.core.ui.component.LoadingIndicator
import com.zhixue.lite.core.ui.component.Text
import com.zhixue.lite.core.ui.component.TextField
import com.zhixue.lite.core.ui.theme.Theme

@Composable
fun ModifyScreen(
    onBackClick: () -> Unit,
    viewModel: ModifyViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val message = uiState.message

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.messageShown()
        }
    }

    ModifyScreen(
        isModifying = uiState.isModifying,
        originPassword = viewModel.originPassword,
        newPassword = viewModel.newPassword,
        onOriginPasswordChange = viewModel::updateOriginPassword,
        onNewPasswordChange = viewModel::updateNewPassword,
        onModifyClick = viewModel::doModify,
        onBackClick = onBackClick
    )
}

@Composable
fun ModifyScreen(
    isModifying: Boolean,
    originPassword: String,
    newPassword: String,
    onOriginPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onModifyClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 24.dp)
    ) {
        ModifyTopBar(
            onBackClick = onBackClick
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(28.dp, Alignment.CenterVertically)
        ) {
            item { ModifyHeadline() }
            item { Spacer(modifier = Modifier) }
            item { ModifyOriginPasswordInput(originPassword, onOriginPasswordChange) }
            item { ModifyNewPasswordInput(newPassword, onNewPasswordChange) }
            item { Spacer(modifier = Modifier) }
            item { ModifyButton(isModifying = isModifying, onClick = onModifyClick) }
            item { Spacer(modifier = Modifier) }
        }
    }
}

@Composable
fun ModifyTopBar(
    onBackClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 12.dp)
    ) {
        Image(
            modifier = Modifier
                .clip(CircleShape)
                .clickable(onClick = onBackClick)
                .padding(8.dp)
                .size(20.dp),
            painter = painterResource(com.zhixue.lite.core.ui.R.drawable.ic_back),
            tint = Theme.colors.onBackground
        )
    }
}

@Composable
fun ModifyHeadline() {
    Text(
        text = stringResource(R.string.headline_modify),
        color = Theme.colors.onBackground,
        style = Theme.typography.headline
    )
}

@Composable
fun ModifyOriginPasswordInput(
    originPassword: String,
    onOriginPasswordChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        Text(
            text = stringResource(R.string.label_origin_password),
            color = Theme.colors.onBackgroundVariant,
            style = Theme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.surface, Theme.shapes.small)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            value = originPassword,
            onValueChange = onOriginPasswordChange,
            color = Theme.colors.onSurface,
            style = Theme.typography.body,
            keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) },
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Composable
fun ModifyNewPasswordInput(
    newPassword: String,
    onNewPasswordChange: (String) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.label_new_password),
            color = Theme.colors.onBackgroundVariant,
            style = Theme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.surface, Theme.shapes.small)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            value = newPassword,
            onValueChange = onNewPasswordChange,
            color = Theme.colors.onSurface,
            style = Theme.typography.body,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Composable
fun ModifyButton(
    isModifying: Boolean,
    onClick: () -> Unit
) {
    Crossfade(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, Theme.shapes.small)
            .background(Theme.colors.primary),
        targetState = isModifying,
        animationSpec = tween(600)
    ) { isModifyingState ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = !isModifying, onClick = onClick)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isModifyingState) {
                LoadingIndicator(
                    modifier = Modifier.padding(3.5.dp),
                    color = Theme.colors.onPrimary
                )
            } else {
                Text(
                    text = stringResource(R.string.button_modify),
                    color = Theme.colors.onPrimary,
                    style = Theme.typography.button
                )
            }
        }
    }
}