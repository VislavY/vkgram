package ru.vyapps.vkgram.home.views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.home.models.HomeViewState

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    viewState: HomeViewState.Display,
    navController: NavController,
    onConversationListEnd: (Int) -> Unit,
    onFriendListEnd: (Int) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        val pagerState = rememberPagerState()
        val tabTitles = listOf("Беседы", "Друзья")
        Column {
            HomeTabRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                pagerState = pagerState,
                tabTitles = tabTitles
            )

            Spacer(Modifier.height(16.dp))

            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = VKgramTheme.palette.surface
            )

            HorizontalPager(
                count = tabTitles.size,
                state = pagerState
            ) { page ->
                if (page == 0) {
                    ConversationListContent(
                        viewState = viewState,
                        navController = navController,
                        onListEnd = onConversationListEnd
                    )
                } else {
                    FriendListContent(
                        viewState = viewState,
                        navController = navController,
                        onListEnd = onFriendListEnd
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Preview
@Composable
fun HomeContent_Preview() {
    MainTheme {
        HomeContent(
            viewState = HomeViewState.Display(
                conversations = emptyList(),
                friends = emptyList()
            ),
            navController = rememberNavController(),
            onConversationListEnd = { },
            onFriendListEnd = { }
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Preview
@Composable
fun DarkHomeContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        HomeContent(
            viewState = HomeViewState.Display(
                conversations = emptyList(),
                friends = emptyList()
            ),
            navController = rememberNavController(),
            onConversationListEnd = { },
            onFriendListEnd = { }
        )
    }
}