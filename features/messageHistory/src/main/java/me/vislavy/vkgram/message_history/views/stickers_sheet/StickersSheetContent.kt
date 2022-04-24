package me.vislavy.vkgram.message_history.views.stickers_sheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@OptIn(ExperimentalFoundationApi::class, com.google.accompanist.pager.ExperimentalPagerApi::class)
@Composable
fun StickersSheetContent(
    modifier: Modifier = Modifier,
    color: Color = VKgramTheme.palette.surface,
    onEmojiClick: (String) -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(250.dp),
        color = color
    ) {
        val pagerState = rememberPagerState()
        Box {
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth(),
                state = pagerState,
                count = 1
            ) { page ->
                when (page) {
                    0 -> EmojisPage(onEmojiClick = onEmojiClick)
                }
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(VKgramTheme.palette.surface)
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Outlined.SentimentSatisfied,
                        contentDescription = null,
                        tint = if (pagerState.currentPage == 0)
                            VKgramTheme.palette.primary else VKgramTheme.palette.onSurface
                    )
                }

//                IconButton(onClick = {}) {
//                    Icon(
//                        modifier = Modifier.size(20.dp),
//                        imageVector = Icons.Outlined.StarOutline,
//                        contentDescription = null,
//                        tint = if (pagerState.currentPage == 1)
//                            VKgramTheme.palette.primary else VKgramTheme.palette.onSurface
//                    )
//                }
//
//                IconButton(onClick = {}) {
//                    Icon(
//                        modifier = Modifier.size(20.dp),
//                        imageVector = Icons.Outlined.WatchLater,
//                        contentDescription = null,
//                        tint = if (pagerState.currentPage == 2)
//                            VKgramTheme.palette.primary else VKgramTheme.palette.onSurface
//                    )
//                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewStickersBottomSheetContent() {
    MainTheme {
        StickersSheetContent(onEmojiClick = { })
    }
}