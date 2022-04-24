package me.vislavy.vkgram.profile.ui.contents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.vk.sdk.api.messages.dto.MessagesHistoryAttachment
import kotlinx.coroutines.launch
import me.vislavy.vkgram.core.base.DisplayState
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.core.theme.defaults.VKgramSwitchDefaults
import me.vislavy.vkgram.core.ui.ErrorContent
import me.vislavy.vkgram.profile.R
import me.vislavy.vkgram.profile.models.ProfileEvent
import me.vislavy.vkgram.profile.models.ProfileState
import me.vislavy.vkgram.profile.ui.ProfileTopAppBar
import me.vislavy.vkgram.profile.ui.Section
import me.vislavy.vkgram.profile.ui.attachments.AudioItem
import me.vislavy.vkgram.profile.ui.attachments.FileItem
import me.vislavy.vkgram.profile.ui.attachments.PhotoItem
import me.vislavy.vkgram.profile.ui.attachments.VideoItem
import me.vislavy.vkgram.profile.ui.foundation.NestedScrollScaffold
import me.vislavy.vkgram.profile.ui.foundation.rememberNestedScrollScaffoldState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    state: ProfileState = ProfileState(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavController = rememberNavController(),
    uid: Long = 0,
    onEvent: (ProfileEvent) -> Unit = { }
) {
    val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val nestedScrollScaffoldState = rememberNestedScrollScaffoldState()

    Scaffold(
        modifier = modifier.systemBarsPadding(),
        scaffoldState = scaffoldState,
        backgroundColor = VKgramTheme.palette.background,
        topBar = {
            ProfileTopAppBar(
                navController = navController,
                model = state.user,
                hovered = nestedScrollScaffoldState.offset < 0F,
                onEvent = onEvent
            )
        }
    ) { paddingValues ->
        when (state.displayState) {
            DisplayState.Loading -> ProfileLoadingContent(Modifier.padding(paddingValues))
            DisplayState.Error -> ErrorContent(
                modifier = Modifier.padding(paddingValues),
                onReload = { onEvent(ProfileEvent.Reload(uid)) }
            )
            else -> SwipeRefresh(
                state = rememberSwipeRefreshState(state.displayState == DisplayState.Reloading),
                onRefresh = { onEvent(ProfileEvent.Reload(uid)) },
                indicator = { swipeRefreshState, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = swipeRefreshState,
                        refreshTriggerDistance = refreshTrigger,
                        backgroundColor = VKgramTheme.palette.surface,
                        contentColor = VKgramTheme.palette.primary,
                        shape = CircleShape,
                        arrowEnabled = true,
                        scale = true
                    )
                }
            ) {
                ModalBottomSheetLayout(
                    sheetState = modalBottomSheetState,
                    sheetContent = {
                    ProfileBottomSheetContent(model = state.user!!)
                }
                ) {
                    NestedScrollScaffold(
                        state = nestedScrollScaffoldState,
                        scrollableContent = {
                            UserProfileContent(
                                modifier = Modifier.padding(bottom = 8.dp),
                                navController = navController,
                                model = state.user!!,
                                onEvent = onEvent
                            )
                        },
                        nestedScrollableContent = {
                            ProfileAttachments(
                                photoAttachments = state.photoAttachments,
                                videoAttachments = state.videoAttachments,
                                audioAttachments = state.audioAttachments,
                                fileAttachments = state.fileAttachments,
                                onEvent = onEvent
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileNotifications(modifier: Modifier = Modifier) {
    Section(
        modifier = modifier,
        body = {
            Column {
                Text(
                    text = stringResource(R.string.profile_notifications),
                    color = VKgramTheme.palette.onSurface,
                    style = VKgramTheme.typography.bodyLarge
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Включены",
                    color = VKgramTheme.palette.onSurfaceVariant,
                    style = VKgramTheme.typography.bodyMedium
                )
            }
        },
        trailingIcon = {
            Switch(
                checked = true,
                onCheckedChange = { },
                colors = VKgramSwitchDefaults.switchColors()
            )
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProfileAttachments(
    modifier: Modifier = Modifier,
    photoAttachments: List<MessagesHistoryAttachment>,
    videoAttachments: List<MessagesHistoryAttachment>,
    audioAttachments: List<MessagesHistoryAttachment>,
    fileAttachments: List<MessagesHistoryAttachment>,
    onEvent: (ProfileEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    val pageTitles = setOf("Фото", "Видео", "Аудио", "Файлы")

    val config = LocalConfiguration.current
    val photoAndVideoItemSize = config.screenWidthDp / 3 - 4

    Column(modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                Divider(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .background(
                            color = VKgramTheme.palette.primary,
                            shape = RoundedCornerShape(
                                topStart = 4.dp,
                                topEnd = 4.dp
                            )
                        ),
                    color = Color.Transparent,
                    thickness = 4.dp
                )
            },
            divider = { }
        ) {
            pageTitles.forEachIndexed { index, pageTitle ->
                val selected = index == pagerState.currentPage

                Tab(
                    selected = selected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    selectedContentColor = VKgramTheme.palette.primary,
                    unselectedContentColor = VKgramTheme.palette.onSurfaceVariant
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = pageTitle,
                        style = VKgramTheme.typography.labelLarge
                    )
                }
            }
        }

        HorizontalPager(
            count = pageTitles.size,
            state = pagerState
        ) { pageIndex ->
            when (pageIndex) {
                0 -> LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    itemsIndexed(photoAttachments) { index, photoAttachment ->
                        PhotoItem(
                            modifier = Modifier.size(photoAndVideoItemSize.dp),
                            model = photoAttachment.attachment.photo!!
                        )

                        if (index == photoAttachments.size - 1) {
                            onEvent(ProfileEvent.IncreasePhotoList)
                        }
                    }
                }
                1 -> LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    itemsIndexed(videoAttachments) { index, videoAttachment ->
                        VideoItem(
                            modifier = Modifier.size(photoAndVideoItemSize.dp),
                            model = videoAttachment.attachment.video!!
                        )

                        if (index == videoAttachments.size - 1) {
                            onEvent(ProfileEvent.IncreaseVideoList)
                        }
                    }
                }
                2 -> LazyColumn {
                    itemsIndexed(audioAttachments) { index, audioAttachment ->
                        AudioItem(model = audioAttachment.attachment.audio!!)

                        if (index == audioAttachments.size - 1) {
                            onEvent(ProfileEvent.IncreaseAudioList)
                        }
                    }
                }
                3 -> LazyColumn {
                    itemsIndexed(fileAttachments) { index, fileAttachment ->
                        FileItem(model = fileAttachment.attachment.doc!!)

                        if (index == fileAttachments.size - 1) {
                            onEvent(ProfileEvent.IncreaseFileList)
                        }
                    }
                }
            }
        }
    }
}