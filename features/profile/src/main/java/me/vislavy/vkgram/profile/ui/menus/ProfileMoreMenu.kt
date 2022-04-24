package me.vislavy.vkgram.profile.ui.menus

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import me.vislavy.vkgram.api.local.entities.StoredUser
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.theme.defaults.VKgramMenuDefaults
import me.vislavy.vkgram.profile.R
import me.vislavy.vkgram.profile.models.ProfileEvent
import me.vislavy.vkgram.profile.ui.dialogs.BlockUserDialog

@Composable
fun ProfileMoreMenu(
    modifier: Modifier = Modifier,
    extended: MutableState<Boolean>,
    model: StoredUser,
    onEvent: (ProfileEvent) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val linkCopiedMessage = stringResource(R.string.profile_link_copied)
    val blockUserDialogVisibleState = remember { mutableStateOf(false) }

    DropdownMenu(
        modifier = modifier.background(VKgramTheme.palette.surfaceAtPrimary),
        expanded = extended.value,
        onDismissRequest = {
            extended.value = false
        }
    ) {
        DropdownMenuItem(
            colors = VKgramMenuDefaults.itemColors(),
            text = {
                Text(
                    text = stringResource(R.string.profile_copy_link),
                    style = VKgramTheme.typography.bodyLarge
                )
            },
            onClick = {
                clipboardManager.setText(AnnotatedString("https://vk.com/${model.screenName}"))
                Toast.makeText(context, linkCopiedMessage, Toast.LENGTH_SHORT).show()
                extended.value = false
            }
        )

        DropdownMenuItem(
            colors = VKgramMenuDefaults.itemColors(),
            text = {
                Text(
                    text = stringResource(R.string.profile_block),
                    style = VKgramTheme.typography.bodyLarge
                )
            },
            onClick = {
                blockUserDialogVisibleState.value = true
                extended.value = false
            }
        )
    }

    BlockUserDialog(
        visible = blockUserDialogVisibleState,
        userName = model.nameGen,
        onEvent = onEvent
    )
}