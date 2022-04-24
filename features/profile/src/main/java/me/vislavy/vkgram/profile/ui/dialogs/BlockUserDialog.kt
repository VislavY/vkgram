package me.vislavy.vkgram.profile.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.theme.defaults.VKgramButtonDefaults
import me.vislavy.vkgram.profile.R
import me.vislavy.vkgram.profile.models.ProfileEvent

@Composable
fun BlockUserDialog(
    modifier: Modifier = Modifier,
    visible: MutableState<Boolean>,
    userName: String,
    onEvent: (ProfileEvent) -> Unit
) {
    if (visible.value) {
        AlertDialog(
            modifier = modifier,
            containerColor = VKgramTheme.palette.surfaceAtPrimary,
            titleContentColor = VKgramTheme.palette.onSurface,
            textContentColor = VKgramTheme.palette.onSurfaceVariant,
            title = {
                Text(
                    text = stringResource(R.string.block_dialog_title),
                    style = VKgramTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.block_dialog_body, userName),
                    style = VKgramTheme.typography.bodyMedium
                )
            },
            dismissButton = {
                TextButton(
                    colors = VKgramButtonDefaults.textButtonColors(),
                    onClick = {
                        visible.value = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.block_dialog_dissmis),
                        style = VKgramTheme.typography.labelLarge
                    )
                }
            },
            confirmButton = {
                TextButton(
                    colors = VKgramButtonDefaults.textButtonColors(),
                    onClick = {
                        onEvent(ProfileEvent.BlockUser)
                        visible.value = false
                    }
                ) {
                    Text(
                        text = stringResource(R.string.block_dialog_confirm),
                        style = VKgramTheme.typography.labelLarge
                    )
                }
            },
            onDismissRequest = {
                visible.value = false
            },
        )
    }
}