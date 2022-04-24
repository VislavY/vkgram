package me.vislavy.vkgram.profile.ui.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.profile.R
import me.vislavy.vkgram.profile.models.ProfileEvent

@Composable
fun UnfollowDialog(
    visible: MutableState<Boolean>,
    userName: String,
    onEvent: (ProfileEvent) -> Unit
) {
    if (visible.value) {
        AlertDialog(
            containerColor = VKgramTheme.palette.surfaceAtPrimary,
            titleContentColor = VKgramTheme.palette.onSurface,
            textContentColor = VKgramTheme.palette.onSurfaceVariant,
            title = {
                Text(
                    text = stringResource(R.string.profile_unsubscribe_dialog_title),
                    style = VKgramTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.profile_unsubscribe_dialog_body, userName),
                    style = VKgramTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEvent(ProfileEvent.UnfollowOrUnfriend)
                        visible.value = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = VKgramTheme.palette.primary)
                ) {
                    Text(
                        text = stringResource(R.string.profile_unsubscribe_dialog_confirm),
                        style = VKgramTheme.typography.labelLarge
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        visible.value = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = VKgramTheme.palette.primary)
                ) {
                    Text(
                        text = stringResource(R.string.profile_unsubscribe_dialog_dismiss),
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