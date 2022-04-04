package me.vislavy.vkgram.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.profile.models.ProfileEvent
import me.vislavy.vkgram.profile.models.ProfileState
import me.vislavy.vkgram.profile.views.ProfileContent
import me.vislavy.vkgram.profile.views.ProfileLoadingContent
import me.vislavy.vkgram.profile.views.ProfileTopAppBar
import me.vislavy.vkgram.profile.views.attachment.ProfileAttachmentsTopAppBar

@ExperimentalAnimationApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun ProfileScreen(
    userId: Int,
    navController: NavController,
    viewModel: ProfileViewModel
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState()

    Scaffold(
        topBar = {
            ProfileTopAppBar(
                modifier = Modifier.statusBarsPadding(),
                navController = navController
            )

            if (state is ProfileState.Display) {
                AnimatedVisibility(
                    visible = (state as ProfileState.Display).isAttachmentsVisible,
                    enter = expandVertically(),
                    exit = shrinkVertically()
                ) {
                    ProfileAttachmentsTopAppBar(
                        modifier = Modifier.statusBarsPadding(),
                        pagerState = pagerState
                    )
                }
            }
        },
        containerColor = VKgramTheme.palette.background
    ) { paddingValues ->
        when (state) {
            is ProfileState.Loading -> ProfileLoadingContent(
                modifier = Modifier
                    .padding(paddingValues)
                    .navigationBarsPadding()
            )
            is ProfileState.Display -> SwipeRefresh(
                state = rememberSwipeRefreshState((state as ProfileState.Display).isReloading),
                onRefresh = { viewModel.onEvent(ProfileEvent.Reload(userId)) },
                indicator = { swipeRefreshState, refreshTrigger ->
                    SwipeRefreshIndicator(
                        modifier = Modifier.statusBarsPadding(),
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
                ProfileContent(
                    modifier = Modifier
                        .padding(paddingValues)
                        .navigationBarsPadding()
                        .verticalScroll(rememberScrollState()),
                    state = state as ProfileState.Display,
                    navController = navController,
                    onSubscribeOrAddFriendClick = {
                        viewModel.onEvent(ProfileEvent.SubscribeOrAddFriend)
                    },
                    onUnsubscribeOrUnfriendClick = {
                        viewModel.onEvent(ProfileEvent.UnsubscribeOrUnfriend)
                    },
                    onAttachmentsButtonClick = {
                        viewModel.onEvent(ProfileEvent.SetAttachmentsVisibility(true))
                    }
                )
            }
        }
    }

    LaunchedEffect(state) {
        viewModel.onEvent(ProfileEvent.Load(userId))
    }
}