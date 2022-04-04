package me.vislavy.vkgram.message_history

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch
import me.vislavy.vkgram.api.data.Attachment
import me.vislavy.vkgram.api.data.AttachmentType
import me.vislavy.vkgram.core.views.ErrorContent
import me.vislavy.vkgram.core.views.LoadingContent
import me.vislavy.vkgram.message_history.models.MessageHistoryIntent
import me.vislavy.vkgram.message_history.models.MessageHistoryViewState
import me.vislavy.vkgram.message_history.views.*

@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MessageHistoryScreen(
    conversationId: Int,
    navController: NavController,
    viewModel: MessageHistoryViewModel
) {
    val viewState = viewModel.viewState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val gallerySheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )

    var media by remember { mutableStateOf<Attachment?>(null) }
    var mediaContentVisible by remember { mutableStateOf(false) }

    when (val state = viewState.value) {
        is MessageHistoryViewState.Loading -> LoadingContent()
        is MessageHistoryViewState.Error -> ErrorContent(onReloadClick = {
            viewModel.onEvent(MessageHistoryIntent.ReloadScreen)
        })
        is MessageHistoryViewState.Display ->
//            Box {
//            ModalBottomSheetLayout(
//                modifier = Modifier.height(1000.dp),
//                sheetContent = {
//                    GallerySheetContent(
//                        viewState = state,
//                        sheetState = gallerySheetState,
//                        onSelectFileClick = { file ->
//                            viewModel.onIntent(
//                                MessageHistoryIntent.UpdateSelectedAttachmentList(
//                                    file
//                                )
//                            )
//                        },
//                        onFileClick = { file ->
//                            image = file
//                            imageContentVisible = !imageContentVisible
//                        }
//                    )
//                },
//                sheetState = gallerySheetState,
//                sheetBackgroundColor = Color.Transparent
//            ) {
                Scaffold(
                    topBar = {
                        MessageHistoryTopBar(
                            viewState = state,
                            navController = navController
                        )
                    },
                    bottomBar = {
                        MessageHistoryBottomBar(
                            viewState = state,
                            onTextChange = { text ->
                                viewModel.onEvent(MessageHistoryIntent.UpdateYourMessageText(text))
                            },
                            onSendClick = { viewModel.onEvent(MessageHistoryIntent.SendMessage) },
                            onOpenGalleryClick = {
                                coroutineScope.launch {
                                    gallerySheetState.show()
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    val modifier = Modifier.padding(paddingValues)
                    MessageHistoryContent(
                        modifier = modifier,
                        viewState = state,
                        onMessageListEnd = { currentListSize ->
                            viewModel.onEvent(
                                MessageHistoryIntent.IncreaseMessageList(
                                    currentListSize
                                )
                            )
                        },
                        onMessageMediaClick = { attachment ->
                            media = attachment
                            mediaContentVisible = true
                        }
                    )
                }
            }

//            val galleryBottomSheetOffset = (gallerySheetState.offset.value - 800) / 2
//            GallerySheetBottomBar(
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .offset(
//                        y = if (gallerySheetState.offset.value > 800)
//                            galleryBottomSheetOffset.dp else 0.dp
//                    ),
//                viewState = state,
//                onTextChange = {
//
//                }
//            )

//            AnimatedVisibility(
//                modifier = Modifier
//                    .align(Alignment.BottomEnd)
//                    .padding(16.dp)
//                    .offset(
//                        y = if (gallerySheetState.offset.value > 800)
//                            galleryBottomSheetOffset.dp else 0.dp
//                    )
//                    .navigationBarsWithImePadding(),
//                visible = state.selectedAttachments.isNotEmpty()
//                        && gallerySheetState.targetValue != ModalBottomSheetValue.Hidden,
//                enter = scaleIn() + fadeIn(),
//                exit = scaleOut() + fadeOut()
//            ) {
//                Box(Modifier.size(60.dp)) {
//                    FloatingActionButton(
//                        modifier = Modifier.align(Alignment.Center),
//                        onClick = { viewModel.onIntent(MessageHistoryIntent.UploadAttachment(state.selectedAttachments[0])) },
//                        backgroundColor = VKgramTheme.palette.secondary
//                    ) {
//                        Icon(
//                            imageVector = Icons.Rounded.Send,
//                            contentDescription = null,
//                            tint = VKgramTheme.palette.onSecondary
//                        )
//                    }
//
//                    Box(
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .size(22.dp)
//                            .background(
//                                color = VKgramTheme.palette.secondary,
//                                shape = CircleShape
//                            )
//                            .border(
//                                width = 2.dp,
//                                color = VKgramTheme.palette.onSecondary,
//                                shape = CircleShape
//                            )
//                    ) {
//                        Text(
//                            modifier = Modifier.align(Alignment.Center),
//                            text = if (state.selectedAttachments.isNotEmpty())
//                                "${state.selectedAttachments.size}" else "1",
//                            color = VKgramTheme.palette.onSecondary,
//                            style = VKgramTheme.typography.caption
//                        )
//                    }
//                }
//            }

//            if (gallerySheetState.currentValue == ModalBottomSheetValue.Hidden) {
//                viewModel.onIntent(MessageHistoryIntent.ClearSelectedAttachmentList)
//            }
//        }
//    }

    if (mediaContentVisible) {
        when (media?.type) {
            AttachmentType.Photo -> ImageContent(
                model = media?.photo!!,
                onDismiss = {
                    mediaContentVisible = false
                }
            )
            AttachmentType.Video -> VideoContent(
                model = media?.video!!,
                onDismiss = {
                    mediaContentVisible = false
                }
            )
            else -> Unit
        }
//        ImageContent(
//            image = image,
//            onDismiss = {
//                imageContentVisible = false
//            }
//        )
    }

    LaunchedEffect(viewState) {
        if (viewState.value !is MessageHistoryViewState.Display) {
            viewModel.onEvent(MessageHistoryIntent.EnterScreen(conversationId))
        }
    }
}