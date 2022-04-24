package me.vislavy.vkgram.profile.ui.contents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.vk.sdk.api.base.dto.BaseSex
import com.vk.sdk.api.friends.dto.FriendsFriendStatusStatus
import me.vislavy.vkgram.api.local.entities.StoredUser
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.theme.defaults.VKgramButtonDefaults
import me.vislavy.vkgram.profile.R
import me.vislavy.vkgram.profile.models.ProfileEvent
import me.vislavy.vkgram.profile.ui.dialogs.UnfollowDialog
import me.vislavy.vkgram.profile.ui.dialogs.UnfriendDialog
import me.vislavy.vkgram.profile.ui.menus.ReplyToFollowMenuReply
import me.vislavy.vkgram.profile.utils.formatDate
import me.vislavy.vkgram.profile.utils.formatNumber
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UserProfileContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    model: StoredUser,
    onEvent: (ProfileEvent) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = VKgramTheme.palette.surface,
        shadowElevation = 1.dp
    ) {
        Column {
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = VKgramTheme.palette.surfaceVariant
            )

            Spacer(Modifier.height(16.dp))

            UserProfileInfo(model = model)

            Spacer(Modifier.height(16.dp))

            UserProfileButtons(
                navController = navController,
                model = model,
                onEvent = onEvent
            )

            Spacer(Modifier.height(16.dp))

            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = VKgramTheme.palette.surfaceVariant
            )

            UserProfileAdditionalInfo(model = model)

            if (!model.isYourProfile) {
                Spacer(Modifier.height(16.dp))

                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = VKgramTheme.palette.surfaceVariant
                )

                ProfileNotifications()
            }
        }
    }
}

@Composable
fun UserProfileInfo(
    modifier: Modifier = Modifier,
    model: StoredUser
) {
    val lastSeen = when (model.sex) {
        BaseSex.UNKNOWN -> stringResource(
            R.string.last_seen_unknown,
            formatDate(model.lastSeen)
        )
        BaseSex.FEMALE -> stringResource(
            R.string.last_seen_was_female,
            formatDate(model.lastSeen)
        )
        BaseSex.MALE -> stringResource(
            R.string.last_seen_was_male,
            formatDate(model.lastSeen)
        ).lowercase()
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
    {
        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            model = model.photoUrl,
            contentDescription = stringResource(R.string.profile_photo)
        )

        Spacer(Modifier.width(16.dp))

        Column(
            modifier = Modifier.height(80.dp),
            verticalArrangement = Arrangement.SpaceBetween
        )
        {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1F, false),
                    text = model.name,
                    color = VKgramTheme.palette.onSurface,
                    style = VKgramTheme.typography.titleLarge.copy(fontWeight = FontWeight.Medium),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (model.verified) {
                    Spacer(Modifier.width(8.dp))

                    Icon(
                        imageVector = Icons.Outlined.Verified,
                        contentDescription = stringResource(R.string.profile_verified),
                        tint = VKgramTheme.palette.primary
                    )
                }
            }

            Text(
                text = model.status ?: "",
                color = VKgramTheme.palette.onSurface,
                style = VKgramTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = lastSeen,
                color = VKgramTheme.palette.onSurfaceVariant,
                style = VKgramTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun UserProfileButtons(
    modifier: Modifier = Modifier,
    navController: NavController,
    model: StoredUser,
    onEvent: (ProfileEvent) -> Unit
) {
    val replyToFollowMenuExpandedState = remember { mutableStateOf(false) }
    val unfollowDialogVisibleState = remember { mutableStateOf(false) }
    val unfriendDialogVisibleState = remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .padding(horizontal = 16.dp)
    ) {
        Button(
            modifier = Modifier.weight(1F),
            onClick = {
                navController.navigate("${Destinations.MessageHistory}/${model.id}")
            },
            enabled = model.canWritePrivateMessage,
            colors = VKgramButtonDefaults.buttonColors()
        ) {
            Text(
                text = stringResource(
                    id = if (model.isYourProfile) R.string.profile_notes else R.string.profile_messages
                ),
                style = VKgramTheme.typography.labelLarge
            )
        }

        Spacer(Modifier.width(8.dp))

        if (model.isYourProfile) {
            SecondaryButton(
                modifier = Modifier.weight(1F),
                onClick = { navController.navigate(Destinations.AppSettings) }
            ) {
                Text(
                    text = stringResource(R.string.profile_settings),
                    style = VKgramTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            when (model.friendStatus) {
                FriendsFriendStatusStatus.NOT_A_FRIEND -> SecondaryButton(
                    modifier = Modifier.weight(1F),
                    onClick = { onEvent(ProfileEvent.FollowOrAddFriend) }
                ) {
                    Text(
                        text = stringResource(R.string.profile_follow),
                        style = VKgramTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                FriendsFriendStatusStatus.INCOMING_REQUEST -> SecondaryButton(
                    modifier = Modifier.weight(1F),
                    onClick = {
                    replyToFollowMenuExpandedState.value = true
                    }
                ) {
                    Text(
                        text = stringResource(R.string.profile_reply_to_follow),
                        style = VKgramTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                FriendsFriendStatusStatus.OUTCOMING_REQUEST -> SecondaryButton(
                    modifier = Modifier.weight(1F),
                    onClick = {
                        unfollowDialogVisibleState.value = true
                    }
                ) {
                    Text(
                        text = stringResource(R.string.profile_unfollow),
                        style = VKgramTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                FriendsFriendStatusStatus.IS_FRIEND -> SecondaryButton(
                    modifier = Modifier.weight(1F),
                    onClick = {
                        unfriendDialogVisibleState.value = true
                    },
                    colors = VKgramButtonDefaults.secondaryButtonColors(
                        contentColor = VKgramTheme.palette.error
                    )
                ) {
                    Text(
                        text = stringResource(R.string.profile_unfriend),
                        style = VKgramTheme.typography.labelLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    ReplyToFollowMenuReply(
        expanded = replyToFollowMenuExpandedState,
        onEvent = onEvent
    )

    UnfollowDialog(
        visible = unfollowDialogVisibleState,
        userName = model.nameGen,
        onEvent = onEvent
    )

    UnfriendDialog(
        visible = unfriendDialogVisibleState,
        userName = model.nameGen,
        onEvent = onEvent
    )
}

@Composable
fun UserProfileAdditionalInfo(
    modifier: Modifier = Modifier,
    model: StoredUser
) {
    val resources = LocalContext.current.resources
    val location by remember(model.country, model.city) {
        mutableStateOf(
            value = StringBuilder().apply {
                model.country?.let { append(it) }
                model.city?.let {
                    if (isNotBlank()) append(", ")
                    append(it)
                }
            }.toString()
        )
    }
    val birthday by remember(model.birthday) {
        mutableStateOf(
            value = model.birthday?.let { date ->
                val dateComponents = date.split(".").map { it.toInt() }
                val isFullDate = dateComponents.size == 3
                val calendar = when (isFullDate) {
                    true -> GregorianCalendar(
                        dateComponents[2],
                        dateComponents[1],
                        dateComponents[0]
                    )
                    false -> GregorianCalendar(
                        0,
                        dateComponents[1],
                        dateComponents[0]
                    )
                }
                val dateFormatPattern = if (isFullDate) "dd MMM y" else "dd MMMM"
                val dateFormat = SimpleDateFormat(dateFormatPattern, Locale.getDefault())
                dateFormat.format(calendar.time)
            }
        )
    }

    Column(modifier = modifier.fillMaxWidth()) {
        UserProfileInfo(
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Outlined.Home,
                    contentDescription = stringResource(R.string.profile_location),
                    tint = VKgramTheme.palette.onSurfaceVariant
                )
            },
            body = {
                Text(
                    text = location.ifBlank { stringResource(R.string.profile_not_specified) },
                    color = VKgramTheme.palette.onSurfaceVariant,
                    style = VKgramTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )

        UserProfileInfo(
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Outlined.Cake,
                    contentDescription = stringResource(R.string.profile_birthday),
                    tint = VKgramTheme.palette.onSurfaceVariant
                )
            },
            body = {
                Text(
                    text = birthday ?: stringResource(R.string.profile_not_specified),
                    color = VKgramTheme.palette.onSurfaceVariant,
                    style = VKgramTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )

        UserProfileInfo(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = { }
            ),
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Rounded.Info,
                    contentDescription = stringResource(R.string.profile_more_information),
                    tint = VKgramTheme.palette.primary
                )
            },
            body = {
                Text(
                    text = stringResource(R.string.profile_more_information),
                    color = VKgramTheme.palette.primary,
                    style = VKgramTheme.typography.labelLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(3) { index ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = when (index) {
                            0 -> formatNumber(model.friendCount)
                            1 -> formatNumber(model.mutualFriendCount)
                            else -> formatNumber(model.followerCount)
                        },
                        color = VKgramTheme.palette.onSurface,
                        style = VKgramTheme.typography.titleLarge
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = when (index) {
                            0 -> resources.getQuantityString(
                                R.plurals.profile_plural_friend,
                                model.friendCount
                            ).uppercase()
                            1 -> resources.getQuantityString(
                                R.plurals.profile_plural_mutual_friend,
                                model.mutualFriendCount
                            ).uppercase()
                            else -> resources.getQuantityString(
                                R.plurals.profile_plural_followers,
                                model.followerCount
                            ).uppercase()
                        },
                        color = VKgramTheme.palette.onSurfaceVariant,
                        style = VKgramTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun UserProfileInfo(
    modifier: Modifier = Modifier,
    leadingIconColor: Color = VKgramTheme.palette.onSurfaceVariant,
    bodyColor: Color = VKgramTheme.palette.onSurfaceVariant,
    leadingIcon: @Composable () -> Unit,
    body: @Composable () -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = VKgramTheme.palette.surface
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(contentColor = leadingIconColor) {
                leadingIcon()
            }

            Spacer(Modifier.width(16.dp))

            Surface(contentColor = bodyColor) {
                body()
            }
        }
    }
}

@Composable
fun SecondaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    colors: ButtonColors = VKgramButtonDefaults.secondaryButtonColors(),
    content: @Composable (RowScope.() -> Unit)
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = colors,
        content = content
    )
}