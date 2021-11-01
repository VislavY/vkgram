package ru.vyapps.vkgram.home.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme

@ExperimentalPagerApi
@Composable
fun HomeTabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    tabTitles: List<String>
) {
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = VKgramTheme.palette.surface
    ) {
        Row {
            tabTitles.forEachIndexed { i, title ->
                val isSelected = (i == pagerState.currentPage)
                Card(
                    modifier = Modifier
                        .padding(4.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    backgroundColor = if (isSelected) {
                        Color.White
                    } else {
                        VKgramTheme.palette.surface
                    },
                    elevation = if (isSelected) {
                        8.dp
                    } else {
                        0.dp
                    }
                ) {
                    Tab(
                        selected = isSelected,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(i)
                            }
                        }
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier.padding(4.dp),
                            color = if (isSelected) {
                                VKgramTheme.palette.primaryText
                            } else {
                                VKgramTheme.palette.secondaryText
                            },
                            style = VKgramTheme.typography.title
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalPagerApi
@Preview
@Composable
fun HomeTabRow_Preview() {
    MainTheme {
        HomeTabRow(
            pagerState = rememberPagerState(),
            tabTitles = listOf("Беседы", "Друзья")
        )
    }
}

@ExperimentalPagerApi
@Preview
@Composable
fun DarkHomeTabRow_Preview() {
    MainTheme(darkThemeEnabled = true) {
        HomeTabRow(
            pagerState = rememberPagerState(),
            tabTitles = listOf("Беседы", "Друзья")
        )
    }
}