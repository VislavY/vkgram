package me.vislavy.vkgram.profile.ui.menus

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.theme.defaults.VKgramMenuDefaults
import me.vislavy.vkgram.profile.R
import me.vislavy.vkgram.profile.models.ProfileEvent

@Composable
fun ReplyToFollowMenuReply(
    modifier: Modifier = Modifier,
    expanded: MutableState<Boolean>,
    onEvent: (ProfileEvent) -> Unit
) {
    DropdownMenu(
        modifier = modifier.background(VKgramTheme.palette.surfaceAtPrimary),
        expanded = expanded.value,
        onDismissRequest = {
            expanded.value = false
        }
    ) {
        DropdownMenuItem(
            colors = VKgramMenuDefaults.itemColors(),
            text = {
                Text(
                    text = stringResource(R.string.profile_accept),
                    style = VKgramTheme.typography.bodyLarge
                )
            },
            onClick = {
                onEvent(ProfileEvent.FollowOrAddFriend)
            }
        )

        DropdownMenuItem(
            colors = VKgramMenuDefaults.itemColors(),
            text = {
                Text(
                    text = stringResource(R.string.profile_cancel),
                    style = VKgramTheme.typography.bodyLarge
                )
            },
            onClick = {
                onEvent(ProfileEvent.UnfollowOrUnfriend)
            }
        )
    }
}