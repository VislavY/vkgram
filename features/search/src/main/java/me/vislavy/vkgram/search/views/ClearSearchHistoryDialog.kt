package me.vislavy.vkgram.search.views

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.search.R

@Composable
fun ClearSearchHistoryDialog(
    onDismiss: () -> Unit,
    onDismissButtonClick: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.search_clear_history),
                color = VKgramTheme.palette.onSurface,
                style = VKgramTheme.typography.subtitle1
            )
        },
        text = {
            Text(
                text = stringResource(R.string.search_are_you_sure),
                color = VKgramTheme.palette.onSurface,
                style = VKgramTheme.typography.body1
            )
        },
        dismissButton = {
            TextButton(onDismissButtonClick) {
                Text(
                    text = stringResource(R.string.search_no).uppercase(),
                    color = VKgramTheme.palette.primary,
                    style = VKgramTheme.typography.button
                )
            }
        },
        confirmButton = {
            TextButton(onConfirmButtonClick) {
                Text(
                    text = stringResource(R.string.search_clear).uppercase(),
                    color = VKgramTheme.palette.error,
                    style = VKgramTheme.typography.button
                )
            }
        }
    )
}