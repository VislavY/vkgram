package ru.vyapps.vkgram.home.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.home.models.HomeViewState

@Composable
fun FriendListContent(
    modifier: Modifier = Modifier,
    viewState: HomeViewState.Display,
    navController: NavController,
    onListEnd: (Int) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        Column {
            Spacer(Modifier.height(16.dp))

            LazyColumn() {
                itemsIndexed(viewState.friends) { i, friend ->
                    FriendItem(
                        model = friend,
                        onClick = { }
                    )

                    if (i == (viewState.friends.size - 1)) {
                        onListEnd(viewState.friends.size)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FriendListContent_Preview() {
    MainTheme {
        FriendListContent(
            viewState = HomeViewState.Display(
                conversationModels = emptyList(),
                friends = emptyList()
            ),
            navController = rememberNavController(),
            onListEnd = { }
        )
    }
}

@Preview
@Composable
fun DarkFriendListContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        FriendListContent(
            viewState = HomeViewState.Display(
                conversationModels = emptyList(),
                friends = emptyList()
            ),
            navController = rememberNavController(),
            onListEnd = { }
        )
    }
}