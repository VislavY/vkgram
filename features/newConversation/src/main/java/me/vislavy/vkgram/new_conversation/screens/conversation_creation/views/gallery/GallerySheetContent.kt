package me.vislavy.vkgram.new_conversation.screens.conversation_creation.views.gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import me.vislavy.vkgram.core.theme.MainTheme
import me.vislavy.vkgram.core.theme.VKgramTheme
import java.io.File

@ExperimentalCoilApi
@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun GallerySheetContent(
    state: ModalBottomSheetState,
    onMediaFileSelect: (File) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        color = Color.White,
    ) {
        Column {
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Выберите фотографию или видео",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = VKgramTheme.palette.primaryText,
                style = VKgramTheme.typography.subtitle1
            )

            Spacer(Modifier.height(16.dp))

            val storagePermissionState =
                rememberPermissionState(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            LazyVerticalGrid(
                cells = GridCells.Adaptive(128.dp),
                contentPadding = PaddingValues(horizontal = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                item {
                    CameraOpenButton()
                }

                if (storagePermissionState.hasPermission) {
                    val picturesDir = File("/storage/emulated/0/Pictures")
                    val mediaFiles = picturesDir.listFiles()
                    mediaFiles?.size?.let {
                        items(it) { i ->
                            ImageItem(
                                imageFile = mediaFiles[i],
                                onClick = { mediaFile ->
                                    onMediaFileSelect(mediaFile)
                                    coroutineScope.launch {
                                        state.hide()
                                    }
                                }
                            )
                        }
                    }
                } else {
                    item {
                        GrantStoragePermButton(storagePermissionState)
                    }
                }
            }
        }
    }
}

@ExperimentalCoilApi
@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun GallerySheetContent_Preview() {
    MainTheme {
        GallerySheetContent(
            state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
            onMediaFileSelect = { }
        )
    }
}

@ExperimentalCoilApi
@ExperimentalPermissionsApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Preview
@Composable
fun DarkGallerySheetContent_Preview() {
    MainTheme(darkThemeEnabled = true) {
        GallerySheetContent(
            state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden),
            onMediaFileSelect = { }
        )
    }
}