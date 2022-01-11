package me.vislavy.vkgram.search.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.vislavy.vkgram.core.ConversationModel
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.search.R
import me.vislavy.vkgram.search.models.SearchViewState

@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    viewState: SearchViewState.Display,
    onClearClick: () -> Unit,
    onConversationClick: (ConversationModel) -> Unit
) {
    var dialogIsEnabledState by remember { mutableStateOf(false) }

    if (dialogIsEnabledState) {
        ClearSearchHistoryDialog(
            onDismiss = {
                dialogIsEnabledState = false
            },
            onDismissButtonClick = {
                dialogIsEnabledState = false
            },
            onConfirmButtonClick = {
                onClearClick()
                dialogIsEnabledState = false
            }
        )
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.background
    ) {
        Column {
            AnimatedVisibility(
                visible = viewState.historyPanelEnabled,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Row(
                    Modifier.padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.search_last),
                        color = VKgramTheme.palette.secondaryText,
                        style = VKgramTheme.typography.body2.copy(fontWeight = FontWeight.Medium)
                    )

                    Spacer(Modifier.weight(1F))

                    Text(
                        modifier = Modifier.clickable {
                            dialogIsEnabledState = true
                        },
                        text = stringResource(R.string.search_clear),
                        color = VKgramTheme.palette.secondaryText,
                        style = VKgramTheme.typography.body2
                    )
                }

                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = VKgramTheme.palette.surface
                )
            }

            LazyColumn {
                items(viewState.conversationModels) { conversationModel ->
                    FoundConversation(
                        model = conversationModel,
                        onClick = {
                            onConversationClick(it)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSearchContent() {
    MainTheme {
        SearchContent(
            viewState = SearchViewState.Display(
                conversationModels = listOf(
                    ConversationModel(
                        title = "Sample title"
                    ),
                    ConversationModel(
                        title = "Sample title"
                    )
                )
            ),
            onClearClick = { },
            onConversationClick = { }
        )
    }
}