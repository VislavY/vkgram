package me.vislavy.vkgram.profile.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import me.vislavy.vkgram.api.data.Counters
import me.vislavy.vkgram.api.data.LastSeen
import me.vislavy.vkgram.api.data.User
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.profile.R
import me.vislavy.vkgram.profile.models.ProfileState
import me.vislavy.vkgram.profile.utils.formatDate
import me.vislavy.vkgram.profile.utils.formatNumber
import me.vislavy.vkgram.profile.views.dialogs.ProfileUnfriendDialog
import me.vislavy.vkgram.profile.views.dialogs.ProfileUnsubscribeDialog

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    state: ProfileState.Display,
    navController: NavController = rememberNavController(),
    onSubscribeOrAddFriendClick: () -> Unit = { },
    onUnsubscribeOrUnfriendClick: () -> Unit = { },
    onAttachmentsButtonClick: () -> Unit = { }
) {
    val isUnsubscribeDialogVisibleState = remember { mutableStateOf(false) }
    var isReplyToSubscriptionMenuVisible by remember { mutableStateOf(false) }
    val isUnfriendDialogVisibleState = remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.surface
    ) {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = DefaultPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    painter = rememberImagePainter(state.user.photo),
                    contentDescription = stringResource(R.string.profile_photo)
                )

                Spacer(Modifier.weight(1F))

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = VKgramTheme.palette.primaryContainer,
                            shape = CircleShape
                        )
                        .clip(CircleShape)
                        .clickable(
                            onClick = {
                                navController.navigate("${Destinations.MessageHistory}/${state.user.id}")
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple()
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Email,
                        contentDescription = stringResource(R.string.profile_dialog),
                        tint = VKgramTheme.palette.onPrimaryContainer
                    )
                }

                Spacer(Modifier.width(16.dp))

                if (!state.isYourProfile) {
                    when (state.user.friendStatus) {
                        0 -> Button(
                            onClick = onSubscribeOrAddFriendClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = VKgramTheme.palette.primary,
                                contentColor = VKgramTheme.palette.onPrimary
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.profile_subscribe),
                                style = VKgramTheme.typography.labelLarge
                            )
                        }
                        1 -> OutlinedButton(
                            onClick = {
                                isUnsubscribeDialogVisibleState.value = true
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = VKgramTheme.palette.primary
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = VKgramTheme.palette.primary
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.profile_unsubscribe),
                                style = VKgramTheme.typography.labelLarge
                            )
                        }
                        2 -> Box {
                            OutlinedButton(
                                onClick = {
                                    isReplyToSubscriptionMenuVisible = true
                                },
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = VKgramTheme.palette.primary
                                ),
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = VKgramTheme.palette.primary
                                )
                            ) {
                                Text(
                                    text = stringResource(R.string.profile_reply_to_subscription),
                                    style = VKgramTheme.typography.labelLarge
                                )
                            }

                            DropdownMenu(
                                expanded = isReplyToSubscriptionMenuVisible,
                                onDismissRequest = {
                                    isReplyToSubscriptionMenuVisible = false
                                }
                            )
                            {
                                DropdownMenuItem(
                                    onClick = onSubscribeOrAddFriendClick,
                                    text = {
                                        Text(
                                            text = stringResource(R.string.profile_reply_to_subscription_menu_accept),
                                            style = VKgramTheme.typography.bodyMedium
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(VKgramTheme.palette.onSurface)
                                )

                                DropdownMenuItem(
                                    onClick = onUnsubscribeOrUnfriendClick,
                                    text = {
                                        Text(
                                            text = stringResource(R.string.profile_reply_to_subscription_menu_reject),
                                            style = VKgramTheme.typography.bodyMedium
                                        )
                                    },
                                    colors = MenuDefaults.itemColors(VKgramTheme.palette.onSurface)
                                )
                            }
                        }
                        3 -> OutlinedButton(
                            onClick = {
                                isUnfriendDialogVisibleState.value = true
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = VKgramTheme.palette.error
                            ),
                            border = BorderStroke(
                                width = 1.dp,
                                color = VKgramTheme.palette.error
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.profile_delete_friend),
                                style = VKgramTheme.typography.labelLarge
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                modifier = Modifier.padding(horizontal = DefaultPadding),
                text = "${state.user.firstName} ${state.user.lastName}",
                color = VKgramTheme.palette.onSurface,
                style = VKgramTheme.typography.titleLarge
            )

            val lastSeen = state.user.lastSeen?.let { formatDate(it.time) }
            Text(
                modifier = Modifier.padding(horizontal = DefaultPadding),
                text = "$lastSeen",
                color = VKgramTheme.palette.onSurfaceVariant,
                style = VKgramTheme.typography.bodySmall
            )

            if (state.user.status.isNotBlank()) {
                Spacer(Modifier.height(16.dp))

                Text(
                    modifier = Modifier.padding(horizontal = DefaultPadding),
                    text = state.user.status,
                    color = VKgramTheme.palette.onSurface,
                    style = VKgramTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(16.dp))

            val location = StringBuilder().apply {
                state.user.country?.let { append("${it.title}, ") }
                state.user.city?.let { append(it.title) }
            }.ifBlank { stringResource(R.string.profile_not_specified).lowercase() }
            if (location.isNotBlank()) {
                Row(
                    modifier = Modifier.padding(horizontal = DefaultPadding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(14.dp),
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = stringResource(R.string.profile_location_icon),
                        tint = VKgramTheme.palette.onSurfaceVariant
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = stringResource(R.string.profile_location, location),
                        color = VKgramTheme.palette.onSurfaceVariant,
                        style = VKgramTheme.typography.bodyMedium
                    )
                }
            }

            val birthDay = state.user.birthDay.ifBlank {
                stringResource(R.string.profile_not_specified).lowercase()
            }
            Row(
                modifier = Modifier.padding(horizontal = DefaultPadding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = Icons.Rounded.CalendarToday,
                    contentDescription = stringResource(R.string.profile_birthday_icon),
                    tint = VKgramTheme.palette.onSurfaceVariant
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = stringResource(R.string.profile_birthday, birthDay),
                    color = VKgramTheme.palette.onSurfaceVariant,
                    style = VKgramTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.profile_friends),
                        color = VKgramTheme.palette.onSurfaceVariant,
                        style = VKgramTheme.typography.bodyLarge
                    )

                    Text(
                        text = formatNumber(state.user.counters.friends),
                        color = VKgramTheme.palette.onSurface,
                        style = VKgramTheme.typography.titleLarge
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.profile_common_friends),
                        color = VKgramTheme.palette.onSurfaceVariant,
                        style = VKgramTheme.typography.bodyLarge
                    )

                    Text(
                        text = formatNumber(state.user.counters.commonFriends),
                        color = VKgramTheme.palette.onSurface,
                        style = VKgramTheme.typography.titleLarge
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.profile_subscribes),
                        color = VKgramTheme.palette.onSurfaceVariant,
                        style = VKgramTheme.typography.bodyLarge
                    )

                    Text(
                        text = formatNumber(state.user.counters.subscribes),
                        color = VKgramTheme.palette.onSurface,
                        style = VKgramTheme.typography.titleLarge
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Divider(
                modifier = Modifier.padding(horizontal = DefaultPadding),
                color = VKgramTheme.palette.surfaceVariant
            )

            Spacer(Modifier.height(16.dp))

            when (state.isYourProfile) {
                true -> {
                    Text(
                        modifier = Modifier.padding(horizontal = DefaultPadding),
                        text = stringResource(R.string.profile_settings),
                        color = VKgramTheme.palette.primary,
                        style = VKgramTheme.typography.titleSmall
                    )

                    ProfileSectionItem(
                        onClick = { },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = stringResource(R.string.profile_account_settings_icon)
                            )
                        },
                        text = stringResource(R.string.profile_account),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.ArrowRight,
                                contentDescription = stringResource(R.string.profile_arrow_right_icon)
                            )
                        }
                    )

                    ProfileSectionItem(
                        onClick = { },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Settings,
                                contentDescription = stringResource(R.string.profile_application_settings_icon)
                            )
                        },
                        text = stringResource(R.string.profile_application),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.ArrowRight,
                                contentDescription = stringResource(R.string.profile_arrow_right_icon)
                            )
                        }
                    )

                    ProfileSectionItem(
                        onClick = { },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Block,
                                contentDescription = stringResource(R.string.profile_blacklist_icon)
                            )
                        },
                        text = stringResource(R.string.profile_blacklist),
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.ArrowRight,
                                contentDescription = stringResource(R.string.profile_arrow_right_icon)
                            )
                        }
                    )

                    Spacer(Modifier.height(16.dp))

                    Divider(
                        modifier = Modifier.padding(horizontal = DefaultPadding),
                        color = VKgramTheme.palette.surfaceVariant
                    )

                    Spacer(Modifier.height(16.dp))

                    TextButton(
                        modifier = Modifier.padding(horizontal = DefaultPadding),
                        onClick = { }
                    ) {
                        Text(
                            text = stringResource(R.string.profile_logout),
                            color = VKgramTheme.palette.error,
                            style = VKgramTheme.typography.labelLarge
                        )
                    }
                }
                false -> {
                    Text(
                        modifier = Modifier.padding(horizontal = DefaultPadding),
                        text = stringResource(R.string.profile_general),
                        color = VKgramTheme.palette.primary,
                        style = VKgramTheme.typography.titleSmall
                    )

                    ProfileSectionItem(
                        onClick = {
//                            onNotificationChange()
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.Notifications,
                                contentDescription = stringResource(R.string.profile_notifications_icon)
                            )
                        },
                        text = stringResource(R.string.profile_notifications),
                        trailingIcon = {
                            Switch(
                                checked = true,
                                onCheckedChange = null,
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = VKgramTheme.palette.onPrimaryContainer,
                                    checkedTrackColor = VKgramTheme.palette.primaryContainer,
                                    checkedTrackAlpha = 1F,
                                    uncheckedThumbColor = VKgramTheme.palette.onSurfaceVariant,
                                    uncheckedTrackColor = VKgramTheme.palette.surfaceVariant,
                                    uncheckedTrackAlpha = 1F
                                )
                            )
                        }
                    )

                    ProfileSectionItem(
                        onClick = onAttachmentsButtonClick,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Outlined.PhotoLibrary,
                                contentDescription = null
                            )
                        },
                        text = "Вложения",
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Rounded.ArrowRight,
                                contentDescription = stringResource(R.string.profile_arrow_right_icon)
                            )
                        }
                    )
                }
            }
        }
    }

    ProfileUnsubscribeDialog(
        isVisibleState = isUnsubscribeDialogVisibleState,
        model = state.user,
        onConfirm = onUnsubscribeOrUnfriendClick
    )

    ProfileUnfriendDialog(
        isVisibleState = isUnfriendDialogVisibleState,
        model = state.user,
        onConfirm = onUnsubscribeOrUnfriendClick
    )
}

@Preview("Profile content")
@Composable
fun PreviewProfileContent() {
    MainTheme {
        ProfileContent(
            state = ProfileState.Display(UserSample),
            navController = rememberNavController()
        )
    }
}

@Preview("Profile content dark theme")
@Composable
fun PreviewProfileContent_Dark() {
    MainTheme(darkThemeEnabled = true) {
        ProfileContent(
            state = ProfileState.Display(UserSample)
        )
    }
}

@Preview("Your profile content")
@Composable
fun PreviewProfileContent_Your() {
    MainTheme {
        ProfileContent(
            state = ProfileState.Display(
                user = UserSample,
                isYourProfile = true
            )
        )
    }
}

@Preview("Your profile content dark theme")
@Composable
fun PreviewProfileContent_YourDark() {
    MainTheme(darkThemeEnabled = true) {
        ProfileContent(
            state = ProfileState.Display(
                user = UserSample,
                isYourProfile = true
            )
        )
    }
}

private val DefaultPadding = 16.dp
private val UserSample = User(
    firstName = "Vladislav",
    lastName = "Yants",
    lastSeen = LastSeen(),
    status = "Android Developer.",
    counters = Counters(123, 7, 3424),
    friendStatus = 2
)