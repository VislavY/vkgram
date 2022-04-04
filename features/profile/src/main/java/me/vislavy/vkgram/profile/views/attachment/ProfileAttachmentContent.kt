package me.vislavy.vkgram.profile.views.attachment

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import me.vislavy.vkgram.api.data.Photo
import me.vislavy.vkgram.api.data.PhotoSize
import me.vislavy.vkgram.api.data.User
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.profile.models.ProfileState
import me.vislavy.vkgram.profile.views.attachment.items.ProfilePhotoItem

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun ProfileAttachmentContent(
    modifier: Modifier = Modifier,
    state: ProfileState.Display,
    pagerState: PagerState = rememberPagerState()
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.surface
    ) {
        HorizontalPager(
            state = pagerState,
            count = 4,
            contentPadding = PaddingValues(8.dp)
        ) { page ->
            when (page) {
                0 -> ProfilePhotoPage(models = state.photos)
                1 -> Unit
                2 -> Unit
                3 -> Unit
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ProfilePhotoPage(
    modifier: Modifier = Modifier,
    models: List<Photo> = emptyList()
) {
    val config = LocalConfiguration.current

    Surface(
        modifier = modifier.fillMaxSize(),
        color = VKgramTheme.palette.surface
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(3),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(models) { model ->
                val size = (config.screenWidthDp / 3 - 24).dp
                ProfilePhotoItem(
                    modifier = Modifier.size(size),
                    model = model
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Preview("Profile attachment content")
@Composable
fun PreviewProfileAttachmentContent() {
    MainTheme {
        ProfileAttachmentContent(
            state = ProfileState.Display(
                user = User(),
                photos = List(10) { Photo(sizes = listOf(PhotoSize())) }
            )
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Preview("Profile attachment content dark theme")
@Composable
fun PreviewProfileAttachmentContent_Dark() {
    MainTheme(darkThemeEnabled = true) {
        ProfileAttachmentContent(
            state = ProfileState.Display(
                user = User(),
                photos = List(10) { Photo(sizes = listOf(PhotoSize())) }
            )
        )
    }
}

