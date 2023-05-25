package com.zhixue.lite.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.zhixue.lite.core.ui.component.Image
import com.zhixue.lite.core.ui.component.Text
import com.zhixue.lite.core.ui.theme.Theme

@Composable
fun SwitchAccountDialog(
    credentials: Map<String, String>,
    onAccountClick: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(Theme.colors.background, Theme.shapes.small),
            contentPadding = PaddingValues(top = 28.dp, bottom = 16.dp),
        ) {
            item {
                Text(
                    modifier = Modifier.padding(horizontal = 28.dp),
                    text = stringResource(R.string.switch_account_title),
                    color = Theme.colors.onBackground,
                    style = Theme.typography.titleMedium
                )
            }
            item { Spacer(modifier = Modifier.height(12.dp)) }
            credentials.forEach { (username, password) ->
                item {
                    Row(
                        modifier = Modifier
                            .clickable {
                                onAccountClick(username, password)
                                onDismiss()
                            }
                            .padding(horizontal = 28.dp, vertical = 18.dp)
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = username,
                            color = Theme.colors.onBackground,
                            style = Theme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                        Image(
                            painter = painterResource(com.zhixue.lite.core.ui.R.drawable.ic_next),
                            tint = Theme.colors.onBackground
                        )
                    }
                }
            }
        }
    }
}