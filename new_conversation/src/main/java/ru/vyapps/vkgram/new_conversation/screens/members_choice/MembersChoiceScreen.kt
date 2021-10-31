package ru.vyapps.vkgram.new_conversation.screens.members_choice

import androidx.compose.animation.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import kotlinx.serialization.ExperimentalSerializationApi
import ru.vyapps.vkgram.core.views.LoadingContent
import ru.vyapps.vkgram.core.views.ErrorContent
import ru.vyapps.vkgram.new_conversation.screens.members_choice.models.MembersChoiceEvent
import ru.vyapps.vkgram.new_conversation.screens.members_choice.models.MembersChoiceViewState
import ru.vyapps.vkgram.new_conversation.screens.members_choice.views.MembersChoiceTopBar
import ru.vyapps.vkgram.new_conversation.screens.members_choice.views.MembersChoiceContent
import ru.vyapps.vkgram.new_conversation.screens.members_choice.views.MembersChoiceFab

@ExperimentalSerializationApi
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun MembersChoiceScreen(
    navController: NavController = rememberNavController(),
    viewModel: MembersChoiceViewModel = viewModel()
) {
    val viewState = viewModel.viewState.collectAsState()
    Scaffold(
        topBar = {
            MembersChoiceTopBar(navController)
        },
        floatingActionButton = {
            if (viewState.value is MembersChoiceViewState.Display) {
                MembersChoiceFab(
                    viewState = (viewState.value as MembersChoiceViewState.Display),
                    navController = navController
                )
            }
        }
    ) { padding ->
        when (val state = viewState.value) {
            is MembersChoiceViewState.Loading -> LoadingContent()
            is MembersChoiceViewState.Display -> {
                val modifier = Modifier.padding(padding)
                MembersChoiceContent(
                    modifier = modifier,
                    viewState = state,
                    onItemClick = { clickedFriend ->
                        viewModel.onEvent(MembersChoiceEvent.OnFriendClick(clickedFriend))
                    },
                    onItemListEnd = { itemCount ->
                        viewModel.onEvent(MembersChoiceEvent.OnItemListEnd(itemCount))
                    }
                )
            }
            is MembersChoiceViewState.Error -> ErrorContent(onReloadClick = {
                viewModel.onEvent(MembersChoiceEvent.ReloadScreen)
            })
            else -> throw NotImplementedError("Unexpected new_conversation state")
        }

        LaunchedEffect(viewState) {
            if (viewState.value !is MembersChoiceViewState.Display) {
                viewModel.onEvent(MembersChoiceEvent.EnterScreen)
            }
        }
    }
}