package me.vislavy.vkgram.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import me.vislavy.vkgram.core.views.ErrorContent
import me.vislavy.vkgram.search.models.SearchIntent
import me.vislavy.vkgram.search.models.SearchViewState
import me.vislavy.vkgram.search.views.SearchContent
import me.vislavy.vkgram.search.views.SearchLoadingContent
import me.vislavy.vkgram.search.views.SearchTopBar

@ExperimentalAnimationApi
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel
) {
    val viewState = viewModel.viewState.collectAsState()

    when (val state = viewState.value) {
        is SearchViewState.Error -> ErrorContent(onReloadClick = {
            viewModel.onIntent(SearchIntent.Reload)
        })
        is SearchViewState.Display -> Scaffold(topBar = {
            SearchTopBar(
                viewState = state,
                onSearchTextChange = { searchText ->
                    viewModel.onIntent(SearchIntent.StartSearch(searchText))
                },
                navController = navController
            )
        }) { paddingValues ->
            val modifier = Modifier.padding(paddingValues)

            AnimatedVisibility(
                modifier = Modifier.zIndex(1F),
                visible = state.isLoading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                SearchLoadingContent(modifier)
            }

            SearchContent(
                modifier = modifier,
                viewState = state,
                onClearClick = {
                    viewModel.onIntent(SearchIntent.ClearSearchHistory)
                },
                onConversationClick = { conversationModel ->
                    viewModel.onIntent(SearchIntent.AddToSearchHistory(conversationModel.properties.id))
                }
            )
        }
    }

    LaunchedEffect(viewState) {
        viewModel.onIntent(SearchIntent.LoadConversationList)
    }
}