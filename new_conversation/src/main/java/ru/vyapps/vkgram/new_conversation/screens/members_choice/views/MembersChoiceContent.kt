package ru.vyapps.vkgram.new_conversation.screens.members_choice.views

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme
import ru.vyapps.vkgram.new_conversation.UserModel
import ru.vyapps.vkgram.new_conversation.screens.members_choice.models.MembersChoiceViewState

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun MembersChoiceContent(
    modifier: Modifier = Modifier,
    viewState: MembersChoiceViewState.Display,
    onItemClick: (UserModel) -> Unit,
    onItemListEnd: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        Column() {

            Spacer(Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .height(82.dp)
                    .fillMaxWidth(),
            ) {
                if (viewState.members.isEmpty()) {
                    Box(Modifier.align(Alignment.Center)) {
                        Text(
                            text = "Выберите собеседников",
                            color = VKgramTheme.palette.secondaryText,
                            style = VKgramTheme.typography.body1
                        )
                    }
                }

                val memberListState = rememberLazyListState()
                LazyRow(
                    state = memberListState,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    itemsIndexed(viewState.members) { i, member ->
                        HorizontalMemberItem(
                            model = member,
                            onRemoveClick = {
                                onItemClick(it)
                            }
                        )

                        if (i == memberListState.layoutInfo.totalItemsCount) {
                            scope.launch {
                                memberListState.animateScrollToItem(viewState.members.size)
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = VKgramTheme.palette.surface
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn {
                itemsIndexed(viewState.items) { i, user ->
                    UserItem(
                        model = user,
                        onClick = {
                            onItemClick(it)
                        }
                    )

                    if (i == (viewState.items.size - 1)) {
                        onItemListEnd(viewState.items.size)
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Preview
@Composable
fun MembersChoiceContent_Preview() {
    MainTheme() {
        MembersChoiceContent(
            viewState = MembersChoiceViewState.Display(
                items = listOf(
                    UserModel(
                        id = 1,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    ),
                    UserModel(
                        id = 2,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    )
                ),
                members = listOf(
                    UserModel(
                        id = 1,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    ),
                    UserModel(
                        id = 2,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    )
                ),
                fabVisible = true
            ),
            onItemClick = { },
            onItemListEnd = { }
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Preview
@Composable
fun MembersChoiceDarkContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        MembersChoiceContent(
            viewState = MembersChoiceViewState.Display(
                items = listOf(
                    UserModel(
                        id = 1,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    ),
                    UserModel(
                        id = 2,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    )
                ),
                members = listOf(
                    UserModel(
                        id = 1,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    ),
                    UserModel(
                        id = 2,
                        domain = "Sample domain",
                        firstName = "It's",
                        lastName = "Sample",
                        isSelected = true
                    )
                ),
                fabVisible = true
            ),
            onItemClick = { },
            onItemListEnd = { }
        )
    }
}