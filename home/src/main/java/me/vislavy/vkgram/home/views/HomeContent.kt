package me.vislavy.vkgram.home.views

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.home.models.HomeViewState

@ExperimentalSerializationApi
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
    val scope = rememberCoroutineScope()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        val pagerState = rememberPagerState()
        val tabTitles = listOf("Беседы", "Друзья")
        Column {
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = VKgramTheme.palette.surface
            )

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = VKgramTheme.palette.background,
                indicator = { tabPositions ->
                    Divider(
                        modifier = Modifier
                            .clip(
                                CircleShape
                            )
                            .tabIndicatorOffset(currentTabPosition = tabPositions[pagerState.targetPage])
                            .padding(horizontal = 72.dp),
                        thickness = 2.dp,
                        color = VKgramTheme.palette.secondary
                    )
                },
                divider = {
                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = VKgramTheme.palette.surface
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    val isSelected = (index == pagerState.currentPage)
                    Tab(
                        selected = isSelected,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 10.dp),
                            text = title,
                            color = if (isSelected) {
                                VKgramTheme.palette.secondary
                            } else {
                                VKgramTheme.palette.secondaryText
                            },
                            style = VKgramTheme.typography.subtitle1
                        )
                    }
                }
            }

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

@ExperimentalSerializationApi
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

@ExperimentalSerializationApi
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