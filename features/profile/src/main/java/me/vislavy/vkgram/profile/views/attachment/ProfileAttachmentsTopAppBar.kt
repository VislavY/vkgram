package me.vislavy.vkgram.profile.views.attachment

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme

@ExperimentalPagerApi
@Composable
fun ProfileAttachmentsTopAppBar(
    modifier: Modifier = Modifier,
    pagerState: PagerState = rememberPagerState()
) {
    Column(modifier.fillMaxWidth()) {
        SmallTopAppBar(
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = VKgramTheme.palette.surface,
                navigationIconContentColor = VKgramTheme.palette.onSurface,
                titleContentColor = VKgramTheme.palette.onSurface,
                actionIconContentColor = VKgramTheme.palette.onSurfaceVariant
            ),
            navigationIcon = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null
                    )
                }
            },
            title = {
                Text(
                    text = "Вложения",
                    style = VKgramTheme.typography.titleLarge
                )
            }
        )


        ProfileAttachmentsTabRow(pagerState = pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun ProfileAttachmentsTabRow(
    modifier: Modifier = Modifier,
    pagerState: PagerState
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = VKgramTheme.palette.surface
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val titles = listOf("Фото", "Видео", "Аудио", "Документы")
            titles.forEachIndexed { index, title ->
                val isSelected = pagerState.currentPage == index
                val titleColor = if (isSelected)
                    VKgramTheme.palette.onSurface else VKgramTheme.palette.onSurfaceVariant
                Text(
                    text = title,
                    style = VKgramTheme.typography.labelLarge,
                    color = titleColor
                )
            }
        }
    }
}

@ExperimentalPagerApi
@Preview("Profile attachment top app bar")
@Composable
fun PreviewAttachmentsTopAppBar() {
    MainTheme {
        ProfileAttachmentsTopAppBar()
    }
}

@ExperimentalPagerApi
@Preview("Profile attachment top app bar dark theme")
@Composable
fun PreviewAttachmentsTopAppBar_Dark() {
    MainTheme(darkThemeEnabled = true) {
        ProfileAttachmentsTopAppBar()
    }
}