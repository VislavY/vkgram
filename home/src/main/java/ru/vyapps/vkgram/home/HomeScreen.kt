package ru.vyapps.vkgram.home

import android.app.Activity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.serialization.ExperimentalSerializationApi
import ru.vyapps.vkgram.core.Destinations
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.core.views.ErrorContent
import ru.vyapps.vkgram.core.views.LoadingContent
import ru.vyapps.vkgram.home.models.HomeEvent
import ru.vyapps.vkgram.home.models.HomeViewState
import ru.vyapps.vkgram.home.views.HomeContent
import ru.vyapps.vkgram.home.views.HomeTopBar

@ExperimentalSerializationApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel
) {
    val viewState = viewModel.viewState.collectAsState()

    when (val state = viewState.value) {
        is HomeViewState.Loading -> LoadingContent()
        is HomeViewState.Error -> ErrorContent(
            onReloadClick = {
                viewModel.onEvent(HomeEvent.ReloadScreen)
            }
        )
        is HomeViewState.Display -> Scaffold(
            topBar = {
                state.profile?.let {
                    HomeTopBar(
                        userModel = it,
                        navController = navController
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Destinations.NewConversation)
                    },
                    modifier = Modifier.padding(bottom = 16.dp),
                    backgroundColor = VKgramTheme.palette.secondary
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Create,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        ) { paddingValues ->
            val modifier = Modifier.padding(paddingValues)
            HomeContent(
                modifier = modifier,
                viewState = state,
                navController = navController,
                onConversationListEnd = { conversationCount ->
                    viewModel.onEvent(HomeEvent.ConversationListEnd(conversationCount))
                },
                onFriendListEnd = { friendCount ->
                    viewModel.onEvent(HomeEvent.FriendListEnd(friendCount))
                }
            )
        }
    }

    LaunchedEffect(viewState) {
        if (viewState.value !is HomeViewState.Display) {
            viewModel.onEvent(HomeEvent.EnterScreen)
        } else {
            viewModel.onEvent(HomeEvent.UpdateProfile)
        }
    }
}