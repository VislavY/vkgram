package me.vislavy.vkgram.message_history.views.gallery

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import me.vislavy.vkgram.message_history.models.MessageHistoryViewState
import java.io.File
import java.lang.Exception

@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun GallerySheetContent(
    modifier: Modifier = Modifier,
    viewState: MessageHistoryViewState.Display,
    sheetState: ModalBottomSheetState,
    color: Color = VKgramTheme.palette.surface,
    onSelectFileClick: (File) -> Unit,
    onFileClick: (File) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val windowInsets = LocalWindowInsets.current
    val sheetProgress = sheetState.progress

    var shapeCorner by remember { mutableStateOf(32F) }
    shapeCorner = when (sheetState.progress.to) {
        ModalBottomSheetValue.Expanded -> 32F - 32F * sheetState.progress.fraction
        ModalBottomSheetValue.HalfExpanded -> 0F + 32F * sheetState.progress.fraction
        else -> 32F
    }
    Surface(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(
            topStart = shapeCorner,
            topEnd = shapeCorner
        ),
        color = color
    ) {
        Box {
            var topBarAlpha by remember { mutableStateOf(0F) }
            topBarAlpha = when {
                sheetProgress.to == ModalBottomSheetValue.Expanded -> if (sheetProgress.fraction >= 0.8F)
                    (1F - 0.8F / sheetProgress.fraction) * 5 else 0F
                sheetProgress.from == ModalBottomSheetValue.Expanded -> if (sheetProgress.fraction <= 0.2F)
                    (1F - 0.8F / (1F - sheetProgress.fraction)) * 5 else 0F
                else -> 0F
            }
            TopAppBar(
                modifier = Modifier
                    .alpha(if (topBarAlpha > 0.999) 1F else topBarAlpha)
                    .zIndex(if (sheetProgress.to == ModalBottomSheetValue.Expanded) 1F else 0F),
                backgroundColor = VKgramTheme.palette.primary,
                contentPadding = rememberInsetsPaddingValues(
                    insets = windowInsets.statusBars,
                    applyBottom = false
                ),
            ) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        tint = VKgramTheme.palette.onPrimary
                    )
                }

                Spacer(Modifier.width(16.dp))

                Text(
                    text = "Галерея",
                    color = VKgramTheme.palette.primaryText,
                    style = VKgramTheme.typography.title
                )
            }

            Column {
                val spacerHeight by animateDpAsState(
                    targetValue = when {
                        sheetProgress.to == ModalBottomSheetValue.Expanded -> if (sheetProgress.fraction >= 0.85F)
                            windowInsets.statusBars.top.dp + 6.dp else 0.dp
                        sheetProgress.from == ModalBottomSheetValue.Expanded -> if (sheetProgress.fraction <= 0.15F)
                            windowInsets.statusBars.top.dp + 6.dp else 0.dp
                        else -> 0.dp
                    }
                )
                Spacer(Modifier.height(spacerHeight))

                Spacer(Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .width(36.dp)
                        .height(4.dp)
                        .align(Alignment.CenterHorizontally)
                        .background(
                            color = VKgramTheme.palette.onSurface.copy(0.24F),
                            shape = RoundedCornerShape(4.dp)
                        )
                )

                Spacer(Modifier.height(10.dp))

                val storagePermissionState = rememberPermissionState(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                if (storagePermissionState.hasPermission) {
                    val images = mutableListOf<File>()

                    val projection = arrayOf(
                        MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DATE_TAKEN
                    )
                    var cursor: Cursor? = null
                    try {
                        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
                        cursor = context.contentResolver.query(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection,
                            null,
                            null,
                            sortOrder

                        )
                        cursor?.let {
                            val columnIndex =
                                it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                            while (it.moveToNext()) {
                                val imagePath = cursor.getString(columnIndex)
                                val image = File(imagePath)
                                images.add(image)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    } finally {
                        cursor?.close()
                    }

                    LazyVerticalGrid(
                        cells = GridCells.Adaptive(100.dp),
                        contentPadding = PaddingValues(horizontal = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(images) { image ->
                            ImageItem(
                                file = image,
                                onSelectClick = onSelectFileClick,
                                onClick = onFileClick,
                                isSelected = viewState.selectedAttachments.contains(image)
                            )
                        }
                    }
                } else {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "Чтобы прикрепить файл, приложению необходим доступ к хранилищу.",
                        textAlign = TextAlign.Center,
                        color = VKgramTheme.palette.primaryText,
                        style = VKgramTheme.typography.subtitle1
                    )

                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            if (!storagePermissionState.shouldShowRationale) {
                                storagePermissionState.launchPermissionRequest()
                            } else {
                                val intent =
                                    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                val uri = Uri.fromParts("package", context.packageName, null)
                                intent.data = uri
                                context.startActivity(intent)

                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(VKgramTheme.palette.secondary)
                    ) {
                        Text(
                            text = "Разрешить",
                            color = Color.White,
                            style = VKgramTheme.typography.button
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewGallerySheetContent() {
    MainTheme {
        GallerySheetContent(
            viewState = MessageHistoryViewState.Display(),
            sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
            onSelectFileClick = { },
            onFileClick = { }
        )
    }
}