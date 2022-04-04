package me.vislavy.vkgram.profile.views.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import me.vislavy.vkgram.api.data.User
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.profile.R

@Composable
fun ProfileUnsubscribeDialog(
    isVisibleState: MutableState<Boolean>,
    model: User,
    onConfirm: () -> Unit
) {
    if (isVisibleState.value) {
        AlertDialog(
            onDismissRequest = {
                isVisibleState.value = false
            },
            icon = {
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    painter = rememberImagePainter(model.photo),
                    contentDescription = stringResource(R.string.profile_photo)
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.profile_unsubscribe_dialog_title),
                    style = VKgramTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = stringResource(
                        id = R.string.profile_unsubscribe_dialog_body,
                        "${model.firstName} ${model.lastName}"
                    ),
                    style = VKgramTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm()
                        isVisibleState.value = false
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
                        isVisibleState.value = false
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = VKgramTheme.palette.primary)
                ) {
                    Text(
                        text = stringResource(R.string.profile_unsubscribe_dialog_dismiss),
                        style = VKgramTheme.typography.labelLarge
                    )
                }
            },
            containerColor = VKgramTheme.palette.surface,
            titleContentColor = VKgramTheme.palette.onSurface,
            textContentColor = VKgramTheme.palette.onSurfaceVariant
        )
    }
}