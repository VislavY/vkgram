package me.vislavy.vkgram.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.serialization.ExperimentalSerializationApi
import me.vislavy.vkgram.core.Destinations
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.views.ErrorContent
import me.vislavy.vkgram.core.views.LoadingContent
import me.vislavy.vkgram.home.models.HomeEvent
import me.vislavy.vkgram.home.models.HomeViewState
import me.vislavy.vkgram.home.views.HomeContent
import me.vislavy.vkgram.home.views.HomeTopBar

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