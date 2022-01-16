package me.vislavy.vkgram.search.views

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.search.R
import me.vislavy.vkgram.search.models.SearchViewState

@ExperimentalAnimationApi
@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    viewState: SearchViewState.Display,
    onSearchTextChange: (String) -> Unit,
    navController: NavController = rememberNavController()
) {
    val focusRequesterState by remember { mutableStateOf(FocusRequester()) }

    SideEffect {
        focusRequesterState.requestFocus()
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = VKgramTheme.palette.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .size(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = VKgramTheme.palette.onSurface
                )
            }

            Spacer(Modifier.weight(1F))

            var searchTextState by remember { mutableStateOf("") }
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequesterState),
                value = searchTextState,
                onValueChange = { text ->
                    searchTextState = text
                    onSearchTextChange(searchTextState)
                },
                textStyle = VKgramTheme.typography.body1,
                placeholder = {
                    Text(
                        text = stringResource(R.string.search),
                        color = VKgramTheme.palette.hintText,
                        style = VKgramTheme.typography.searchText
                    )
                },
                maxLines = 1,
                trailingIcon = {
                    AnimatedVisibility(
                        visible = !viewState.historyPanelEnabled,
                        enter = (fadeIn() + scaleIn()),
                        exit = (fadeOut()  + scaleOut())
                    ) {
                        IconButton(onClick = {
                            searchTextState = ""
                            onSearchTextChange(searchTextState)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = null,
                                tint = VKgramTheme.palette.onSurface
                            )
                        }
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = VKgramTheme.palette.background,
                    unfocusedBorderColor = VKgramTheme.palette.background,
                    textColor = VKgramTheme.palette.primaryText,
                    cursorColor = VKgramTheme.palette.secondary
                )
            )
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun PreviewSearchTopBar() {
    MainTheme {
        SearchTopBar(
            viewState = SearchViewState.Display(),
            onSearchTextChange = { }
        )
    }
}