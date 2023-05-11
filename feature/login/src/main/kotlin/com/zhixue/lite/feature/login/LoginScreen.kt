package com.zhixue.lite.feature.login

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun LoginScreen(
    navigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
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

    LoginScreen(
        username = viewModel.username,
        password = viewModel.password,
        isLogging = uiState.isLogging,
        onUsernameChange = viewModel::updateUsername,
        onPasswordChange = viewModel::updatePassword,
        onLoginClick = { viewModel.doLogin(onLoginCompleted = navigateToHome) }
    )
}

@Composable
fun LoginScreen(
    username: String,
    password: String,
    isLogging: Boolean,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(28.dp, Alignment.CenterVertically),
        contentPadding = PaddingValues(36.dp)
    ) {
        item { LoginLogo() }
        item { LoginHeadline() }
        item { LoginUsernameInput(username, onUsernameChange) }
        item { LoginPasswordInput(password, onPasswordChange) }
        item { Spacer(modifier = Modifier) }
        item { LoginButton(isLogging, onLoginClick) }
        item { Spacer(modifier = Modifier) }
    }
}

@Composable
fun LoginLogo() {
    Box(
        modifier = Modifier
            .background(Theme.colors.surface, Theme.shapes.large)
            .padding(16.dp)
    ) {
        Image(
            modifier = Modifier.size(36.dp),
            painter = painterResource(com.zhixue.lite.core.ui.R.drawable.ic_logo)
        )
    }
}

@Composable
fun LoginHeadline() {
    Text(
        text = stringResource(R.string.headline_login),
        color = Theme.colors.onBackground,
        style = Theme.typography.headline
    )
}

@Composable
fun LoginUsernameInput(
    username: String,
    onUsernameChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        Text(
            text = stringResource(R.string.label_username),
            color = Theme.colors.onBackgroundVariant,
            style = Theme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.surface, Theme.shapes.small)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            value = username,
            onValueChange = onUsernameChange,
            color = Theme.colors.onSurface,
            style = Theme.typography.body,
            keyboardActions = KeyboardActions { focusManager.moveFocus(FocusDirection.Down) }
        )
    }
}

@Composable
fun LoginPasswordInput(
    password: String,
    onPasswordChange: (String) -> Unit
) {
    Column {
        Text(
            text = stringResource(R.string.label_password),
            color = Theme.colors.onBackgroundVariant,
            style = Theme.typography.labelMedium
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.surface, Theme.shapes.small)
                .padding(horizontal = 24.dp, vertical = 20.dp),
            value = password,
            onValueChange = onPasswordChange,
            color = Theme.colors.onSurface,
            style = Theme.typography.body,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation()
        )
    }
}

@Composable
fun LoginButton(
    isLogging: Boolean,
    onClick: () -> Unit
) {
    Crossfade(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp, Theme.shapes.small)
            .background(Theme.colors.primary),
        targetState = isLogging,
        animationSpec = tween(600)
    ) { isLoggingState ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(enabled = !isLogging, onClick = onClick)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (isLoggingState) {
                LoadingIndicator(
                    modifier = Modifier.padding(3.5.dp),
                    color = Theme.colors.onPrimary
                )
            } else {
                Text(
                    text = stringResource(R.string.button_login),
                    color = Theme.colors.onPrimary,
                    style = Theme.typography.button
                )
            }
        }
    }
}