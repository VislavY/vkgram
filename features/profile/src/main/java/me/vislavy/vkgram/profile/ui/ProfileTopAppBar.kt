package me.vislavy.vkgram.profile.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.vislavy.vkgram.api.local.entities.StoredUser
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.theme.defaults.VKgramTopAppBarDefaults
import me.vislavy.vkgram.profile.R
import me.vislavy.vkgram.profile.models.ProfileEvent
import me.vislavy.vkgram.profile.ui.menus.ProfileMoreMenu

@Composable
fun ProfileTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController(),
    model: StoredUser? = null,
    hovered: Boolean = false,
    onEvent: (ProfileEvent) -> Unit = { }
) {
    val moreMenuExtendedState = remember { mutableStateOf(false) }

    val color by animateColorAsState(
        targetValue = if (hovered)
            VKgramTheme.palette.surfaceAtPrimary else VKgramTheme.palette.surface
    )
    val elevation by animateDpAsState(
        targetValue = if (hovered) 4.dp else 0.dp
    )

    model?.let {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopEnd)
        ) {
            ProfileMoreMenu(
                extended = moreMenuExtendedState,
                model = it,
                onEvent = onEvent
            )
        }
    }

    SmallTopAppBar(
        modifier = modifier.shadow(elevation),
        colors = VKgramTopAppBarDefaults.smallTopAppBarColors(containerColor = color),
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.profile_back_arrow_icon)
                )
            }
        },
        title = {
            Text(
                text = model?.screenName ?: stringResource(R.string.profile_loading),
                style = VKgramTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = {
                model?.apply {
                    moreMenuExtendedState.value = true
                }
            }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = stringResource(R.string.profile_more_icon)
                )
            }
        }
    )
}