package me.vislavy.vkgram.home.views

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.api.data.User
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.home.R
import me.vislavy.vkgram.home.models.HomeViewState

@ExperimentalAnimationApi
@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    viewState: HomeViewState.Display,
    userModel: User,
    onClearSelectedConvListClick: () -> Unit,
    onDeleteConvClick: () -> Unit,
    navController: NavController
) {
    val navDialogInOpenedState = remember { mutableStateOf(false) }
    NavigationDialog(
        isOpenedState = navDialogInOpenedState,
        profileModel = userModel,
        navController = navController
    )

    var deleteConvDialogState by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = modifier,
        elevation = 0.dp,
        backgroundColor = VKgramTheme.palette.primary,
        contentPadding = rememberInsetsPaddingValues(
            LocalWindowInsets.current.statusBars,
            applyBottom = false,
        )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(Modifier.width(16.dp))

            Card(
                modifier = Modifier
                    .clickable {
                        navDialogInOpenedState.value = true
                    }
                    .height(32.dp),
                shape = RoundedCornerShape(32.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.width(12.dp))

                    Image(
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(24.dp),
                        painter = rememberImagePainter(
                            data = userModel.photo,
                            builder = {
                                crossfade(true)
                                placeholder(R.drawable.photo_placeholder_56)
                                transformations(CircleCropTransformation())
                            }
                        ),
                        contentDescription = null
                    )

                    Spacer(Modifier.width(8.dp))

                    Text(
                        text = userModel.firstName,
                        color = VKgramTheme.palette.primaryText,
                        style = VKgramTheme.typography.body2
                    )

                    Spacer(Modifier.width(12.dp))
                }
            }

            Spacer(Modifier.weight(1F))

            IconButton(onClick = {
                navController.navigate(Destinations.Search)
            }) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                    tint = VKgramTheme.palette.onSurface
                )
            }
        }


        AnimatedVisibility(
            visible = viewState.selectModeEnabled,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Row(
                modifier = Modifier.background(VKgramTheme.palette.primary),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClearSelectedConvListClick) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onSurface
                    )
                }

                Spacer(Modifier.width(16.dp))

                Text(
                    text = "${viewState.selectedConversations.size}",
                    color = VKgramTheme.palette.secondaryText,
                    style = VKgramTheme.typography.subtitle1
                )

                Spacer(Modifier.weight(1F))

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.PushPin,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onSurface
                    )
                }

                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.VolumeOff,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onSurface
                    )
                }

                IconButton(onClick = { deleteConvDialogState = true }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onSurface
                    )
                }
            }
        }
    }

    if (deleteConvDialogState) {
        DeleteConversationDialog(
            selectedConversations = viewState.selectedConversations,
            onDismiss = { deleteConvDialogState = false },
            onCancelClick = { deleteConvDialogState = false },
            onConfirmClick = {
                onDeleteConvClick()
                deleteConvDialogState = false
            }
        )
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewHomeTopBar() {
    MainTheme {
        HomeTopBar(
            viewState = HomeViewState.Display(),
            userModel = User(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample"
            ),
            onClearSelectedConvListClick = { },
            onDeleteConvClick = { },
            navController = rememberNavController()
        )
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewHomeTopBarOnSelectMode() {
    MainTheme {
        HomeTopBar(
            viewState = HomeViewState.Display(selectModeEnabled = true),
            userModel = User(
                id = 1,
                domain = "Sample domain",
                firstName = "It's",
                lastName = "Sample"
            ),
            onClearSelectedConvListClick = { },
            onDeleteConvClick = { },
            navController = rememberNavController()
        )
    }
}
