package com.zhixue.lite.feature.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.zhixue.lite.core.ui.component.Text
import com.zhixue.lite.core.ui.theme.Theme

private const val UPDATE_URI = "https://gitee.com/luiooiul/ZhiBan/releases/"

@Composable
fun CheckUpdateDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        LazyColumn(
            modifier = Modifier.background(Theme.colors.background, Theme.shapes.small),
            contentPadding = PaddingValues(top = 28.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    modifier = Modifier.padding(horizontal = 28.dp),
                    text = stringResource(R.string.check_update_title),
                    color = Theme.colors.onBackground,
                    style = Theme.typography.titleMedium
                )
            }
            item {
                Text(
                    modifier = Modifier.padding(horizontal = 28.dp),
                    text = stringResource(R.string.check_update_contents),
                    color = Theme.colors.onBackground,
                    style = Theme.typography.body,
                    singleLine = false
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        modifier = Modifier
                            .clip(Theme.shapes.small)
                            .clickable {
                                jumpToUpdate(context)
                                onDismiss()
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        text = stringResource(R.string.check_update_button_jump),
                        color = Theme.colors.primary,
                        style = Theme.typography.button
                    )
                }
            }
        }
    }
}

private fun jumpToUpdate(context: Context) {
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(UPDATE_URI)))
    } catch (e: Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }
}