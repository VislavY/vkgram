package ru.vyapps.vkgram.new_conversation.screens.conversation_creation.views.gallery

import android.Manifest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ImageNotSupported
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import ru.vyapps.vkgram.core.theme.MainTheme
import ru.vyapps.vkgram.core.theme.VKgramTheme

@ExperimentalPermissionsApi
@Composable
fun GrantStoragePermButton(
    storagePermissionState: PermissionState
) {
    val permDeniedAlertDialogState = remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .clickable {
                if (storagePermissionState.shouldShowRationale || !storagePermissionState.permissionRequested) {
                    storagePermissionState.launchPermissionRequest()
                } else {
                    permDeniedAlertDialogState.value = true
                }
            }
            .size(128.dp),
        color = Color.White
    ) {
        PermDeniedAlertDialog(
            visibleState = permDeniedAlertDialogState,
            text = "Разрешите доступ к хранилищу, чтобы сохранять и загружать фото, видео, музыку и др. файлы."
        )

        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.ImageNotSupported,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = VKgramTheme.palette.onPrimary
            )

            Text(
                text = "Предоставить доступ к галерее",
                color = VKgramTheme.palette.secondaryText,
                textAlign = TextAlign.Center,
                style = VKgramTheme.typography.body2
            )
        }
    }
}

@ExperimentalPermissionsApi
@Preview
@Composable
fun GrantStoragePermButton_Preview() {
    MainTheme {
        GrantStoragePermButton(rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE))
    }
}

@ExperimentalPermissionsApi
@Preview
@Composable
fun DarkGrantStoragePermButton_Preview() {
    MainTheme(darkThemeEnabled = true) {
        GrantStoragePermButton(rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE))
    }
}